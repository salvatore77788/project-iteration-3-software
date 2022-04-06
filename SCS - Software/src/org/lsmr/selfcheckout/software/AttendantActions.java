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

}