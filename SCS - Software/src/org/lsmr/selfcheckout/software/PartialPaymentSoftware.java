package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.*;

import java.math.BigDecimal;

/**
 * handle partial payment
 */
public class PartialPaymentSoftware {
    SelfCheckoutStationSoftware scss;

    public PartialPaymentSoftware(SelfCheckoutStationSoftware scss) throws OverloadException {
        this.scss = scss;
    }

    /**
     * Handle partial payment
     * * enable scanner
     * * update item and total amount
     * * checkout if total partial payment amount match total amount
     *
     * @param amount partial amount want to pay
     * @throws OverloadException
     * @throws EmptyException
     * @throws InterruptedException
     */
    public void partialPayment(BigDecimal amount) throws EmptyException, OverloadException, InterruptedException {
        // disable scanner
        scss.scs.mainScanner.disable();

        // update total paid amount with partial payment
        //scss.amountPaid[0] = scss.amountPaid[0].add(amount);
        scss.addAmountPaid(amount);

        // if total amount paid then go for checkout using parent method
        BigDecimal total = scss.total();
        if (total.compareTo(scss.getAmountPaid()) <= 0) {
            scss.checkout();
        }

        // if total partial payment not match with total amount,
        // do nothing just enable scanner so user can scan items
        else {
            scss.scs.mainScanner.enable();
        }
    }

    public BigDecimal getAmountPaid() {
        return scss.getAmountPaid();
    }
}
