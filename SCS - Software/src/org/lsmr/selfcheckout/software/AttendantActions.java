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
	/* 
	 * !!! A list of selcheckout stations can be retrived from the SupervisionStation class if needed
	 * */
	
	// Make a static array of selfcheckout stations
	/* 
	 * !!! Again an array of selfcheckout stations exist in  SupervisionStation class but I believe it to be redundant to 
	 * implement it here
	 * */

	
    // Blocks the self checkout station by disabling its crucial components
    // Data class being the same instance for all stations should be disgussed in the meating
    public static void blockStation(SelfCheckoutStation station) {
        station.baggingArea.disable();
        station.scanningArea.disable();
        station.handheldScanner.disable();
        station.mainScanner.disable();
        station.cardReader.disable();
    }

    // Unblocks the self checkout station by enabling its crucial components
    // Could be used to approve a weight discrepancy
    public static void unBlockStation(SelfCheckoutStation station) {
        station.baggingArea.enable();
        station.scanningArea.enable();
        station.handheldScanner.enable();
        station.mainScanner.enable();
        station.cardReader.enable();
    }
    
    /*
     * Should we include a method to disable the 
     * 
     */
    
    
    /**
     * Simulates the attendant emptying a coin storage unit.
     * @param station SelfCheckoutStation whose coin storage is to be unloaded.
     */
    public static void emptyCoinStorageUnit(SelfCheckoutStation station) {
    	station.coinStorage.unload();
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
    public static void emptyBanknoteStorageUnit(SelfCheckoutStation station) {
    	station.banknoteStorage.unload();
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