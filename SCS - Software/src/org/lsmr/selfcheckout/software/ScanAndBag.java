package org.lsmr.selfcheckout.software;

import java.util.Map;
import java.util.Timer;

import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.observers.*;
import org.lsmr.selfcheckout.products.*;
import org.lsmr.selfcheckout.software.gui.ScanningItemGUI;

import java.util.ArrayList;

/**
 * Class ScanAndBag that represents the use case of when the customer scans and bags the items.
 * @version updated to implement customer adding and using their own bags
 *
 */
public class ScanAndBag implements ElectronicScaleObserver, BarcodeScannerObserver{
	
	private double latestItemWeight;	// Keeps track of the latest item's weight that is placed on the scale
	private double scannedItemWeight;	// Keeps track of the latest item's weight that has been scanned
	private double latestScaleWeight;	// Keeps track of the scale's latest known weight 
	private double scaleSensitivity;	// Keeps track of the scale sensitivity
	private double scaleWeightLimit; 	//Keeps track of the scale weight limit
	private boolean systemLock;	// Boolean that keeps track of whether or not the system is locked
	private static ScanAndBag scanNbag;
	private int sensitiveWeights;		// Keeps track of the total weight that has been placed on the scale since weightChanged()
	private BarcodeScanner mainScanner, handheldScanner;	// References to the scanners
//	private Map <Barcode,BarcodedProduct> hashMapProduct;	// HashMap of Products
//	private Map <Barcode,BarcodedItem> hashMapItem;			// HashMap of Items
//	private ArrayList<Product> theProducts = new ArrayList<Product>();	// An arrayList that keeps track of scanned products
//	private ArrayList<Item> theItems = new ArrayList<Item>();	// An arrayList that keeps track of scanned items
	private static Timer t = new java.util.Timer();
	private boolean addingBags;
	private boolean addingOwnBags;
	private int bagsInStock = 2;
	private int storeOwnedBagsUsed = 0;
	private double currentWeight;
	private double weightAtLastEvent;
//	private int storeBagWeightInGrams;
//	private ArrayList<Bag> bags = new ArrayList<Bag>();
	
	//variables from old code
	private TestDatabase db;
	private ArrayList<ItemInfo> itemsScanned;
	
	public ScanningItemGUI scanGUI;
	
	private boolean attendantRemoved = false; 
	
	/**
	 * Constructor for the class ScanAndBag 
	 * The constructor, the checkout station and the respective hashMaps for the Items and Products should be passed into it
	 * 
	 * @param theStation the SelfCheckoutStation used
	 * @param productMap the hashMap for products used 
	 * @param itemMap the hashMap for items used
	 */
	public ScanAndBag(SelfCheckoutStation theStation, TestDatabase db, SelfCheckoutStationSoftware scss)
	{
		this.currentWeight = 0;
		this.weightAtLastEvent = 0;
		this.db = db;
		ScanAndBag.scanNbag = this;
		this.systemLock = false;
		addingBags = false;
//		storeBagWeightInGrams = 8;
		this.mainScanner = theStation.mainScanner;
		this.handheldScanner = theStation.handheldScanner;
		this.scannedItemWeight = 0;

		this.sensitiveWeights = 0;
		this.itemsScanned = new ArrayList<ItemInfo>();
		this.scaleSensitivity = theStation.baggingArea.getSensitivity();
		this.scaleWeightLimit = theStation.baggingArea.getWeightLimit();
//		this.hashMapItem = itemMap;
//		this.hashMapProduct = productMap;
		try {
			this.latestScaleWeight = theStation.baggingArea.getCurrentWeight();
//			this.overloaded = false;
		} catch (OverloadException e) {
			this.mainScanner.disable();
			this.handheldScanner.disable();
//			this.overloaded = true;
		}
		
		scanGUI = new ScanningItemGUI(itemsScanned, scss);
		scanGUI.setVisible(true);
		
	}
	
	public void continueGUI() {
		scanGUI.setVisible(true);
	}
	
	// FOR TESTING PURPOSES
	// ERASE AFTER
	public void addToList(ItemInfo i) {
		itemsScanned.add(i);
		scanGUI.addItem();
	}
	
	// Determines whether or not to lock the system after 5 seconds.
	public static void fiveSecondCheck(){
		if(ScanAndBag.scanNbag.mainScanner.isDisabled()) {
			ScanAndBag.scanNbag.systemLock = true;
		}
	}
	
	// returns whether or not the system is locked
	public boolean isLocked() {
		return this.systemLock;
	}

	// Setter method for the systemlock state
	public void setLocked(Boolean bool) {
		this.systemLock = bool;
	}
	
	// Represents the attendant removing the lock from the system.
	public void bringOutOfLock()
	{
		this.systemLock = false;
		ScanAndBag.t.schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                ScanAndBag.fiveSecondCheck();
		            }
		        }, 
		        5000 
		);
	}
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		
		System.out.println("The device has been enabled");
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		
		System.out.println("The device has been disabled");
		
	}

	// Whenever an item is added to the scale and it is the latest scanned item 
	// this method will enable the scanner so that further scans can be made.
	@Override
	public void weightChanged(ElectronicScale scale, double weightInGrams)
	{
		if(this.systemLock) {
			System.out.println("Please wait for the attendant!");
			scanGUI.wrongWeightOn();
			return;
		} 
		this.weightAtLastEvent = this.latestItemWeight;
		if (latestScaleWeight < weightInGrams) {
			// Something is added
			latestItemWeight = (weightInGrams - (latestScaleWeight + sensitiveWeights));
			sensitiveWeights = 0;
			
			if (addingBags) {
				bagAdded(scale, weightInGrams);
			} else {
				itemAdded(scale);
			}
		}
		else {
			// Case where attendant removed item
			if(attendantRemoved) {
				
				// Get new weight
				latestItemWeight = weightInGrams;
				
				// Set new weight at last event
				this.weightAtLastEvent = latestItemWeight;
				
				// Reset boolean 
				setAttendantRemoved(false);
			} else {
				
				// Prompt wrong weight and alert attendant
				scanGUI.wrongWeightOn();
			}
			
		}
		
		
		latestScaleWeight = weightInGrams;
	}
	
	private void itemAdded(ElectronicScale scale) {
		double epsilon = 0.3;
		if((Math.abs(latestItemWeight - scannedItemWeight) < epsilon) && (latestScaleWeight <= scale.getWeightLimit()))
		{
			mainScanner.enable();
			handheldScanner.enable();
		}
		else {
			mainScanner.disable();
			handheldScanner.disable();
		}
	}
	
	private void bagAdded(ElectronicScale scale, double weightInGrams) {
		
		if (!addingBags) {
			systemLock = true;
		}
		
		if (addingOwnBags) {
			
		} else {
			double epsilon = 0.0001;
			if (Math.abs(latestScaleWeight - weightInGrams) < epsilon) {
				//valid bag
				
				if (bagsInStock > 0) {
					//bags still in stock
					storeOwnedBagsUsed++;
					bagsInStock--;
				} else {
					//out of stock
					systemLock = true;
				}
				
			} else {
				//invalid bag
				systemLock = true;
			}
		}
	}

	@Override
	public void overload(ElectronicScale scale) {
		if(systemLock) {
			System.out.println("Please wait for the attendant!");
			scanGUI.wrongWeightOn();
			return;
		}
		
		mainScanner.disable();
		handheldScanner.disable();
		System.out.println("Excessive weight has been placed, please remove!");
		
	}

	// Whenever the Item causing overload is removed from the scale this method
	// will enable the scanner so that new scans can be made.
	@Override
	public void outOfOverload(ElectronicScale scale) {
		if(this.systemLock) {
			System.out.println("Please wait for the attendant!");
			scanGUI.wrongWeightOn();
		}
		else 
		{
			ScanAndBag.t.cancel();
			mainScanner.enable();
			handheldScanner.enable();
			System.out.println("The excessive weight has been removed, you can continue scanning.");
		}
	}


	// Whenever a barcode is scanned this method will fetch the corresponding item and product information.
	// With the item data, weight will be acquired so that it can be used to evaluate whether or not an item
	// is placed on the scale. Product information will be passed to an arrayList to enable further use by
	// other methods. This class will also disable the scanner if the scanned item weight is not less than the
	// sensitivity so that a new item is not scanned before the requirements conserning the scale are not met.
	

	
	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		
		ItemInfo info = db.lookupBarcode(barcode);
//		BarcodedItem item1 = hashMapItem.get(barcode);
//		BarcodedProduct product1 = hashMapProduct.get(barcode);
//		theProducts.add(product1);	
//		theItems.add(item1);
		this.scannedItemWeight = info.weight;
		
		if (scannedItemWeight <= scaleSensitivity + 0.3)
		{
			//this.itemsScanned.add(info);
			sensitiveWeights += scannedItemWeight;
		}
		else {
			this.itemsScanned.add(info);
			scanGUI.addItem();
			mainScanner.disable();
			handheldScanner.disable();
			ScanAndBag.t.schedule( 
			        new java.util.TimerTask() {		// Schedules a fiveSecondCheck so that the system can be locked if no items are placed at the end of 5 seconds.
			            @Override
			            public void run() {
			                ScanAndBag.fiveSecondCheck();
			            }
			        }, 
			        5000 
			);
	
		}
		
	}
	
	// Get method so that other classes can get the scanned products.
	public ArrayList<ItemInfo> getItemsScanned(){
		return itemsScanned;
	}

	// Clears the list of scanned items
	public void clearItemsScanned() {
		this.itemsScanned.clear();
	}
	
//	// Get method so that other classes can get the scanned products.
//	public ArrayList<Product> getTheProducts()
//	{
//		return theProducts;
//	}
//	
//	// Get method so that other classes can get the scanned items.
//	public ArrayList<Item> getTheItems()
//	{
//		return theItems;
//	}
//	
	
	//a method to represent when the customer wants to start adding bags
	public void startAddingBags(boolean includeOwnBags) {
		addingOwnBags = includeOwnBags;
		addingBags = true;
		mainScanner.disable();
		handheldScanner.disable();
	}
	
	//a method to represent when the customer is done adding bags
	public void stopAddingBags() {
		addingBags = false;
		mainScanner.enable();
		handheldScanner.enable();
	}
	
	//a method to restock bags
	public void restockBags(int numberOfBags) throws SimulationException {
		if (numberOfBags > 0) {
			bagsInStock += numberOfBags;
		} else {
			// throw new SimulationException("Cannot restock negative bags!");
		}
	}
	
	public void setAttendantRemoved(boolean bool) {
		attendantRemoved = bool;
	}
	
	//getter method for bags in stock
	public int getBagsInStock() {
		return bagsInStock;
	}
	
	//getter method for number of store owned bags used
	public int getStoreOwnedBagsUsed() {
		return storeOwnedBagsUsed;
	}

	public double getCurrentWeight() {
		return this.latestScaleWeight;
	}
	
	public double getWeightAtLastEvent() {
		return this.weightAtLastEvent;
	}

}