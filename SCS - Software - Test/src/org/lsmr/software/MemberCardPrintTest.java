package org.lsmr.software;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.MagneticStripeFailureException;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class MemberCardPrintTest {

	SelfCheckoutStation station;
	ShoppingCartReceiptPrinter printerObs;
	CardData membership;
	MembersDatabase membersdataB;
	MembershipCardObserver membercardObserver;
	Card testCard;
	
	// membership number MembershipCardObserver 
	
	@BeforeClass
	public static void setUpBeforeClass() {
		// Set up a test product database
		Barcode bc = new Barcode(new Numeral[] {Numeral.zero});
		
		BarcodedProduct[] products = new BarcodedProduct[] {
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.zero}), "Box of Biscuits", new BigDecimal("10.99"), 500),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.one}), "Milk 2% 4L", new BigDecimal("5.29"), 4000),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.two}), "Peanut Butter", new BigDecimal("7.49"),1000),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.three}), "Quinoa", new BigDecimal("12.99"),1800),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.four}), "Nutella", new BigDecimal("4.97"),725),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "Margarine", new BigDecimal("4.99"),1360),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.six}), "Basmati Rice", new BigDecimal("16.97"),4000),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.seven}), "Cream Cheese", new BigDecimal("3.47"),250),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.eight}), "Ketchup", new BigDecimal("3.97"),272),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.nine}), "Instant Coffee", new BigDecimal("11.99"),300),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.one,Numeral.zero}), "Condensed Milk", new BigDecimal("1.69"),310),
				
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
		
		// The printer observer is here.
		printerObs = new ShoppingCartReceiptPrinter(station);
		
		//  Creating a test member database.
		membersdataB = new MembersDatabase();
		
		
		// Creating a test Card
		// 						type, number, cardholder, cvv, pin, isTapEnabled, hasChip
		testCard = new Card("member","51207832","Tom", "321", "1",false, true);
		
		// Notice that I'm only using the card number and the name.
		membersdataB.members.put("51207832", "Tom");
		
		// the cardReader inside station will get attached to this instance
		// of card observer
		membercardObserver = new MembershipCardObserver(station, printerObs,membersdataB);
		
		ShoppingCart.getInstance().Empty();
	}
	
	
	// A normal test, where the receipt is printed. (short receipt)
	@Test
	public void simpleMemberReceipt() throws SimulationException {
		// Load paper and ink
		station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		
		Barcode bc = createBarcodeFromString("0");
		// where Add takes a Product and productWeight.
		ShoppingCart.getInstance().Add(ProductDatabase.Instance.LookupItemViaBarcode(bc), 0);
		
		boolean swipedWorked = false;

		do {
			
			try {
				station.cardReader.swipe(testCard);
				swipedWorked = true;
				
				//System.out.println("Passed");
			} catch (IOException e) {
				//System.out.println("Failed");
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				continue;
			}
			
		}while(!swipedWorked);
		
		printerObs.printReceipt();
		
		String receipt = station.printer.removeReceipt();
		
		System.out.println(receipt);
		assertEquals("Number of lines not correct", 6, countLines(receipt));
	}
	

	// A normal test, where the receipt is printed. (medium size receipt)
	@Test
	public void memberPrintReceipt() throws SimulationException {
		// Load paper and ink
		station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		
		
		float[] WeightofItem = {500,4000,1000,1800,725,1360,4000,250,272,300,310};
		
		// Adding items to ShoppingCart.
		for(int i = 0; i <11; i++) { 
		  // Create Barcode and add it. 
		  Barcode bc = createBarcodeFromString(String.valueOf(i));
		  ShoppingCart.getInstance().Add(ProductDatabase.Instance.LookupItemViaBarcode(bc), WeightofItem[i]); // Change String name. // barcode = "item"+
		  
		  }
	
		boolean swipedWorked = false;
		
		
		do {
			
			try {
				station.cardReader.swipe(testCard);
				swipedWorked = true;
				
				//System.out.println("Passed");
			} catch (IOException e) {
				//System.out.println("Failed");
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				continue;
			}
			
		}while(!swipedWorked);
		
		printerObs.printReceipt();
		
		String receipt = station.printer.removeReceipt();
		
		System.out.println(receipt);
		
		// how did you get the number of lines  to equal 6?
		// there was one for box of biscuits with is added as Barcode 0
		// then there was a simple line 
		// The third line contained the total.
		// With the membership card number, I'm now having a total of 6. (3 more)
		// a line (___)
		// Line with Member number
		// one last line (\n) which is just empty.
		assertEquals("Number of lines not correct", 16, countLines(receipt));
	}
	
	
	
	
	
	// Simulating that the card is being swiped multiple times until
	// the Magnetic Stripe Fails.
	@Test(expected = MagneticStripeFailureException.class)
	public void memberTestPrintReceipt2() throws SimulationException, MagneticStripeFailureException {
		// Load paper and ink
		station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		
		Barcode bc = createBarcodeFromString("0");
		ShoppingCart.getInstance().Add(ProductDatabase.Instance.LookupItemViaBarcode(bc), 0);
	
		boolean swipedWorked = true;
		while(swipedWorked) {
			try { 
				  station.cardReader.swipe(testCard); 
				  } 
			catch (Exception e){
				  swipedWorked = false;
				  throw new MagneticStripeFailureException(); 
				  }
		}
		
		
		printerObs.printReceipt();
		 
		String receipt = station.printer.removeReceipt();
		
		System.out.println(receipt);
		
		
		// how did you get the number of lines  to equal 6?
		// there was one for box of biscuits with is added as Barcode 0
		// then there was a simple line 
		// The third line contained the total.
		// With the membership card number, I'm now having a total of 6. (3 more)
		// a line (___)
		// Line with Member number
		// one last line (\n) which is just empty.
		assertEquals("Number of lines not correct", 6, countLines(receipt));
	}
	
	
	// This test is meant to ascertain that if the card swiped was not 
	// "registered" in our database then it will simply not print anything
	// in the receipt.
	@Test
	public void notA_member() throws MagneticStripeFailureException{
		// Load paper and ink
		station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		
		Barcode bc = createBarcodeFromString("0");
		// where Add takes a Product and productWeight.
		ShoppingCart.getInstance().Add(ProductDatabase.Instance.LookupItemViaBarcode(bc), 500);
		
		Card testCard2 = new Card("member","38507146","Bob", "456", "2",false, false);
		
		boolean swipedWorked = false;
		
		
		do {
			
			try {
				station.cardReader.swipe(testCard2);
				swipedWorked = true;
				
				//System.out.println("Passed");
			} catch (IOException e) {
				//System.out.println("Failed");
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				continue;
			}
			
		}while(!swipedWorked);
		
		printerObs.printReceipt();
		
		String receipt = station.printer.removeReceipt();
		
		System.out.println(receipt);
	    assertEquals("Number of lines not correct", 3, countLines(receipt));
	}
	
	
	// If you tap a card, then it was not a member card
	// based on the assumption that member cards are only swiped.
	// When the receipt prints, it should not display a "Member Number:"
	@Test
	public void differentClassCardData() throws SimulationException {
		// Load paper and ink
		station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		
		Barcode bc = createBarcodeFromString("8");
		// where Add takes a Product and productWeight.
		ShoppingCart.getInstance().Add(ProductDatabase.Instance.LookupItemViaBarcode(bc), 0);
		
		Card testCard3 = new Card("member","38507146","Bob", "456", "2",true, true);
		
		boolean tapWorked = false;

		do {
			
			try {
				station.cardReader.tap(testCard3);
				//station.cardReader.swipe(testCard);
				tapWorked = true;
				
				//System.out.println("Passed");
			} catch (IOException e) {
				//System.out.println("Failed");
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				continue;
			}
			
		}while(!tapWorked);
		
		printerObs.printReceipt();
		
		String receipt = station.printer.removeReceipt();
		
		System.out.println(receipt);
		assertEquals("Number of lines not correct", 3, countLines(receipt));
	}
	
	
	// The test card will be of type debit
	// The receipt should not have any Member Number shown.
	@Test
	public void differentTypeCard() throws SimulationException {
		// Load paper and ink
		station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		
		Barcode bc = createBarcodeFromString("9");
		// where Add takes a Product and productWeight.
		ShoppingCart.getInstance().Add(ProductDatabase.Instance.LookupItemViaBarcode(bc), 0);
		
		Card testCard3 = new Card("debit","38507146","Luke", "456", "2",true, true);
		
		boolean swipedWorked = false;

		do {
			
			try {
				
				station.cardReader.swipe(testCard3);
				swipedWorked = true;
				
				//System.out.println("Passed");
			} catch (IOException e) {
				//System.out.println("Failed");
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				continue;
			}
			
		}while(!swipedWorked);
		
		printerObs.printReceipt();
		
		String receipt = station.printer.removeReceipt();
		
		System.out.println(receipt);
		assertEquals("Number of lines not correct", 3, countLines(receipt));
	}
	
	
	

	// Utility methods. 
	
	private long countLines(String str) {
		// str.char returns a stream of integer code point values.
		// these are then filtered such that if it's \n, it'll be 
		// added to the count. i'm guessing the +1 is for the 
		// the last invisible \n at the end of the file.?
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
