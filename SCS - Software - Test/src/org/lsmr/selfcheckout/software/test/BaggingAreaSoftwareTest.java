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
		Assert.assertTrue("Should return true as item hasn't been scanned yet so doesn't need to be in bagging area",
				scss.checkBaggingAreaItem(milk_item));

		testHardware.scs.mainScanner.scan(milk);

		Assert.assertFalse("Should return false as item has been scanned but is not in bagging area",
				scss.checkBaggingAreaItem(milk_item));

		testHardware.scs.baggingArea.add(milk);

		Assert.assertTrue("Should return true as item has been scanned and is on scale",
				scss.checkBaggingAreaItem(milk_item));
	} 

	@Test
	public void MultipleItemTest() throws OverloadException{

		Assert.assertTrue("Should return true as no items have been scanned so bagging area expected to be empty",
				scss.checkBaggingAreaAll());

		testHardware.scs.mainScanner.scan(milk);
		testHardware.scs.mainScanner.scan(facemasks);

		Assert.assertFalse("Should return false as items have been scanned and not placed in bagging area",
				scss.checkBaggingAreaAll());

		testHardware.scs.baggingArea.add(milk);

		Assert.assertFalse("Should return false as two items have been scanned and only one added to bagging area",
				scss.checkBaggingAreaAll());

		testHardware.scs.baggingArea.add(facemasks);

		Assert.assertTrue("Should return true as both scanned items have been placed in bagging area",
				scss.checkBaggingAreaAll());
	}
}
