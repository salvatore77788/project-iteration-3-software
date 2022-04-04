package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.software.BarcodeScannerSoftware;
import org.lsmr.selfcheckout.software.ElectronicScaleSoftware;
import org.lsmr.selfcheckout.software.ItemInfo;
import org.lsmr.selfcheckout.software.TestDatabase;

public class BarcodeScannerSoftwareTest 
{
	private ElectronicScaleSoftware ess = new ElectronicScaleSoftware();
	private BarcodeScanner bs = new BarcodeScanner();

	private ItemInfo info;
	private AbstractDevice<?> device;
	private Barcode barcode;
	private DatabaseStub dbs;
	private BarcodeScannerSoftware bss;
	
	private ArrayList<ItemInfo> itemsScanned;
	
	// Stub class for ItemInfo
	public class ItemInfoStub extends ItemInfo
	{
		BigDecimal price;
		double weight;
		String description;
		
		protected ItemInfoStub(BigDecimal price, double weight, String description, boolean infoNull)
		{
			super(price, weight, description);
			this.price = price;
			this.weight = weight;
			this.description = description;
		}
    }
	
	// Stub for TestDatabase
	public class DatabaseStub extends TestDatabase
	{
		ItemInfoStub infoStub;
		
		protected DatabaseStub(BigDecimal price, double weight, String description, boolean infoNull)
		{
			super(); 
			infoStub = new ItemInfoStub(price, weight, description, infoNull);
			
			if (infoNull)
					infoStub = null;
		}
				
        @Override
        public ItemInfoStub lookupBarcode(Barcode barcode) 
        {
        	return infoStub;
        }
	}
	
	@Before
	public void setup()
	{
		itemsScanned = new ArrayList<ItemInfo>();
		info = new ItemInfo(new BigDecimal(10), 25, "Someitem");
		itemsScanned.add(info);
		bss = new BarcodeScannerSoftware(dbs, ess, itemsScanned, 1000);
	}
	
	@Test
	public void testGetItemsScanned()
	{
		itemsScanned = bss.getItemsScanned();
		assertTrue(itemsScanned.contains(info));
	}
	
	@Test
	public void testClearItemsScanned()
	{
		bss.clearItemsScanned();
		assertTrue(itemsScanned.isEmpty());
	}
	
	@Test
	public void testEnabled()
	{
		bss.enabled(device);
	}

	@Test
	public void testDisabled()
	{
		bss.disabled(device);
	}
	
	@Test
	public void testBarcodeScanned()
	{	
		// Standard test with price = 10, weight = 50, description = "Milk", infoNull = false
		// with weightThreshold = 0 for one of the branches at line 63 (in BarcodeScannerSoftware)
		dbs = new DatabaseStub(new BigDecimal("10"), 50, "Milk", false);
		bss = new BarcodeScannerSoftware(dbs, ess, itemsScanned, 0);
		barcode = new Barcode(new Numeral[] {Numeral.one});
		bss.barcodeScanned(bs, barcode);
		assertTrue(itemsScanned.contains(dbs.infoStub));
		
		// Standard test but with weightThreshold = 100000 for the other the branches at line 63
		dbs = new DatabaseStub(new BigDecimal("10"), 50, "Milk", false);
		bss = new BarcodeScannerSoftware(dbs, ess, itemsScanned, 100000);
		barcode = new Barcode(new Numeral[] {Numeral.one});
		bss.barcodeScanned(bs, barcode);
		assertTrue(itemsScanned.contains(dbs.infoStub));

		// Standard test but with negative weight
		dbs = new DatabaseStub(new BigDecimal("1.00"), -1, "Negative Weight", false);
		bss = new BarcodeScannerSoftware(dbs, ess, itemsScanned, 0);
		barcode = new Barcode(new Numeral[] {Numeral.one});
		bss.barcodeScanned(bs, barcode);
		// Cannot have assertTrue here since an item doesn't get added (line 74 in BarcodeScannerSoftware)
		// because the tested weight in this case is negative
		assertFalse(itemsScanned.contains(dbs.infoStub));

		
		// Standard test but with negative price
		dbs = new DatabaseStub(new BigDecimal("-23.00"), 123, "Negative Price", false);
		bss = new BarcodeScannerSoftware(dbs, ess, itemsScanned, 0);
		barcode = new Barcode(new Numeral[] {Numeral.one});
		bss.barcodeScanned(bs, barcode);
		// Cannot have assertTrue here since an item doesn't get added (line 74 in BarcodeScannerSoftware)
		// because the tested price in this case is negative
		assertFalse(itemsScanned.contains(dbs.infoStub));

		// Standard test but infoNull = true
		dbs = new DatabaseStub(new BigDecimal("3.21"), 12, "", true);
		bss = new BarcodeScannerSoftware(dbs, ess, itemsScanned, 0);
		barcode = new Barcode(new Numeral[] {Numeral.one});
		bss.barcodeScanned(bs, barcode);
		// Cannot have assertTrue here since an item doesn't get added (line 74 in BarcodeScannerSoftware)
		// because the item to be added is null
		assertFalse(itemsScanned.contains(dbs.infoStub));
	}
}
