package org.lsmr.selfcheckout.software;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.CoinTray;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CoinDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.CoinStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.CoinTrayObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;

public class CoinSlotSoftware
        implements CoinDispenserObserver, CoinStorageUnitObserver, CoinTrayObserver, CoinValidatorObserver {
	
	private SelfCheckoutStationSoftware software;
    
    public CoinSlotSoftware(SelfCheckoutStationSoftware software) {
    	this.software = software;
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {

        // TODO Auto-generated method stub

    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {

        // TODO Auto-generated method stub

    }

    /*
     * A valid coin is detected therefore coin gets processed and customer can
     * continue their checkout.
     */
    @Override
    public void validCoinDetected(CoinValidator validator, BigDecimal value) {
        software.addAmountPaid(value);
    }

    /*
     * An invalid coin is detected therefore customer receives back the coin and has
     * to insert a valid one before continuing their checkout.
     */
    @Override
    public void invalidCoinDetected(CoinValidator validator) {

        System.err.println("Our system has detected an invalid coin, please insert a valid coin.");

    }

    /*
     * System has detected a new coin in the cointray.
     */
    @Override
    public void coinAdded(CoinTray tray) {

        // TODO Auto-generated method stub

    }

    /*
     * System has detected that coin storage is full therefore, administrators need
     * to empty the station.
     */
    @Override
    public void coinsFull(CoinStorageUnit unit) {

        System.err.println("Coin storage is currently full, please empty the station.");

    }

    /*
     * System has detected a new coin addition, therefore administrators can check
     * how many there are in the storage
     */
    @Override
    public void coinAdded(CoinStorageUnit unit) {

        // TODO Auto-generated method stub

    }

    /*
     * System has detected new coin loading process, which means administrators are
     * loading coins to the storage
     */
    @Override
    public void coinsLoaded(CoinStorageUnit unit) {

        // TODO Auto-generated method stub

    }

    /*
     * System has detected a new coin unloading process, which means administrators
     * are unloading coins from to the storage
     */
    @Override
    public void coinsUnloaded(CoinStorageUnit unit) {

        // TODO Auto-generated method stub

    }

    /*
     * System has detected that coin dispenser is full, which means there should not
     * be any more emit coins at the moment.
     */
    @Override
    public void coinsFull(CoinDispenser dispenser) {

        // TODO Auto-generated method stub

    }

    /*
     * System has detected that coin dispenser is empty, which means there is no
     * issue.
     */
    @Override
    public void coinsEmpty(CoinDispenser dispenser) {

        // TODO Auto-generated method stub

    }

    /*
     * System has detected that coin addition to dispenser, which means system can
     * proceed the calculation for checkout.
     * 
     */
    @Override
    public void coinAdded(CoinDispenser dispenser, Coin coin) {

        // TODO Auto-generated method stub

    }

    /*
     * System has detected that coin removing process from the dispenser, which
     * means system can tell us when a coin is removed, also remove the price of the
     * coin from the calculation.
     * 
     */
    @Override
    public void coinRemoved(CoinDispenser dispenser, Coin coin) {

    }

    /*
     * System has detected that coin loading process to the dispenser, which
     * means system can tell us when a coin is loaded.
     * 
     */
    @Override
    public void coinsLoaded(CoinDispenser dispenser, Coin... coins) {

        // TODO Auto-generated method stub

    }

    /*
     * System has detected that coin unloading process to the dispenser, which
     * means system can tell us when a coin is unloaded, also remove the price of
     * the coin from the calculation.
     * 
     */
    @Override
    public void coinsUnloaded(CoinDispenser dispenser, Coin... coins) {

        // TODO Auto-generated method stub

    }

}
