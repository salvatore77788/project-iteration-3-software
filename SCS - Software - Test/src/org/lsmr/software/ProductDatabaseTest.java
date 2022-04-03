package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class ProductDatabaseTest {
	@Before
	public void setUp() {
		ProductDatabase.Instance.clearDatabase();
	}
	
	@Test
	public void testRegisterProduct() {
		Barcode barcode = new Barcode(new Numeral[] {Numeral.one, Numeral.eight, Numeral.nine });
		BarcodedProduct product = new BarcodedProduct(barcode, "Test product", new BigDecimal(10.95), 1);
		
		ProductDatabase db = ProductDatabase.Instance;
		
		db.RegisterProducts(product);
		
		BarcodedProduct dbProduct = db.LookupItemViaBarcode(barcode);
		
		assertEquals("Looked up product not equal registered product", product, dbProduct);
	}
	
	@Test
	public void testRegisterMultipleProducts() {
		final int NUM_PRODUCTS = 10;
		ProductDatabase db = ProductDatabase.Instance;
		
		BarcodedProduct[] products = new BarcodedProduct[NUM_PRODUCTS];
		
		for(int i = 0; i < NUM_PRODUCTS; i++) {
			Barcode bc = new Barcode(new Numeral[] {Numeral.valueOf((byte)i)});
			products[i] = new BarcodedProduct(bc, "Test product", new BigDecimal(1.00), 1);
		}
		
		db.RegisterProducts(products);
		
		assertEquals("Incorrect number of products.", NUM_PRODUCTS, db.getNumberOfProducts());
		
		// Can in-depth check if needed
	}

}
