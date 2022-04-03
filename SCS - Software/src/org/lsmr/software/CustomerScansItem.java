package org.lsmr.software;

import java.util.HashMap;
import java.util.ArrayList; 
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;
import org.lsmr.selfcheckout.products.BarcodedProduct;


public class CustomerScansItem implements BarcodeScannerObserver {
	public SelfCheckoutStation station ;  
	public ShoppingCart shopCart;
	public ProductDatabase database;
	
	//To use to determine if item was scanned or not (not necessarily added to cart), 
	//or in the case of multiple quantities of the same item
	private Map<Barcode, Integer> scanStatus = new HashMap<>();
	//private ArrayList<Item> scannedItem = new ArrayList<Item>();
	
	
	
	
	public CustomerScansItem (SelfCheckoutStation s, ShoppingCart c, ProductDatabase d) {
		station = s;
		shopCart = c;
		database = d;
		
		
	}
	
	/**
	 * An event announcing that the indicated barcode has been successfully scanned.
	 * 
	 * @param barcodeScanner
	 *            The device on which the event occurred.
	 * @param barcode
	 *            The barcode that was read by the scanner.
	 */
	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
	    System.out.println("BARCODE SCANNED");
	    
	    Data data = Data.getInstance();
	    
	    if (!data.getIsDisabled())
	        {
	
    		if (scanStatus.get(barcode) == null) {
    			scanStatus.put(barcode, 1);
    		}
    		else {
    			int quantity = scanStatus.get(barcode);
    			scanStatus.replace(barcode, quantity + 1 );
    		}
    		
    		scanItem(barcodeScanner, barcode);
	        }
	}
	
	/*
	 * A scanned item is checked for corresponding product in system and added to cart
	 * 
	 * @param barcodeScanner
	 *            The device on which the event occurred.
	 * @param barcode
	 *            The barcode that was read by the scanner.
	 */
	public void scanItem(BarcodeScanner barcodeScanner, Barcode barcode) {
	
	    Data data = Data.getInstance();
	        
		BarcodedProduct product = null;
		System.out.println("databse: " + database);
		System.out.println("barcode: " + barcode);
		System.out.println("if: " + database.LookupItemViaBarcode(barcode));
		if (database.LookupItemViaBarcode(barcode) != null) {
			product = database.LookupItemViaBarcode(barcode);
		}
		//a barcode that does not map to a product leads to a discrepancy
		if(database.LookupItemViaBarcode(barcode) == null) {	
			barcodeScanner.disable();
			return;
		}
		        
		shopCart.Add(product, product.getExpectedWeight());
		
		//thread to wait 5 seconds 
        data.startBaggingTimer();
         
	}
	
	
	
	
	/*
	 * To get quanity of scanned item
	 * 
	 * @param barcode
	 *            The barcode that was read by the scanner.
	 * @return 
	 * 			Number of times a barcode was scanned
	 */
	public Integer getScanStatus(Barcode barcode) {
		return scanStatus.get(barcode);
	}
	
	public void emptyStatus() {
		scanStatus.clear();
	}
	
	
	/*
	 * To override a block due to a scanning discrepancy
	 * 
	 *  @param barcodeScanner
	 *            The device on which the event occurred.
	 */
	public void overrideBlock(BarcodeScanner barcodeScanner) {
		barcodeScanner.enable();
	}
	

	

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {}


}
