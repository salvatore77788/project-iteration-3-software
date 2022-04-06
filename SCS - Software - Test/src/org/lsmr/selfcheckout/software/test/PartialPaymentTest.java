package org.lsmr.selfcheckout.software.test;

import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.software.PartialPaymentSoftware;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PartialPaymentTest {

    @Test
    public void partialPaymentTest() throws Exception {

        TestHardware testHardware = new TestHardware();
        SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testHardware.scs);

        testHardware.scs.printer.addInk(10000);
        testHardware.scs.printer.addPaper(300);
        ;

        TestItems testItems = new TestItems();
        BarcodedItem guitarItem = testItems.lookupItem(new Barcode(new Numeral[] { Numeral.nine }));
        testHardware.scs.mainScanner.scan(guitarItem);

        PartialPaymentSoftware pps = new PartialPaymentSoftware(software);

        /*
         * * Handle partial payment
         * * * enable scanner
         * * * update item and total amount
         * * * checkout if total partial payment amount match total amount
         */

        // lets consider scanner is disable
        testHardware.scs.mainScanner.disable();

        // total partial amount paid
        BigDecimal amountPaid = pps.getAmountPaid();
        pps.partialPayment(BigDecimal.TEN); // partial payment
        BigDecimal afterPartialPaid = pps.getAmountPaid();

        // make partial payment
        assertEquals(amountPaid.add(BigDecimal.TEN), afterPartialPaid);

        // scanner should enable after partial payment
        assertFalse(testHardware.scs.mainScanner.isDisabled());
    }
}
