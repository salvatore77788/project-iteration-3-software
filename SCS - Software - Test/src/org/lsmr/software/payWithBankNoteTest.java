// SENG 300 Assignment 3
// Group members: Owen Fielder, Joshua Tolentino, Sean Choi, Abdul Bari Mostafa

package org.lsmr.software;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.*;

public class payWithBankNoteTest{
	
	public payWithBankNote test;
   
 	public Currency cTest = SelfCheckoutStationSetup.currency;
 	
 	SelfCheckoutStation station;
 	
 	@Before
 	public void setUp() {
 		station = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
 		
 		test = new payWithBankNote(station);
 	}
 	
 	@Test
 	public void testBanknotePaid() {
 		Banknote bn = new Banknote(cTest, 5);
 		
 		try {
 			station.banknoteInput.accept(bn);
 			
 			assertEquals("Banknote was not paid.", new BigDecimal(5), test.totalPaid);
 		}
 		catch(DisabledException e) {
 			e.printStackTrace();
 		} catch (OverloadException e) {
			e.printStackTrace();
		}
 	}
 	
 	@Test
 	public void testInvalidBanknote() {
 		Banknote bn = new Banknote(Currency.getInstance(Locale.CHINA), 20);
 		
 		try {
 			station.banknoteInput.accept(bn);
 			
 			assertEquals("Banknote was incorrectly accepted.", BigDecimal.ZERO, test.totalPaid);
 		}
 		catch(DisabledException e) {
 			e.printStackTrace();
 		} catch (OverloadException e) {
			e.printStackTrace();
		}
 	}
}