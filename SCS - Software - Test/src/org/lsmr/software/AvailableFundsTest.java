package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteSlotObserver;

public class AvailableFundsTest {
	private class BanknoteSlotStub implements BanknoteSlotObserver {
		public List<Banknote> banknotes = new ArrayList<Banknote>();
		
		private Timer removeBanknoteTimer = new Timer();
		
		// Need to use atomics to handle threading synchronization and avoid a race condition.
		private AtomicInteger taskCount = new AtomicInteger(0);
		//private int taskCount = 0;
		
		public boolean isProcessingTasks() {
			return (taskCount.get() > 0);
		}

		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void banknoteInserted(BanknoteSlot slot) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void banknoteEjected(BanknoteSlot slot) {
			// TODO Auto-generated method stub
			// Remove the banknote immediately
			//banknotes.add(slot.removeDanglingBanknote());
			// Need to delay this by one second, since otherwise there are issues with call order of observation events.
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					//taskCount++;
					taskCount.set(taskCount.get()+1);
					banknotes.add(slot.removeDanglingBanknote());
					taskCount.set(taskCount.get()-1);
					//taskCount--;
				}
			};
			
			removeBanknoteTimer.schedule(task, 1);
		}

		@Override
		public void banknoteRemoved(BanknoteSlot slot) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private SelfCheckoutStation station;
	private AvailableFunds funds;
	private BanknoteSlotStub banknotes;
	
	private Coin nickel = new Coin(SelfCheckoutStationSetup.currency, new BigDecimal("0.05"));
	private Coin dime = new Coin(SelfCheckoutStationSetup.currency, new BigDecimal("0.10"));
	private Coin quarter = new Coin(SelfCheckoutStationSetup.currency, new BigDecimal("0.25"));
	private Coin loonie = new Coin(SelfCheckoutStationSetup.currency, new BigDecimal("1.00"));
	private Coin toonie = new Coin(SelfCheckoutStationSetup.currency, new BigDecimal("2.00"));
	
	private Banknote bn5 = new Banknote(SelfCheckoutStationSetup.currency, 5);
	private Banknote bn10 = new Banknote(SelfCheckoutStationSetup.currency, 10);
	private Banknote bn20 = new Banknote(SelfCheckoutStationSetup.currency, 20);
	private Banknote bn50 = new Banknote(SelfCheckoutStationSetup.currency, 50);
	private Banknote bn100 = new Banknote(SelfCheckoutStationSetup.currency, 100);
	
	@Before
	public void setUp() throws Exception {
		station = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
		funds = new AvailableFunds(station);
		banknotes = new BanknoteSlotStub();
		station.banknoteOutput.attach(banknotes);
	}

	@Test
	public void testCoinAmountStored() {
		// Store $8.85 in coins
		try {
			station.coinSlot.accept(toonie);
			station.coinSlot.accept(toonie);
			station.coinSlot.accept(toonie);
			station.coinSlot.accept(loonie);
			station.coinSlot.accept(quarter);
			station.coinSlot.accept(quarter);
			station.coinSlot.accept(quarter);
			station.coinSlot.accept(quarter);
			
			for(int i = 0; i < 8; i++) {
				station.coinSlot.accept(dime);
			}
			
			station.coinSlot.accept(nickel);
			
			BigDecimal stored = funds.getTotalFundsStored();
			
			assertEquals("Coin amount stored not the same.", new BigDecimal("8.85"), stored);
		} catch (DisabledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCoinsFull() {
		loadCoins(nickel, SelfCheckoutStation.COIN_DISPENSER_CAPACITY-1);
		try {
			station.coinSlot.accept(nickel);
			
			BigDecimal expected = new BigDecimal("0.05");
			expected = expected.multiply(new BigDecimal(SelfCheckoutStation.COIN_DISPENSER_CAPACITY));
			assertEquals("Number of nickels stored incorrect.", expected, funds.getTotalFundsStored());
		} catch (DisabledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBanknoteAmountStored() {
		try {
			station.banknoteDispensers.get(5).load(bn5);
			station.banknoteDispensers.get(10).load(bn10);
			station.banknoteDispensers.get(20).load(bn20);
			station.banknoteDispensers.get(50).load(bn50);
			station.banknoteDispensers.get(100).load(bn100);
			
			BigDecimal stored = funds.getTotalFundsStored();
			
			assertEquals("Banknote amount stored is not the same.", new BigDecimal("185.00"), stored);
		} catch (SimulationException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFundsStored() {			
			try {
				station.coinSlot.accept(quarter);
				station.coinSlot.accept(loonie);
				station.coinSlot.accept(toonie);
				station.coinSlot.accept(nickel);
				
				station.banknoteDispensers.get(10).load(bn10);
				station.banknoteDispensers.get(50).load(bn50);
				station.banknoteDispensers.get(100).load(bn100);
				
				BigDecimal stored = funds.getTotalFundsStored();
				
				assertEquals("Stored funds not correct.", new BigDecimal("163.30"), stored);
			} catch (DisabledException | SimulationException | OverloadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	@Test
	public void testCoinsUnloaded() {
		// Load
		loadCoins(quarter, 100);
		
		// Then unload
		station.coinDispensers.get(quarter.getValue()).unload();
		
		assertEquals("Funds stored should be zero.", new BigDecimal("0.00"), funds.getTotalFundsStored());
	}
	
	@Test
	public void testBanknotesUnloaded() {
		loadBanknotes(bn5, 100);
		
		station.banknoteDispensers.get(bn5.getValue()).unload();
		
		assertEquals("Funds stored should be zero", new BigDecimal("0.00"), funds.getTotalFundsStored());
	}
	
	@Test
	public void testQuarterChange() {
		loadCoinsAndBanknotes();
		
		funds.beginDisbursingChange(new BigDecimal("0.75"));
		assertFalse("Should not be still disbursing change.", funds.isDisbursingChange());
		
		List<Coin> change = station.coinTray.collectCoins();
		
		// Change should be three quarters
		Coin[] expectedCoins = {quarter, quarter, quarter};
		checkCoins(change, expectedCoins);
	}
	
	@Test
	public void testCoinChange() {
		loadCoinsAndBanknotes();
		
		funds.beginDisbursingChange(new BigDecimal("3.65"));
		assertFalse("Should not be still disbursing change.", funds.isDisbursingChange());
		
		List<Coin> change = station.coinTray.collectCoins();
		
		// Should be one toonie, one loonie, two quarters, one dime, and one nickel
		Coin[] expectedCoins = {toonie, loonie, quarter, quarter, dime, nickel};
		checkCoins(change, expectedCoins);
	}
	
	@Test
	public void testBanknote5Change() {
		loadCoinsAndBanknotes();
		
		funds.beginDisbursingChange(new BigDecimal("5.00"));
		
		while(isDisbursingChange() || banknotes.isProcessingTasks()) {
			// Empty loop since we're waiting for the banknotes to be removed
		}
		
		// Change should be one $5
		assertEquals("Incorrect number of banknotes.", 1, banknotes.banknotes.size());
		
		assertEquals("Banknote incorrect", bn5, banknotes.banknotes.get(0));
	}
	
	@Test
	public void testBanknoteChange() {
		loadCoinsAndBanknotes();
		
		funds.beginDisbursingChange(new BigDecimal("285.00"));
		while(isDisbursingChange()) {
			// Empty loop since we're waiting for the banknotes to be removed
		}
		
		Banknote[] expected = {bn100, bn100, bn50, bn20, bn10, bn5};
		checkBanknotes(expected);
	}
	
	@Test
	public void testChangeGeneral() {
		loadCoinsAndBanknotes();
		
		funds.beginDisbursingChange(new BigDecimal("118.90"));
		while(isDisbursingChange()) {
			// Empty loop since we're waiting for the banknotes to be removed
		}
		
		Banknote[] expectedBn = {bn100, bn10, bn5};
		Coin[] expectedCoins = {toonie, loonie, quarter, quarter, quarter, dime, nickel};
		
		List<Coin> change = station.coinTray.collectCoins();
		
		checkBanknotes(expectedBn);
		checkCoins(change, expectedCoins);
	}
	
	@Test
	public void testChangeMissingCoins() {
		loadCoins(dime, 200);
		loadCoins(loonie, 1);
		
		funds.beginDisbursingChange(new BigDecimal("2.10"));
		assertFalse("Should not be still disbursing change.", funds.isDisbursingChange());
		
		Coin[] expectedCoins = {loonie, dime, dime, dime, dime, dime, dime, dime, dime, dime, dime, dime};
		List<Coin> change = station.coinTray.collectCoins();
		
		checkCoins(change, expectedCoins);
	}
	
	@Test
	public void testChangeMissingBanknotes() {
		loadBanknotes(bn5, 100);
		loadBanknotes(bn10, 1);
		loadBanknotes(bn50, 2);
		
		funds.beginDisbursingChange(new BigDecimal("70.00"));
		while(isDisbursingChange()) {
			// Empty loop since we're waiting for the banknotes to be removed
		}
		
		Banknote[] expectedBn = {bn50, bn10, bn5, bn5};
		checkBanknotes(expectedBn);
	}
	
	@Test
	public void testChangeMissing() {
		loadCoins(quarter, 200);
		loadCoins(toonie, 1);
		loadBanknotes(bn10, 20);
		loadBanknotes(bn20, 1);
		
		funds.beginDisbursingChange(new BigDecimal("44.50"));
		while(isDisbursingChange()) {
			// Empty loop since we're waiting for the banknotes to be removed
		}
		
		Coin[] expectedCoins = {toonie, quarter, quarter, quarter, quarter, quarter, quarter, quarter, quarter, quarter, quarter};
		Banknote[] expectedBn = {bn20, bn10, bn10};
		
		List<Coin> change = station.coinTray.collectCoins();
		
		checkCoins(change, expectedCoins);
		checkBanknotes(expectedBn);
	}
	
	@Test
	public void testNotEnoughChange() {
		loadCoins(quarter, 1);
		loadBanknotes(bn10, 1);
		loadBanknotes(bn100, 1);
		
		funds.beginDisbursingChange(new BigDecimal("34.50"));
		while(isDisbursingChange()) {
			
		}
		
		Coin[] expected = {quarter};
		Banknote[] expectedBn = {bn10};
		
		List<Coin> change = station.coinTray.collectCoins();
		
		checkCoins(change, expected);
		checkBanknotes(expectedBn);
		
		assertEquals("Remaining change not correct.", new BigDecimal("24.25"), funds.changeLeft());
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testTryToDisburseChangeSameTime() {
		loadCoinsAndBanknotes();
		
		funds.beginDisbursingChange(new BigDecimal("1000"));
		funds.beginDisbursingChange(new BigDecimal("1000"));
	}
	
	private void checkCoins(List<Coin> change, Coin[] expectedCoins) {
		for(int i = 0; i < expectedCoins.length; i++) {
			Coin exp = expectedCoins[i];
			Coin res = change.get(i);
			assertEquals("Incorrect coin.", exp, res);
		}
		for(int i = expectedCoins.length; i < 20; i++) {
			assertEquals("Coin should be null.", null, change.get(i));
		}
	}
	
	private void checkBanknotes(Banknote[] expected) {
		assertEquals("Incorrect number of banknotes", expected.length, banknotes.banknotes.size());
		
		for(int i = 0; i < expected.length; i++)
			assertEquals("Incorrect banknote", expected[i], banknotes.banknotes.get(i));
	}
	
	// Helper method to just load a ton of coins and banknotes into the self-checkout station.
	private void loadCoinsAndBanknotes() {
		loadCoins(nickel, 200);
		loadCoins(dime, 200);
		loadCoins(quarter, 200);
		loadCoins(loonie, 200);
		loadCoins(toonie, 200);
		
		loadBanknotes(bn5, 100);
		loadBanknotes(bn10, 100);
		loadBanknotes(bn20, 100);
		loadBanknotes(bn50, 100);
		loadBanknotes(bn100, 100);
	}
	
	private void loadCoins(Coin c, int count) {
		try {
			station.coinDispensers.get(c.getValue()).load(Collections.nCopies(count, c).toArray(new Coin[count]));
		} catch (SimulationException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadBanknotes(Banknote bn, int count) {
		try {
			station.banknoteDispensers.get(bn.getValue()).load(Collections.nCopies(count, bn).toArray(new Banknote[count]));
		} catch (SimulationException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Need this method to sidestep caching of the AvailableFunds' "disbursingChange" flag in loops by marking this as synchronized.
	// Since the actual code has no need to worry about multithreading, better to do it here than mark the field as volatile.
	// Only seems to be a problem when running eclemma
	private synchronized boolean isDisbursingChange() {
		return funds.isDisbursingChange();
	}
}
