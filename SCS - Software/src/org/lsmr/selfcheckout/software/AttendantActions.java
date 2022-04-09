package org.lsmr.selfcheckout.software;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
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
    
    /*
     * Should we include a method to disable the 
     * 
     */
    
    
    /**
     * Simulates the attendant emptying a coin storage unit.
     * @param storageUnit Coin storage unit to empty.
     */
    public static void emptyCoinStorageUnit(SelfCheckoutStation station) {
    	station.coinStorage.unload();
    }
    
    /**
     * Simulates the attendant refilling a coin dispenser.
     * @param dispenser Coin dispenser to be refilled.
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
     * @param storageUnit Banknote storage unit to empty.
     */
    public static void emptyBanknoteStorageUnit(SelfCheckoutStation station) {
    	station.banknoteStorage.unload();
    }
    
    /**
     * Simulates the attendant refilling a banknote dispenser.
     * @param dispenser Banknote dispenser to be refilled.
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