package org.lsmr.selfcheckout.software;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;

public class BarcodeScannerSoftware implements BarcodeScannerObserver{
	
	//settings
	private double weightThreshold;			//how much weight and item can differ by
	
	//information
	private TestDatabase db;
	private ArrayList<ItemInfo> itemsScanned;
	
	//other software
	private ElectronicScaleSoftware ess;
	
	
	public BarcodeScannerSoftware(TestDatabase db, ElectronicScaleSoftware ess, 
			ArrayList<ItemInfo> itemsScanned, double weightThreshold) {
		this.db = db;
		this.ess = ess;
		this.itemsScanned = itemsScanned;
		this.weightThreshold = weightThreshold;
		
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		//We should be able to ignore this for now
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		//We should be able to ignore this for now
		
	}

	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		ItemInfo info = db.lookupBarcode(barcode);	
		if (info == null) {
			System.out.println("Barcode not scanned properly. Method is now returning");
			return;
		}
		
		//Check if the info.price < 0 (invalid price)
		if (info.price.compareTo(new BigDecimal(0)) < 0) {
			System.err.println("Negative Price Scanned!");
			return;			
		}
		
		//Check if the weight is < 0
		if (info.weight > 0) {
			double weightDiff = ess.getCurrentWeight() - ess.getWeightAtLastEvent();
			if (Math.abs(weightDiff - info.weight) > weightThreshold) {
				System.out.println("Please wait for an attendant");
				//Notify attendant
				//disable the touch screen
			}
		}else{
			System.err.println("Negative weight from Database!");
			return;
		}
		
		
		this.itemsScanned.add(info);				//Add item to the list of scanned item, price too	
		
	}
	
	public ArrayList<ItemInfo> getItemsScanned(){
		return itemsScanned;
	}
	
	public void clearItemsScanned() {
		this.itemsScanned.clear();
	}
	

}
