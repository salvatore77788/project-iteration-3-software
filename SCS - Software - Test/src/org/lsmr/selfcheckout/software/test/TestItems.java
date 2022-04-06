package org.lsmr.selfcheckout.software.test;

import java.math.BigDecimal;
import java.util.HashMap;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class TestItems {

	private HashMap<Barcode, BarcodedItem> listOfItems = new HashMap<Barcode, BarcodedItem>();
	private HashMap<Barcode, BarcodedProduct> listOfProducts = new HashMap<Barcode, BarcodedProduct>();

	public TestItems() {

		Barcode barcode = new Barcode(new Numeral[] { Numeral.one });
		BarcodedItem item = new BarcodedItem(barcode, 1200);
		BarcodedProduct product = new BarcodedProduct(barcode, "Dairy Land 2% Milk", new BigDecimal("1.58"), 1);
		listOfItems.put(barcode, item);
		listOfProducts.put(barcode, product);

		barcode = new Barcode(new Numeral[] { Numeral.two });
		item = new BarcodedItem(barcode, 210);
		product = new BarcodedProduct(barcode, "50Pc Disposable Face Mask", new BigDecimal("26.96"), 1);
		listOfItems.put(barcode, item);
		listOfProducts.put(barcode, product);

		barcode = new Barcode(new Numeral[] { Numeral.eight });
		item = new BarcodedItem(barcode, 212);
		product = new BarcodedProduct(barcode,
				"This is a super long line. This is a super long line. This is a super long line",
				new BigDecimal("1226.96"), 1);
		listOfItems.put(barcode, item);
		listOfProducts.put(barcode, product);

		barcode = new Barcode(new Numeral[] { Numeral.nine });
		item = new BarcodedItem(barcode, 2000);
		product = new BarcodedProduct(barcode,
				"Guitar",
				new BigDecimal("60.00"), 1);
		listOfItems.put(barcode, item);
		listOfProducts.put(barcode, product);

	}

	public BarcodedItem lookupItem(Barcode barcode) {
		return listOfItems.get(barcode);
	}

	public BarcodedProduct lookupProduct(Barcode barcode) {
		return listOfProducts.get(barcode);
	}

}
