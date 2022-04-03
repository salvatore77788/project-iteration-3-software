package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;
public class ShoppingCartReceiptPrinterTest {
	SelfCheckoutStation station;
	ShoppingCartReceiptPrinter printer;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		// Set up a test product database
		//Barcode bc = new Barcode(new Numeral[] {Numeral.zero});
		
		BarcodedProduct[] products = new BarcodedProduct[] {
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.zero}), "Box of Biscuits", new BigDecimal("10.99"), 1),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.one}), "Milk 2% 4L", new BigDecimal("4.88"), 1),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.two}), "Magnesium 150mg", new BigDecimal("6.99"), 1),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.three}), "GTX 3090", new BigDecimal("0.99"), 1)
		};
		ProductDatabase.Instance.RegisterProducts(products);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		ProductDatabase.Instance.clearDatabase();
	}

	@Before
	public void setUp() throws Exception {
		station = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
		printer = new ShoppingCartReceiptPrinter(station);
		
		ShoppingCart.getInstance().Empty();
	}

	@Test
	public void testPrintSimpleReceipt() {
		// Load paper and ink
		try {
			station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Barcode bc = createBarcodeFromString("0");
		ShoppingCart.getInstance().Add(ProductDatabase.Instance.LookupItemViaBarcode(bc), 0);
		
		printer.printReceipt();
		
		String receipt = station.printer.removeReceipt();
		
		System.out.println(receipt);
		
		assertEquals("Number of lines not correct", 3, countLines(receipt));
	}
	
	@Test
	public void testPrintEmptyShoppingCartReceipt() {
		try {
			station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printer.printReceipt();
		
		String receipt = station.printer.removeReceipt();
		
		System.out.println(receipt);
		assertEquals("Number of lines not correct", 2, countLines(receipt));
	}
	
	@Test
	public void testFullShoppingCartReceipt() {
		try {
			station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < 8; i++) {
			Barcode bc = createBarcodeFromString(Integer.toString(i%4));
			ShoppingCart.getInstance().Add(ProductDatabase.Instance.LookupItemViaBarcode(bc), 0);
		}
		
		printer.printReceipt();
		
		String receipt = station.printer.removeReceipt();
		System.out.println(receipt);
		assertEquals("Number of lines incorrect", 10, countLines(receipt));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testOutOfPaper() {
		try {
			station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			station.printer.addPaper(1);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printer.printReceipt();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testOutOfInk() {
		try {
			station.printer.addInk(5);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printer.printReceipt();
		
		String receipt = station.printer.removeReceipt();
		System.out.println(receipt);
	}
	
	private long countLines(String str) {
		return str.chars().filter(c -> c == '\n').count() + 1;
	}
	
	private Barcode createBarcodeFromString(String barcode) {
		Numeral[] numerals = new Numeral[barcode.length()];
		
		for(int i = 0; i < numerals.length; i++) {
			Byte b = Byte.valueOf(Character.toString(barcode.charAt(i)));
			numerals[i] = Numeral.valueOf(b);
		}
		
		return new Barcode(numerals);
	}

}
