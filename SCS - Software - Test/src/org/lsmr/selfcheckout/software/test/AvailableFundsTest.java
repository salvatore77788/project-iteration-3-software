package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
import org.lsmr.selfcheckout.devices.DisabledException;
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
}
