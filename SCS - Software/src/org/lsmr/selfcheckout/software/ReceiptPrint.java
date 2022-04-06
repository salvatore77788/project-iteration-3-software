package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;

public class ReceiptPrint implements ReceiptPrinterObserver {

    private SelfCheckoutStation scs;
    private int inkAmount;
    private int paperAmount;
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

    public void detectLowInk(int inkLevel) {
        int lowPercentageInk = MAXIMUM_INK % 10;

        if (inkLevel < lowPercentageInk) {
            this.lowAmountInk = true;
            System.out.println("The ink amount is 10%, please refill.");
        }
    }

    public void detectLowPaper(int paperLevel) {
        int lowPercentagePaper = MAXIMUM_PAPER % 10;

        if (paperLevel < lowPercentagePaper) {
            this.lowAmountPaper = true;
            System.out.println("The paper amount is 10%, please refill.");
        }
    }

    // You cannot replace ink by simply pouring more ink into the unit
    // therefore, in order to replace the ink you will have to remove
    // the cartridge and add a new catridge (max allowable ink allowed)
    public void addingInk(int ink) throws OverloadException {
        scs.printer.addInk(ink);
        promptInkAdded(ink);
    }

    // equivalent to of a paper roll
    public int paperRoll() {
        return MAXIMUM_PAPER;
    }

    // equivalent of a new ink cartridge
    public int inkCartridge() {
        return MAXIMUM_INK;
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

    public boolean getlowAmountPaper() {
        return this.lowAmountPaper;
    }

    public int getpaperAmount() {
        return this.paperAmount;
    }

    public boolean getaddPaper() {
        return this.addPaper;
    }

    public boolean getNoPaper() {
        return this.noPaper;
    }

    // INK GETTERS AND SETTERS
    public void setinkAmount(int inkAmount) {
        this.inkAmount = inkAmount;
    }

    public void setaddInk(boolean state) {
        this.addInk = state;
    }

    public boolean getlowAmountInk() {
        return this.lowAmountInk;
    }

    public int getinkAmount() {
        return this.inkAmount;
    }

    public boolean getaddInk() {
        return this.addInk;
    }

    public boolean getNoInk() {
        return this.noInk;
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
