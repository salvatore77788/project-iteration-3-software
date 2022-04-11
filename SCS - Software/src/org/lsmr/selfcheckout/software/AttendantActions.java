package org.lsmr.selfcheckout.software;

import java.util.Scanner;

import java.util.List;

import java.math.BigDecimal;


import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.IllegalErrorPhaseSimulationException;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class AttendantActions {

	// We need references to selfcheckout stations
	// Make a static array of selfcheckout stations

    // Blocks the self checkout station by disabling its crucial components
    // Data class being the same instance for all stations should be discussed in the meeting
    public void attendantBlockStation(SelfCheckoutStation station) {
       // Data data = Data.getInstance(); //commented out for errors thrown during testing for print
        station.baggingArea.disable();
        station.scanningArea.disable();
        station.handheldScanner.disable();
        station.mainScanner.disable();
        station.cardReader.disable();
     //   data.setIsDisabled(true); //commented out for errors thrown during testing for print
    }

    // Unblocks the self checkout station by enabling its crucial components
    // Could be used to approve a weight discrepancy
    public void attendantUnBlockStation(SelfCheckoutStation station) {
      //  Data data = Data.getInstance(); //commented out for errors thrown during testing for print
        station.baggingArea.enable();
        station.scanningArea.enable();
        station.handheldScanner.enable();
        station.mainScanner.enable();
        station.cardReader.enable();
       // data.setIsDisabled(false); //commented out for errors thrown during testing for print
    }

    public void fillInk(ReceiptPrint printer, int amount) throws OverloadException {
    	printer.setinkAmount(amount);
    }
    public void fillPaper(ReceiptPrint printer, int amount) throws OverloadException {
        printer.setpaperAmount(amount);
    }

    // Remove scanned item from customer station
    // Needs the software of the station that has the data stored
    public void removeItem() throws Exception {

      Scanner scanner = new Scanner(System.in);
      boolean exist = false;

      int stationNumber = 0;

      // Choose station
      // Should replace with GUI
      while(!exist) {

        // Get input and check if it is valid
        try {
          System.out.print("Enter Station Number: ");

          // Get input from attendant
          String itemString = scanner.nextLine();

          // Try to convert to int
          stationNumber = Integer.parseInt(itemString);

          // Check if that station number exists
          for(SelfCheckoutStationSoftware s: AttendantStation.softwareStationConnected) {
            if(s.stationNumber == stationNumber) {
              exist = true;
              break;
            }
          }

          // Station does not exist
          if(exist == false) {
            throw new Exception();
          }

          System.out.println("Station #" + stationNumber + " was selected");

        } catch (Exception e) {
          System.out.println("Invalid selection, please try again");
        }
      }

      // Get the software for the right station
        SelfCheckoutStationSoftware stationSoftware = AttendantStation.softwareStationConnected.get(stationNumber);
        ElectronicScaleSoftware scaleSoftware = AttendantStation.electronicScaleConnected.get(stationNumber);

        int count = 0;

        // Display all items scanned by customer
        for(ItemInfo item: stationSoftware.itemsScanned) {
          System.out.println("Item count: " + count + " Item Description: "+ item.description + " Item Price: $" + item.price);
          count++;
        }


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
    /*
     * Should we include a method to disable the
     */

    /*
     * DISTINCTION BETWEEN DISPENSER AND STORAGE UNIT
     *
     * DISPENSER:
     * 	- the attendant loads coins/banknotes
     *  - if the customer is supposed to receive change, this is where they receive it from
     *
     * STORAGE UNIT:
     *  - banknotes/coins come from customers
     *  - must be emptied by the attendant
     */


    /**
     * Simulates the attendant emptying a coin storage unit.
     * @param station SelfCheckoutStation whose coin storage is to be unloaded.
     */
    public static List<Coin> emptyCoinStorageUnit(SelfCheckoutStation station) {
    	List<Coin> storageContents = station.coinStorage.unload();

    	// This should be displayed on the touch screen.
    	BigDecimal totalValue = new BigDecimal(0);
    	for (int i = 0; i < storageContents.size(); i++) {
    		totalValue.add(storageContents.get(i).getValue());
    	}
    	System.out.printf("$%d removed from banknote storage unit.\n", totalValue);

    	return storageContents;
    }

    /**
     * Simulates the attendant refilling a coin dispenser.
     * @param station SelfCheckoutStation that is being refilled.
     * @param denom The coin denomination of the coin dispenser to refill.
     * @param coins Array of coins that will be put into the dispenser unit.
     */
    public static void fillCoinDispenser(SelfCheckoutStation station, BigDecimal denom, Coin ... coins) {
    	CoinDispenser dispenser = station.coinDispensers.get(denom);

    	// Assume the coins are correct
    	try {
			dispenser.load(coins);
		} catch (SimulationException e) {
			// Should not happen, since "null" coins don't really exist
			e.printStackTrace();
		} catch (OverloadException e) {
			// Trying to load too many coins
			e.printStackTrace();
		}
    }

    /**
     * Simulates the attendant emptying a banknote storage unit.
     * @param station SelfCheckoutStation whose banknote storage is to be unloaded.
     */
    public static List<Banknote> emptyBanknoteStorageUnit(SelfCheckoutStation station) {
    	List<Banknote> storageContents = station.banknoteStorage.unload();

    	// This should be displayed on the touch screen.
    	int totalValue = 0;
    	for (int i = 0; i < storageContents.size(); i++) {
    		totalValue += storageContents.get(i).getValue();
    	}
    	System.out.printf("$%d removed from banknote storage unit.\n", totalValue);

    	return storageContents;
    }

    /**
     * Simulates the attendant refilling a banknote dispenser.
     * @param station SelfCheckoutStation that is being refilled.
     * @param denom The banknote denomination of the banknote dispenser to refill.
     * @param banknotes Banknotes to load into the dispenser.
     */
    public static void fillBanknoteDispenser(SelfCheckoutStation station, int denom, Banknote ... banknotes) {
    	BanknoteDispenser dispenser = station.banknoteDispensers.get(denom);
    	try {
			dispenser.load(banknotes);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			// Trying to load too many banknotes
			e.printStackTrace();
		}
    }
}
