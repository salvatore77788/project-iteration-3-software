package org.lsmr.selfcheckout.software.test;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.AttendantActions;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

import static org.junit.Assert.*;

public class AttendantActionsTest {

    private SelfCheckoutStation station;
    private AttendantActions attendantActions;

    @Before
    public void setup() {
        // init SelfCheckoutStation
        Currency currency = Currency.getInstance("CAD");
        int[] banknoteDenominations = { 5, 10, 20, 50 };
        BigDecimal[] coinDenominations = { new BigDecimal(0.05), new BigDecimal(0.1), new BigDecimal(0.25),
                new BigDecimal(1.00), new BigDecimal(2.00) };
        int weightLimitInGrams = 100;
        int sensitivity = 1;
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.MONTH, 11);
        expiry.add(Calendar.YEAR, 2022);
        station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, weightLimitInGrams,
                sensitivity);

        // init Attendant Actions
        attendantActions = new AttendantActions();
    }

    @Test
    public void attendantBlockStationTest() {
        attendantActions.attendantBlockStation(station);
        assertTrue(station.baggingArea.isDisabled());
        assertTrue(station.scanningArea.isDisabled());
        assertTrue(station.handheldScanner.isDisabled());
        assertTrue(station.mainScanner.isDisabled());
        assertTrue(station.cardReader.isDisabled());
    }

    @Test
    public void attendantUnBlockStationTest() {
        attendantActions.attendantUnBlockStation(station);
        assertFalse(station.baggingArea.isDisabled());
        assertFalse(station.scanningArea.isDisabled());
        assertFalse(station.handheldScanner.isDisabled());
        assertFalse(station.mainScanner.isDisabled());
        assertFalse(station.cardReader.isDisabled());
    }
}
