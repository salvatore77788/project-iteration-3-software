package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.SimulationException;

public class payWithCoinTest {
	
	//Instance Creation
	SelfCheckoutStation station;
	public PayWithCoin testCoinPayment;
 	public Currency currencyTest = SelfCheckoutStationSetup.currency;
 	
 	@Before
 	public void setUp() {
 		station = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
 		testCoinPayment = new PayWithCoin(station);
 	}
 	
 	@Test
 	public void testCoinPaid() {
 		BigDecimal value = new BigDecimal("0.05");
 		Coin testCoin = new Coin(currencyTest, value);
 		
 		try {
 			station.coinSlot.accept(testCoin);
 			
 			assertEquals("Coin was not paid.", new BigDecimal("0.05"), testCoinPayment.totalPaid);
 		}
 		catch(DisabledException e) {
 			e.printStackTrace();
 		}
 	}
 	
 	@Test
 	public void testInvalidCoin() {
 		Coin badCoin = new Coin(Currency.getInstance(Locale.CHINA), new BigDecimal("0.15"));
 		
 		try {
 			station.coinSlot.accept(badCoin);
 			
 			assertEquals("Coin was incorrectly accepted", BigDecimal.ZERO, testCoinPayment.totalPaid);
 		} catch(DisabledException e) {
 			e.printStackTrace();
 		}
 	}
 	
 	@Test
 	public void testCoinPaidIntoStorage() {
 		Coin nickel = new Coin(currencyTest, new BigDecimal("0.05"));
 		try {
			station.coinDispensers.get(nickel.getValue()).load(Collections.nCopies(SelfCheckoutStation.COIN_DISPENSER_CAPACITY, nickel).toArray(new Coin[SelfCheckoutStation.COIN_DISPENSER_CAPACITY]));
			
			station.coinSlot.accept(nickel);
			
			assertEquals("Coin was not paid.", new BigDecimal("0.05"), testCoinPayment.totalPaid);
		} catch (SimulationException | OverloadException | DisabledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
}
