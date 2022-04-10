package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

public class ReturnChangeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	// testing if change is returned when a banknote and a coin needs to be emitted.
	// testing based on number of bag used = 0.
	@Test
	public void testCheckoutChangeWithBanknoteAndCoin() throws Exception {
		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);
		software.setMemberCardNumber("12345");

		testHardware.scs.printer.addInk(10000);
		testHardware.scs.printer.addPaper(300);
		;

		TestItems testItems = new TestItems();
		BarcodedItem milkItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.one }));
		testHardware.scs.mainScanner.scan(milkItem);
		testHardware.scs.mainScanner.scan(testItems.lookupItem(new Barcode(new Numeral[] { Numeral.two })));

		// In the simulation scan the milk item
		Banknote bn = new Banknote(Currency.getInstance(Locale.CANADA), 100);
		testHardware.scs.banknoteValidator.accept(bn);

		software.checkout();
		BigDecimal actual = software.getAmountReturned();
		BigDecimal expected = BigDecimal.valueOf(71.45);

		assertTrue(expected.equals(actual));

	}

	// testing if change is returned when only a banknote needs to be emitted.
	// testing based on number of bag used = 0.
	@Test
	public void testCheckoutChangeWithBanknote() throws Exception {
		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);
		software.setMemberCardNumber("12345");

		testHardware.scs.printer.addInk(10000);
		testHardware.scs.printer.addPaper(300);
		;

		TestItems testItems = new TestItems();
		BarcodedItem guitarItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.nine }));
		testHardware.scs.mainScanner.scan(guitarItem);

		// In the simulation scan the milk item
		Banknote bn = new Banknote(Currency.getInstance(Locale.CANADA), 100);
		testHardware.scs.banknoteValidator.accept(bn);

		software.checkout();
		BigDecimal actual = software.getAmountReturned();
		BigDecimal expected = new BigDecimal("40");
		assertTrue(expected.equals(actual));

	}

	// testing if change is returned when only a coin needs to be emitted.
	// testing based on number of bag used = 0.
	@Test
	public void testCheckoutChangeWithCoin() throws Exception {
		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);
		software.setMemberCardNumber("12345");

		testHardware.scs.printer.addInk(10000);
		testHardware.scs.printer.addPaper(300);
		;

		TestItems testItems = new TestItems();
		BarcodedItem milkItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.one }));
		testHardware.scs.mainScanner.scan(milkItem);

		// In the simulation scan the milk item
		Coin c = new Coin(Currency.getInstance(Locale.CANADA), new BigDecimal("2.00"));
		testHardware.scs.coinValidator.accept(c);

		software.checkout();
		BigDecimal actual = software.getAmountReturned();
		BigDecimal expected = new BigDecimal("0.40");
		assertTrue(expected.equals(actual));

	}

	// testing if change is returned when only a coin needs to be emitted.
	// testing based on number of bag used = 0. // NEEDS TO BE LOOKED AT.
	@Test
	public void testCheckoutChangeWithCoin2() throws Exception {
		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);
		software.setMemberCardNumber("12345");

		testHardware.scs.printer.addInk(10000);
		testHardware.scs.printer.addPaper(300);
		;

		TestItems testItems = new TestItems();
		BarcodedItem guitarItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.nine }));
		testHardware.scs.mainScanner.scan(guitarItem);

		// In the simulation scan the milk item
		Banknote bn = new Banknote(Currency.getInstance(Locale.CANADA), 10);
		testHardware.scs.banknoteValidator.accept(bn);

		software.checkout();
		BigDecimal actual = software.getAmountReturned();
		BigDecimal expected = new BigDecimal(40.00);
		assertTrue(expected.equals(actual));

	}

	// testing if change is returned when a banknote and a coin needs to be emitted.
	// testing based on number of bag used = 0.
	@Test
	public void testCheckoutChangeWithBanknoteAndCoin2() throws Exception {
		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);
		software.setMemberCardNumber("12345");

		testHardware.scs.printer.addInk(10000);
		testHardware.scs.printer.addPaper(300);
		;

		TestItems testItems = new TestItems();
		BarcodedItem maskItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.two }));
		testHardware.scs.mainScanner.scan(maskItem);

		// In the simulation scan the milk item
		Banknote bn = new Banknote(Currency.getInstance(Locale.CANADA), 100);
		testHardware.scs.banknoteValidator.accept(bn);

		software.checkout();
		BigDecimal actual = software.getAmountReturned();
		BigDecimal expected = BigDecimal.valueOf(73.05);

		assertTrue(expected.equals(actual));

	}

}
