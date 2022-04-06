package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class AttendantActions {

    // Blocks the self checkout station by disabling its crucial components
    // Data class being the same instance for all stations should be disgussed in the meating
    public void attendantBlockStation(SelfCheckoutStation station) {
        Data data = Data.getInstance();
        station.baggingArea.disable();
        station.scanningArea.disable();
        station.handheldScanner.disable();
        station.mainScanner.disable();
        station.cardReader.disable();
        data.setIsDisabled(true);
    }

    // Unblocks the self checkout station by enabling its crucial components
    // Could be used to approve a weight discrepancy
    public void attendantUnBlockStation(SelfCheckoutStation station) {
        Data data = Data.getInstance();
        station.baggingArea.enable();
        station.scanningArea.enable();
        station.handheldScanner.enable();
        station.mainScanner.enable();
        station.cardReader.enable();
        data.setIsDisabled(false);
    }

    // Remove scanned item from customer station
    public void removeItem(SelfCheckoutStation station) throws Exception {
    	
    	SelfCheckoutStationSoftware stationSoftware = new SelfCheckoutStationSoftware(station);
    	ElectronicScaleSoftware scaleSoftware = new ElectronicScaleSoftware(station);
    	
    	
    	int count = 0;
    	
    	// Display all items scanned by customer
    	for(ItemInfo item: stationSoftware.itemsScanned) {
    		System.out.println("Item count: " + count + " Item Description: "+ item.description + " Item Price: $" + item.price);
    		count++;
    	}
    	
		Scanner scanner = new Scanner(System.in);

		boolean valid = false;
		int itemNum = 0;
		
		// Choose item to remove
		// Should replace with GUI
		while(!valid) {
			
			// Get input and check if it is valid 
			try {
				System.out.print("Enter Item Number to Remove: ");
				
				// Get input from attendant
				String itemString = scanner.nextLine();
				
				// Try to convert to int
				itemNum = Integer.parseInt(itemString);
				
				// If int selected is greater then the number of items the customer has scanned then it is invalid
				if(itemNum >= stationSoftware.itemsScanned.size()) {
					throw new Exception();
				}
				
				System.out.println("Item #" + itemNum + " was selected");
				valid = true;
			} catch (Exception e) {
				System.out.println("Invalid selection, please try again");
			}
		}
    	
    	// Get item to remove
    	ItemInfo item = stationSoftware.itemsScanned.get(itemNum);
    	
    	// Mark item removed on ElectronicScaleSoftware so the scale works properly once the item is taken off bagging area
    	scaleSoftware.setAttendantRemovedItem();
    	
    	// Remove item
    	stationSoftware.itemsScanned.remove(item);
    	
    	
    }
    
    
}