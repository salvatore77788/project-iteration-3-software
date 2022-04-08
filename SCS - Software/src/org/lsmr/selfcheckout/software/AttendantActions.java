package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class AttendantActions {

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
    
    public void fillInk(ReceiptPrint printer) throws OverloadException {
    	printer.setinkAmount(1<<20);
    }
    public void fillPaper(ReceiptPrint printer) throws OverloadException {
        printer.setpaperAmount(1<<10);
    }

}