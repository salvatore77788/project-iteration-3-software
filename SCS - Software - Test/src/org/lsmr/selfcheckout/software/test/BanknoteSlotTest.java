package org.lsmr.selfcheckout.software.test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.software.BanknoteSlotSoftware;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSetup;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;


/*The reason why we have these as null is because the methods aren't really implemented for this iteration.*/


public class BanknoteSlotTest {

	 private SelfCheckoutStationSoftware software;
	 private BanknoteSlotSoftware bss;
	 private BigDecimal[] amountPaid;

	 private final Currency currency = Currency.getInstance(Locale.CANADA);
	 
	
	@Before
	public void setup() {
		//We give the BanknoteSlotSoftware a reference to this class' variable called amountPaid.
		amountPaid = new BigDecimal[1];
		amountPaid[0] = new BigDecimal("0");
		try {
			software = new SelfCheckoutStationSoftware(SelfCheckoutStationSetup.createSelfCheckoutStationFromInit());
			bss = software.banknoteSlotSoftware;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEnabled() {
		bss.enabled(null);
	}
	
	@Test
	public void testDisabled() {
		bss.disabled(null);
	}
	
	@Test
	public void testBanknotesFull() {
		bss.banknotesFull(null);
	}
	
	@Test
	public void testBanknotesAdded() {
		bss.banknoteAdded(null);
	}
	
	@Test
	public void testBanknotesLoaded() {
		bss.banknotesLoaded(null);
	}

	@Test
	public void testBanknotesUnloaded() {
		bss.banknotesUnloaded(null);
	}
	
	@Test
	public void testmoneyFull() {
		bss.moneyFull(null);
	}
	
	@Test
	public void testBanknotesEmpty() {
		bss.banknotesEmpty(null);
	}
	
	@Test
	public void testBillAdded() {
		bss.billAdded(null, null);
	}
	
	@Test
	public void testBanknoteRemoved() {
		bss.banknoteRemoved(null, null);
	}
	
	//Ends with an underscore because of same name method in BanknoteSlotTest.java
	@Test
	public void testBanknotesLoaded_() {
		bss.banknotesLoaded(null, null);
	}
	
	//Ends with an underscore because of same name method in BanknoteSlotTest.java
	@Test
	public void testBanknotesUnloaded_() {
		bss.banknotesUnloaded(null, null);
	}

	@Test
	public void testInvalidBanknoteDetected() {
		amountPaid[0] = new BigDecimal("0");
		bss.invalidBanknoteDetected(null);
		Assert.assertTrue("Amount paid should be zero", software.getAmountPaid().intValue() == 0);
		amountPaid[0] = new BigDecimal("0");
	}
	
	@Test
	public void testValidBanknoteDetected() {
		amountPaid[0] = new BigDecimal("0");
		int value = 5;
		bss.validBanknoteDetected(null, Currency.getInstance(Locale.CANADA), value);
		Assert.assertTrue("Amount Paid should be" + value, software.getAmountPaid().intValue() == value);
		amountPaid[0] = new BigDecimal("0");
	}
}
