package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.Product;
import org.lsmr.software.ShoppingCart.ShoppingCartEntry;

public class ScanAfterPartialPaymentTest{
	
	private Checkout checkoutStation;
	public PayWithCoin testCoinPayment;
	CustomerScansItem scan;
	ProductDatabase database = ProductDatabase.Instance;
	ShoppingCart cart = ShoppingCart.Instance;
	Currency currency = Currency.getInstance("CAD");
	int[] banknoteDenominations = {1, 2, 5, 10};
	BigDecimal[] coinDenominations = {new BigDecimal("0.05")};
	SelfCheckoutStation station = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
	
	@Before
	public void setup() {
		checkoutStation = new Checkout(station);
		testCoinPayment = new PayWithCoin(station);
		
		scan = new CustomerScansItem(station, cart, database);
		station.handheldScanner.attach(scan);
		station.handheldScanner.enable();
		
		PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(station);
        station.baggingArea.attach(baggingArea);
        station.mainScanner.attach(scan);
        station.mainScanner.enable();

        BarcodedItem testItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), 2000);
		
		cart.Empty();
		scan.emptyStatus();
		database.clearDatabase();
	}
	
	@Test
	public void returnToScanningTest() {
		checkoutStation.wouldLikeToCheckOut(100);
		checkoutStation.returnToScanning();
		assertFalse(station.mainScanner.isDisabled());
		assertFalse(station.handheldScanner.isDisabled());
		assertFalse(station.baggingArea.isDisabled());
		assertFalse( station.scanningArea.isDisabled());
		assertTrue(station.coinValidator.isDisabled());
		assertTrue(station.banknoteValidator.isDisabled());
	}
	
	@Test
	public void scanItemAfterPartialPaymentTest() {
		checkoutStation.wouldLikeToCheckOut(100);
		
 		BigDecimal value = coinDenominations[0];
        Coin testCoin = new Coin (currency, value);
        try {
        	station.coinSlot.accept(testCoin);
        }
		catch (Exception NullPointerException) {
			checkoutStation.returnToScanning();
			
			Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
			Barcode b = new Barcode(n);
			BarcodedItem item = new BarcodedItem(b, 2.0);
			double arbitraryWeight = 2;
			
			BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99), arbitraryWeight);
			database.RegisterProducts(product);
			
			station.mainScanner.scan(item);
			
			List<ShoppingCartEntry> cartEntries = cart.getEntries();
			
			assertFalse(cartEntries.isEmpty());
			assertFalse(station.mainScanner.isDisabled());
			assertFalse(station.handheldScanner.isDisabled());
			assertFalse(station.baggingArea.isDisabled());
			assertFalse( station.scanningArea.isDisabled());
			assertTrue(station.coinValidator.isDisabled());
			assertTrue(station.banknoteValidator.isDisabled());
		}		
	}
}