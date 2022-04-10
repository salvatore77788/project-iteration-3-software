package org.lsmr.selfcheckout.software;

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
        station.baggingArea.disable();
        station.scanningArea.disable();
        station.handheldScanner.disable();
        station.mainScanner.disable();
        station.cardReader.disable();
    }

    // Unblocks the self checkout station by enabling its crucial components
    // Could be used to approve a weight discrepancy
    public void attendantUnBlockStation(SelfCheckoutStation station) {
        station.baggingArea.enable();
        station.scanningArea.enable();
        station.handheldScanner.enable();
        station.mainScanner.enable();
        station.cardReader.enable();
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