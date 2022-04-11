package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.*;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.software.gui.ProductCatalogGUI;

public class ProductCatalogGUITest {
	ProductCatalogGUI gui;
	
	@Before
	public void setUp() throws Exception {
		gui = new ProductCatalogGUI();
	}

	@Test
	public void testSelectBarcodedProductByPressingButton() {
		// Product to try to select
		Barcode bc = new Barcode(new Numeral[] {Numeral.zero, Numeral.zero, Numeral.zero, Numeral.eight});
		BarcodedProduct prod = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(bc);
		
		assertNotNull("Barcode product is null.", prod);
		
		String productName = prod.getDescription();
		// Need to find the correct button to press
		for(JButton button : gui.productButtons) {
			if(button.getText().contains(productName)) {
				button.doClick();
				
				assertEquals("Selected product incorrect.", prod, gui.selectedProduct);
			}
		}
	}
	
	@Test
	public void testSelectPLUProductByPressingButton() {
		// Product to try to select
		PLUCodedProduct prod = ProductDatabases.PLU_PRODUCT_DATABASE.get(new PriceLookupCode("7681"));
		
		assertNotNull("PLU product is null.", prod);
		
		String productName = prod.getDescription();
		// Need to find the correct button to press
		for(JButton button : gui.productButtons) {
			if(button.getText().contains(productName)) {
				button.doClick();
				
				assertEquals("Selected product incorrect.", prod, gui.selectedProduct);
			}
		}
	}
	
	@Test
	public void testGoBackButton() {
		gui.jButtonGoBack.doClick();
		
		assertTrue("Go back was not pressed.", gui.wasGoBackPressed);
	}
}
