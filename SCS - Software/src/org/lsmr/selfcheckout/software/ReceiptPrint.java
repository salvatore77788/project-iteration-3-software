package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;

public class ReceiptPrint implements ReceiptPrinterObserver {

    private SelfCheckoutStation scs;
    public int inkAmount = 1 << 20;
    public int paperAmount = 1 << 10;
    private boolean noPaper;
    private boolean noInk;
    private boolean addPaper;
    private boolean addInk;
    private boolean lowAmountPaper;
    private boolean lowAmountInk;

    private static final int MAXIMUM_INK = 1 << 20;
    private static final int MAXIMUM_PAPER = 1 << 10;

    public String removePrintedreceipt() {
        return scs.printer.removeReceipt();
    }

    public void detectLowInk(int inkAmount) throws InterruptedException {
        int lowPercentageInk = MAXIMUM_INK % 5;

        if (inkAmount < lowPercentageInk) {
            //this.lowAmountInk = true;
            while (inkAmount < lowPercentageInk) {
                //this.lowAmountInk = true;
                Thread.sleep(30000); //system just waits until attendant verifies the ink is filled
                //System.out.println("The ink amount is 5%, please refill.");
                //AttendantActions.fillInk();
            }
        }
    }

    public void detectLowPaper(int paperAmount) throws InterruptedException {
        int lowPercentagePaper = MAXIMUM_PAPER % 5;

        if (paperAmount < lowPercentagePaper) {
            //this.lowAmountPaper = true;
            while (inkAmount < lowPercentagePaper) {
                //this.lowAmountInk = true;
                Thread.sleep(30000);
                //System.out.println("The paper amount is 5%, please refill.");
                //AttendantActions.fillInk();
                // warn attendant
                // If attendant comes do this:
                // setpaperAmount(MAXIMUM_PAPER);
            }
        }
    }

    // You cannot replace ink by simply pouring more ink into the unit
    // therefore, in order to replace the ink you will have to remove
    // the cartridge and add a new catridge (max allowable ink allowed)
    public void addingInk(int ink) throws OverloadException {
        scs.printer.addInk(ink);
        promptInkAdded(ink);
    }

    // when you replace paper, you have to replace the entire unit
    // receipt paper comes on a roll and cannot be added to an existing roll
    // therefore, when you change the roll you remove the current amount and
    // add the maximum amount allowable (roughly one roll)
    public void addingPaper(int paper) throws OverloadException {
        scs.printer.addPaper(paper);
        promptPaperAdded(paper);
    }

    public void promptPaperAdded(int paperAdded) {
        if (getaddPaper() == true) {
            this.paperAmount += paperAdded;
            setaddPaper(true);
        }
    }

    public void promptInkAdded(int inkAdded) {
        if (getaddInk() == true) {
            this.inkAmount += inkAdded;
            setaddInk(false);
        }
    }

    // PAPER GETTERS AND SETTERS
    public void setpaperAmount(int paperAmount) {
        this.paperAmount = paperAmount;
    }

    public void setaddPaper(boolean state) {
        this.addPaper = state;
    }

    public int getpaperAmount() {
        return this.paperAmount;
    }

    public boolean getaddPaper() {
        return this.addPaper;
    }

    // INK GETTERS AND SETTERS
    public void setinkAmount(int inkAmount) {
        this.inkAmount = inkAmount;
    }

    public void setaddInk(boolean state) {
        this.addInk = state;
    }

    public int getinkAmount() {
        return this.inkAmount;
    }

    public boolean getaddInk() {
        return this.addInk;
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void outOfPaper(ReceiptPrinter printer) {
        noPaper = true;
    }

    @Override
    public void outOfInk(ReceiptPrinter printer) {
        noInk = true;
    }

    @Override
    public void paperAdded(ReceiptPrinter printer) {
        noPaper = false;
        addPaper = true;

    }

    @Override
    public void inkAdded(ReceiptPrinter printer) {
        noInk = false;
        addInk = true;
    }

}
