package org.lsmr.selfcheckout.software.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.software.ItemInfo;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

public class BaggingAreaSoftwareTest {

	TestHardware testHardware;
	SelfCheckoutStationSoftware scss;
	TestItems testItems = new TestItems();
	BarcodedItem milk;
	BarcodedItem facemasks;
	ItemInfo milk_item;
	ItemInfo facemasks_item;

	@Before
	public void setup() throws Exception {
		testHardware = new TestHardware();
		scss = new SelfCheckoutStationSoftware(testHardware.scs);
				
		Barcode barcode1 = new Barcode(new Numeral[] { Numeral.one });
		Barcode barcode2 = new Barcode(new Numeral[] { Numeral.two });
		milk = new BarcodedItem(barcode1, 1200);
		facemasks = new BarcodedItem(barcode2, 210);
		milk_item = new ItemInfo(new BigDecimal("1.58"), milk.getWeight(), "Random description");
		facemasks_item = new ItemInfo(new BigDecimal("26.96"), facemasks.getWeight(), "Random description");
 
	}
	

	@Test
	public void SingleItemTest() throws OverloadException{
//		Assert.assertTrue("Should return true as item hasn't been scanned yet so doesn't need to be in bagging area",
//				scss.checkBaggingAreaItem(milk_item));

		testHardware.scs.mainScanner.scan(milk);

//		Assert.assertFalse("Should return false as item has been scanned but is not in bagging area",
//				scss.checkBaggingAreaItem(milk_item));

		testHardware.scs.baggingArea.add(milk);

//		Assert.assertTrue("Should return true as item has been scanned and is on scale",
//				scss.checkBaggingAreaItem(milk_item));
		boolean expected = false;
		boolean actual = testHardware.scs.mainScanner.isDisabled();
		assertEquals("Item is scanned and is on the scale.",
				expected, actual);
	} 

	@Test
	public void MultipleItemTest() throws OverloadException{

		testHardware.scs.mainScanner.scan(milk);

		testHardware.scs.baggingArea.add(milk);

		testHardware.scs.mainScanner.scan(facemasks);
		
		testHardware.scs.baggingArea.add(facemasks);
		
		boolean expected = false;
		boolean actual = testHardware.scs.mainScanner.isDisabled();
		assertEquals("item is above the sensitivity.",
				expected, actual);
	}
	
	@Test
	public void scanButDoNotPlace() throws OverloadException{

		testHardware.scs.mainScanner.scan(milk);

		boolean expected = true;
		boolean actual = testHardware.scs.mainScanner.isDisabled();
		assertEquals("item is above the sensitivity.",
				expected, actual);
	}
	
	@Test
	public void scan2ButDoNotPlace2nd() throws OverloadException{

		testHardware.scs.mainScanner.scan(milk);
		testHardware.scs.baggingArea.add(milk);
		testHardware.scs.mainScanner.scan(facemasks);

		boolean expected = true;
		boolean actual = testHardware.scs.mainScanner.isDisabled();
		assertEquals("item is above the sensitivity.",
				expected, actual);
	}
}
