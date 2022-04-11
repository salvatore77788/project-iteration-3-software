package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.AvailableFunds;

public class AvailableFundsTest {
	private SelfCheckoutStation station;
	private AvailableFunds funds;
	
	private Currency currency = Currency.getInstance(Locale.CANADA);
	int[] bnDenoms = new int[] {5, 10, 20, 50, 100};
	BigDecimal[] coinDenoms = new BigDecimal[] {new BigDecimal("0.05"), new BigDecimal("0.10"), new BigDecimal("0.25"), 
			new BigDecimal("1.00"), new BigDecimal("2.00")};
	
	private Coin nickel = new Coin(currency, new BigDecimal("0.05"));
	private Coin dime = new Coin(currency, new BigDecimal("0.10"));
	private Coin quarter = new Coin(currency, new BigDecimal("0.25"));
	private Coin loonie = new Coin(currency, new BigDecimal("1.00"));
	private Coin toonie = new Coin(currency, new BigDecimal("2.00"));
	
	private Banknote bn5 = new Banknote(currency, 5);
	private Banknote bn10 = new Banknote(currency, 10);
	private Banknote bn20 = new Banknote(currency, 20);
	private Banknote bn50 = new Banknote(currency, 50);
	private Banknote bn100 = new Banknote(currency, 100);
	
	@Before
	public void setUp() throws Exception {
		station = new SelfCheckoutStation(currency, bnDenoms, coinDenoms, 1000, 1);
		funds = new AvailableFunds(station);
		funds.attachAll();
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
		} catch (DisabledException | OverloadException e) {
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
		} catch (DisabledException | OverloadException e) {
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
	public void testIsCoinStorageFull() {
		Coin testCoin = new Coin(currency, coinDenoms[0]);
		for(int i = 0; i < SelfCheckoutStation.COIN_STORAGE_CAPACITY; i++) {
			try {
				station.coinStorage.accept(testCoin);
			} catch (DisabledException | OverloadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		assertTrue("Coin storage should be full.", funds.getIsCoinStorageFull());
		
		station.coinStorage.unload();
		
		assertFalse("Coin storage should not be full.", funds.getIsCoinStorageFull());
	}
	
	
	@Test
	public void testIsBanknoteStorageFullAndUnloaded() {
		Banknote testBn = new Banknote(currency, bnDenoms[0]);
		for(int i = 0; i < SelfCheckoutStation.BANKNOTE_STORAGE_CAPACITY; i++) {
			try {
				station.banknoteStorage.accept(testBn);
			} catch (DisabledException | OverloadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		assertTrue("Banknote storage should be full.", funds.getIsBanknoteStorageFull());
		
		station.banknoteStorage.unload();
		
		assertFalse("Banknote storage should not be full.", funds.getIsBanknoteStorageFull());
	}
	
	@Test
	public void testLoadEmitCoins() {
		Coin[] coins = new Coin[] {nickel, dime, quarter, loonie, toonie};
		
		for(Coin c : coins) {
			BigDecimal denom = c.getValue();
			
			loadCoins(c, 50);
			assertEquals("Coin $" + denom.toString() + "" , 50, funds.getCoinCount(denom));
			
			emitCoins(station.coinDispensers.get(denom), 25);
			assertEquals("Coin " + denom.toString() + "." , 25, funds.getCoinCount(denom));
			
			emitCoins(station.coinDispensers.get(denom), 25);
			assertEquals("Coin " + denom.toString() + "." , 0, funds.getCoinCount(denom));
		}
	}
	
	@Test
	public void testLoadEmitBanknotes() {
		Banknote[] bns = new Banknote[] {bn5, bn10, bn20, bn50, bn100};
		
		for(Banknote bn : bns) {
			int denom = bn.getValue();
			
			loadBanknotes(bn, 1);
			assertEquals("Banknote $" + denom, 1, funds.getBanknoteCount(denom));
			
			emitBanknote(station.banknoteDispensers.get(denom));
			assertEquals("Banknote $" + denom, 0, funds.getBanknoteCount(denom));
		}
	}
	
	@Test
	public void testInvalidCoinDenom() {
		BigDecimal badDenom = new BigDecimal("0.5");
		
		assertEquals("Incorrect invalid coin count.", 0, funds.getCoinCount(badDenom));
	}
	
	@Test
	public void testInvalidBanknoteDenom() {
		int badDenom = 2;
		
		assertEquals("Incorrect invalid banknote count.", 0, funds.getBanknoteCount(badDenom));
	}
	
	@Test
	public void testFundsDetached() {
		funds.detachAll();
		
		loadCoins(nickel, 100);
		loadBanknotes(bn10, 100);
		
		assertEquals("Funds detected.", new BigDecimal("0.00"), funds.getTotalFundsStored());
		
		// Check that storage fullness doesn't update
		Coin testCoin = new Coin(currency, coinDenoms[0]);
		for(int i = 0; i < SelfCheckoutStation.COIN_STORAGE_CAPACITY; i++) {
			try {
				station.coinStorage.accept(testCoin);
			} catch (DisabledException | OverloadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertFalse("Coin storage should not be seen as full.", funds.getIsCoinStorageFull());
		
		Banknote testBn = new Banknote(currency, bnDenoms[0]);
		for(int i = 0; i < SelfCheckoutStation.BANKNOTE_STORAGE_CAPACITY; i++) {
			try {
				station.banknoteStorage.accept(testBn);
			} catch (DisabledException | OverloadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertFalse("Banknote storage should not be seen as full.", funds.getIsBanknoteStorageFull());
	}
	
	private void emitCoins(CoinDispenser d, int count) {
		int trayCoinCount = 0;
		for(int i = 0; i < count; i++)
			try {
				d.emit();
				if(++trayCoinCount >= 20) {
					station.coinTray.collectCoins();
					trayCoinCount = -20;
				}
			} catch (OverloadException | EmptyException | DisabledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		station.coinTray.collectCoins();
	}
	
	private void emitBanknote(BanknoteDispenser d) {
		try {
			d.emit();
		} catch (EmptyException | DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		station.banknoteOutput.removeDanglingBanknotes();
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
}
