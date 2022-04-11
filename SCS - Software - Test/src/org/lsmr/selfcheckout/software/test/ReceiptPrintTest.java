package org.lsmr.selfcheckout.software.test;

import org.lsmr.selfcheckout.software.ElectronicScaleSoftware;
import org.lsmr.selfcheckout.software.ReceiptPrint;
import org.lsmr.selfcheckout.software.AttendantStation;
import org.lsmr.selfcheckout.software.AttendantActions;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Numeral;

import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware.*;


import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ReceiptPrintTest {
	
	private TestHardware testHardwarePrint = new TestHardware();
	private SelfCheckoutStationSoftware software;
	private AttendantStation atstat;
	private AttendantActions att = new AttendantActions();
	
	
	@Before
	public void setupPrint() throws Exception {
		software = new SelfCheckoutStationSoftware(testHardwarePrint.scs);
		//testHardwarePrint.scs.printer.addInk(1 << 20);
		//testHardwarePrint.scs.printer.addPaper(1 << 10);
		
		software.startScanGUI();

		TestItems testItems = new TestItems();
		BarcodedItem milkItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.one }));
		testHardwarePrint.scs.mainScanner.scan(milkItem);
		testHardwarePrint.scs.mainScanner.scan(testItems.lookupItem(new Barcode(new Numeral[] { Numeral.eight })));

		// In the simulation scan the milk item
		Banknote bn = new Banknote(Currency.getInstance(Locale.CANADA), 100);
		testHardwarePrint.scs.banknoteValidator.accept(bn);
	}
	
	
	@Test (expected=EmptyException.class)
	public void testEmptyInk() throws EmptyException, OverloadException, InterruptedException
	{
		software.rp.setpaperAmount(1<<10);
		software.rp.detectLowInk(software.rp.getInkAmount());
		software.checkout();
	}	
	
	@Test (expected = EmptyException.class)
	public void testLowInk() throws InterruptedException, EmptyException, OverloadException
	{
		software.rp.setpaperAmount(1<<10);
		software.rp.setinkAmount(0);
		software.checkout();
		
	}
	
	@Test (expected = EmptyException.class)
	public void testEmptyPaper() throws InterruptedException, EmptyException, OverloadException
	{
		software.rp.setinkAmount(1<<20);
		software.rp.setpaperAmount(0);
		software.checkout();
		
	}
	
	@Test (expected = EmptyException.class)
	public void testLowPaper() throws InterruptedException, EmptyException, OverloadException
	{
		software.rp.setinkAmount(1<<20);
		software.rp.setpaperAmount(1);
		software.checkout();
		
	}
	
	
	@Test
	public void testAttnFill() throws Exception
	{
		software.rp.setinkAmount(1<<20);
		software.rp.setpaperAmount(1);
		assertTrue("ink amount expected (1<<20)", software.rp.getInkAmount() == 1<<20 );		
	}
}
