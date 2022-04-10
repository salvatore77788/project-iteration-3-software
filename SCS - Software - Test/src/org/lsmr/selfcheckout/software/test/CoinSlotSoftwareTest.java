package org.lsmr.selfcheckout.software.test;

import java.util.*;

import static org.junit.Assert.assertTrue;

import java.math.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.CoinTray;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.software.CoinSlotSoftware;
import org.lsmr.selfcheckout.software.ItemInfo;
import org.lsmr.selfcheckout.software.TestDatabase;

public class CoinSlotSoftwareTest {

    private BigDecimal[] amountPaid;
    private AbstractDevice<?> device;
    private CoinSlotSoftware css;
    private Currency cur = Currency.getInstance(Locale.CANADA);
    private CoinValidator cv;
    private List<BigDecimal> coinDenominations = new ArrayList<BigDecimal>();
    private CoinTray tray;
    private CoinStorageUnit unit;
    private CoinDispenser dispenser;
    private Coin coin;
    private Coin coins;


    @Before
    public void setup() {
        amountPaid = new BigDecimal[1];
        amountPaid[0] = BigDecimal.ZERO;
        css = new CoinSlotSoftware(amountPaid);
        coinDenominations.add(new BigDecimal(0.05));
        coinDenominations.add(new BigDecimal(0.10));
        coinDenominations.add(new BigDecimal(0.25));
        coinDenominations.add(new BigDecimal(0.50));
        coinDenominations.add(new BigDecimal(1.00));
        coinDenominations.add(new BigDecimal(2.00));
        cv = new CoinValidator(cur, coinDenominations);

    }

    @Test
    public void testEnabled(){
        css.enabled(device);
    }

    @Test
    public void testDisabled(){
        css.disabled(device);
    }

    @Test
    public void testValidCoinDetected() {
        amountPaid[0] = new BigDecimal("0");
        BigDecimal value = new BigDecimal("0.5");
        css.validCoinDetected(null, value);
        Assert.assertEquals("Amount Paid should be" + value, 0, amountPaid[0].compareTo(value));
        amountPaid[0] = new BigDecimal("0");
    }

    @Test
    public void testInvalidCoinDetected() {
        css.invalidCoinDetected(cv);
    }

    @Test
    public void testCoinAdded() {
        css.coinAdded(tray);
    }

    @Test
    public void testCoinsFull() {
        css.coinsFull(unit);
    }

    @Test
    public void testCoinAddedStorage() {
        css.coinAdded(unit);
    }

    @Test
    public void testCoinsLoaded() {
        css.coinsLoaded(unit);
    }

    @Test
    public void testCoinsUnloaded() {
        css.coinsUnloaded(unit);
    }

    @Test
    public void testCoinsFullDispenser() {
        css.coinsFull(dispenser);
    }

    @Test
    public void testCoinsEmpty() {
        css.coinsEmpty(dispenser);
    }

    @Test
    public void testCoinAddedDispenserCoin() {
        css.coinAdded(dispenser, coin);
    }

    @Test
    public void testCoinRemoved() {
        css.coinRemoved(dispenser, coin);
    }

    @Test
    public void testCoinsLoadedDispenser() {
        css.coinsLoaded(dispenser, coins);
    }

    @Test
    public void testCoinsUnloadedDispenser() {
        css.coinsUnloaded(dispenser, coins);
    }

}