package org.lsmr.selfcheckout.software;

import java.util.List;

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
     * @param storageUnit Coin storage unit to empty.
     */
    public List<Coin> emptyCoinStorageUnit(CoinStorageUnit storageUnit) {
    	List<Coin> storageContents = storageUnit.unload();
    	return storageContents;
    }
    
    
    /*
     * This is for completion's sake. There is unlikely to be a case where an attendant would FILL the storage unit,
     * since the it only depletes when an attendant empties it.
     */
    public void fillCoinStorageUnit(CoinStorageUnit storageUnit, Coin... coins) {
    	try {
    		storageUnit.load(coins);
		} catch (SimulationException e) {
			// Should not happen, since "null" coins don't really exist
			e.printStackTrace();
		} catch (OverloadException e) {
			// Trying to load too many coins
			e.printStackTrace();
		}
    }
    
    /**
     * Simulates the attendant refilling a coin dispenser.
     * @param dispenser Coin dispenser to be refilled.
     * @param coins Array of coins that will be put into the dispenser unit.
     */
    public void fillCoinDispenser(CoinDispenser dispenser, Coin ... coins) {
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
    
    public List<Coin> emptyCoinDispenser(CoinDispenser dispenser) {
    	List<Coin> dispenserContents = dispenser.unload();
    	return dispenserContents;
    }
    
    /**
     * Simulates the attendant emptying a banknote storage unit.
     * @param storageUnit Banknote storage unit to empty.
     */
    public void emptyBanknoteStorageUnit(BanknoteStorageUnit storageUnit) {
    	storageUnit.unload();
    }
    
    /**
     * Simulates the attendant refilling a banknote dispenser.
     * @param dispenser Banknote dispenser to be refilled.
     * @param banknotes Banknotes to load into the dispenser.
     */
    public void fillBanknoteDispenser(BanknoteDispenser dispenser, Banknote ... banknotes) {
    	try {
			dispenser.load(banknotes);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			// Trying to load too many banknotes
			e.printStackTrace();
		}
    }
}