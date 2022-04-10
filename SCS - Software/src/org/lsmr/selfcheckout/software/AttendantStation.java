
package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import java.util.ArrayList;



public class AttendantStation {

	public static ArrayList<SelfCheckoutStation> stationConnected = new ArrayList<SelfCheckoutStation>();
	public static ArrayList<SelfCheckoutStationSoftware> softwareStationConnected = new ArrayList<SelfCheckoutStationSoftware>();
	public static ArrayList<ElectronicScaleSoftware> electronicScaleConnected = new ArrayList<ElectronicScaleSoftware>();
	public static ArrayList<BarcodeScannerSoftware> barcodeScannerConnected = new ArrayList<BarcodeScannerSoftware>();
	public static ArrayList<BanknoteSlotSoftware> banknoteSlotConnected = new ArrayList<BanknoteSlotSoftware>();
	public static ArrayList<ScanAndBag> scanAndBagConnected = new ArrayList<ScanAndBag>();
	public static int numOfStations = 0;
	
	public AttendantStation() {
		// TODO Auto-generated constructor stub
	}

	
    // Connect the station and software to the attendant machine
    public void connectToAttendantStation(SelfCheckoutStation scs, SelfCheckoutStationSoftware stationSoftware, ElectronicScaleSoftware scaleSoftware,
    		BarcodeScannerSoftware barcodeScannerSoftware, BanknoteSlotSoftware banknoteSlotSoftware, ScanAndBag scanAndBag) {
    	
    	stationConnected.add(scs);
    	softwareStationConnected.add(stationSoftware);
    	electronicScaleConnected.add(scaleSoftware);
    	barcodeScannerConnected.add(barcodeScannerSoftware);
    	banknoteSlotConnected.add(banknoteSlotSoftware);
    	scanAndBagConnected.add(scanAndBag);
    }
    
    public void testBag(ScanAndBag s) {
    	scanAndBagConnected.add(s);
    }
	
	public void BagsAdded(ElectronicScale scale) {
		scale.enable();
	}
	
	public int assignStationNumber() {
		numOfStations++;
		return numOfStations;
	}
	
	public int getNumberOfStations() {
		return numOfStations;
	}
	
	
}
