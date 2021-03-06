package org.lsmr.selfcheckout.software.test;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.software.ReceiptPrint;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class Testing {

	public static void main(String[] args) throws Exception {

		Testing test = new Testing();
		// test.testBarcodeScannerSoftware();

		// test.testBanknoteValidator();
		test.testCheckout();
		System.exit(0);
		// test.testReceiptPrinter();

		// test.testRegularCash();
		// test.testReceiptPrinter();

		// test.testRegularCash();
	}

	public Testing() {

	}

	private void testStorageFull() throws Exception {
		Currency currency = Currency.getInstance(Locale.CANADA);

		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);

		TestItems testItems = new TestItems();
		BarcodedItem milkItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.one }));
		testHardware.scs.mainScanner.scan(milkItem);
		testHardware.scs.mainScanner.scan(testItems.lookupItem(new Barcode(new Numeral[] { Numeral.two })));

		Coin c1 = new Coin(currency, new BigDecimal("0.05"));
		Coin c2 = new Coin(currency, new BigDecimal("0.10"));
		Banknote bn = new Banknote(Currency.getInstance(Locale.CANADA), 100);

		testHardware.scs.banknoteValidator.accept(bn);
		testHardware.scs.coinValidator.accept(c1);
		testHardware.scs.coinValidator.accept(c2);

		software.checkout();

	}

	private void testRegularCash() throws Exception {

		Currency currency = Currency.getInstance(Locale.CANADA);

		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);

		TestItems testItems = new TestItems();
		BarcodedItem milkItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.one }));
		testHardware.scs.mainScanner.scan(milkItem);
		testHardware.scs.mainScanner.scan(testItems.lookupItem(new Barcode(new Numeral[] { Numeral.two })));

		Coin c1 = new Coin(currency, new BigDecimal("0.05"));
		Coin c2 = new Coin(currency, new BigDecimal("0.10"));
		Banknote bn = new Banknote(Currency.getInstance(Locale.CANADA), 100);

		testHardware.scs.banknoteValidator.accept(bn);
		testHardware.scs.coinValidator.accept(c1);
		testHardware.scs.coinValidator.accept(c2);

		software.checkout();

	}

	private void testCheckout() throws Exception {
		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);

		ReceiptPrint ReceiptPrint = new ReceiptPrint(software.scs);

		int paper = ReceiptPrint.paperAmount;
		int ink = ReceiptPrint.inkAmount;
		System.out.println(ink);
		System.out.println(paper);
		testHardware.scs.printer.addInk(ink);
		testHardware.scs.printer.addPaper(paper);

		TestItems testItems = new TestItems();
		BarcodedItem milkItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.one }));
		testHardware.scs.mainScanner.scan(milkItem);
		testHardware.scs.mainScanner.scan(testItems.lookupItem(new Barcode(new Numeral[] { Numeral.two })));

		// In the simulation scan the milk item
		Banknote bn = new Banknote(Currency.getInstance(Locale.CANADA), 100);
		testHardware.scs.banknoteValidator.accept(bn);

		// To replicate how the member number would show in the receipt.
		software.setMemberCardNumber("85052420");

		software.checkout();

	}

	private void testReceiptPrinter() throws Exception {
		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);

		ReceiptPrint ReceiptPrint = new ReceiptPrint(software.scs);

		int paper = ReceiptPrint.paperAmount;
		int ink = ReceiptPrint.inkAmount;

		testHardware.scs.printer.addInk(ink);
		testHardware.scs.printer.addPaper(paper);

		TestItems testItems = new TestItems();
		BarcodedItem milkItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.one }));
		testHardware.scs.mainScanner.scan(milkItem);
		testHardware.scs.mainScanner.scan(testItems.lookupItem(new Barcode(new Numeral[] { Numeral.eight })));

		// In the simulation scan the milk item
		Banknote bn = new Banknote(Currency.getInstance(Locale.CANADA), 100);
		testHardware.scs.banknoteValidator.accept(bn);

		software.checkout();

	}

	private void testBanknoteValidator() throws Exception {
		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);

		// In the simulation scan the milk item
		Banknote bn = new Banknote(Currency.getInstance(Locale.CANADA), 10);
		testHardware.scs.banknoteValidator.accept(bn);
	}

	private void testBarcodeScannerSoftware() throws Exception {
		TestHardware testHardware = new TestHardware();
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);

		// In the simulation scan the milk item
		TestItems testItems = new TestItems();
		BarcodedItem milkItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.one }));
		testHardware.scs.mainScanner.scan(milkItem);
		testHardware.scs.mainScanner.scan(testItems.lookupItem(new Barcode(new Numeral[] { Numeral.two })));

		System.out.println(software.total());
	}
}