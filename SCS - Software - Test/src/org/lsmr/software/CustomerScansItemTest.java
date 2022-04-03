package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.Product;
import org.lsmr.software.ShoppingCart.ShoppingCartEntry;




public class CustomerScansItemTest {
	
	CustomerScansItem scan;
	//Set up for the self checkout station
	ProductDatabase database = ProductDatabase.Instance;
	ShoppingCart cart = ShoppingCart.Instance;
	Currency currency = Currency.getInstance("CAD");
	int[] banknoteDenominations = {1, 2, 5, 10};
	BigDecimal[] coinDenominations = {BigDecimal.TEN};
	SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 10, 2);
	
	
	@Before
	public void setUp() {
		scan = new CustomerScansItem(station, cart, database);
		station.mainScanner.attach(scan);
		station.mainScanner.enable();;
		
		cart.Empty();
		scan.emptyStatus();
		database.clearDatabase();
	
	}
	
	//Should block scanning if item barcode does not have corresponding product in system
	@Test
	public void barcodeHasNoProductTest() {
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		station.mainScanner.scan(item);
	
		assertTrue(station.mainScanner.isDisabled());
		
	}
	
	
	//Should add product to cart if item barcode matches product barcode in system
	@Test
	public void addProductToCartTest() {
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		
		BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99), 1);
		database.RegisterProducts(product);
		
		station.mainScanner.scan(item);
		
		
		List<ShoppingCartEntry> cartEntries = cart.getEntries();
		assertFalse(cartEntries.isEmpty());
		 	
	}
	
	//Further scanning after a block should not be permitted
	@Test
	public void addProductToCartAfterBlockTest() {
		Numeral[] n1 = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b1 = new Barcode(n1);
		BarcodedItem item1 = new BarcodedItem(b1, 2.0);
		station.mainScanner.scan(item1);
		
		Numeral[] n2 = {Numeral.three, Numeral.two, Numeral.one};
		Barcode b2 = new Barcode(n2);
		BarcodedItem item2 = new BarcodedItem(b2, 2.0);
		station.mainScanner.scan(item2);
		
		assertEquals(scan.getScanStatus(b2), null);
		
	}
	
	//Item with the same barcode should be able to be scanned multiple times
	@Test
	public void scanDuplicateProducts() {    
		Numeral[] n1 = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b1 = new Barcode(n1);
		BarcodedItem item1 = new BarcodedItem(b1, 2.0);
		BarcodedProduct product = new BarcodedProduct(b1, "a product", BigDecimal.valueOf(1.99), 1);
		database.RegisterProducts(product);
		
		BarcodedItem item2 = new BarcodedItem(b1, 2.0);
		
		station.mainScanner.scan(item1);
		
		station.mainScanner.scan(item2);
		
		assertEquals(cart.getEntries().size(), 2);
		
	}
	
	//Scanning should work properly after a block override
	@Test
	public void scanAfterBlockOveriddenTest() {
		station.mainScanner.disable();
		scan.overrideBlock(station.mainScanner);
		
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		
		BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99), 1);
		database.RegisterProducts(product);
		
		station.mainScanner.scan(item);
		
		
		List<ShoppingCartEntry> cartEntries = cart.getEntries();
	
		assertEquals(cartEntries.size(), 1);
			
	 
	}
	
	
	//Multiple items should be able to be scanned and put into the shopping cart
	@Test
	public void multipleProductTest() {
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		
		BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99), 1);
		database.RegisterProducts(product);
		
		station.mainScanner.scan(item);
		
		Numeral[] n1 = {Numeral.two, Numeral.two, Numeral.three};
		Barcode b1 = new Barcode(n1);
		BarcodedItem item1 = new BarcodedItem(b1, 3.0);

		BarcodedProduct product1 = new BarcodedProduct(b1, "a product", BigDecimal.valueOf(1.99), 1);
		database.RegisterProducts(product1);
		
		station.mainScanner.scan(item1);

		assertEquals(cart.getEntries().size(), 2);
		
		
	}

}
