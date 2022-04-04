package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.*;

import java.math.BigDecimal;

/**
 * handle partial payment
 */
public class PartialPaymentSoftware {
    SelfCheckoutStationSoftware scss;

    public PartialPaymentSoftware(SelfCheckoutStationSoftware scss) throws SimulationException, OverloadException {
        this.scss = scss;
    }

    /**
     * Handle partial payment
     * * enable scanner
     * * update item and total amount
     * * checkout if total partial payment amount match total amount
     *
     * @param amount partial amount want to pay
     */
    public void partialPayment(BigDecimal amount) {
        // disable scanner
        scss.scs.mainScanner.disable();

        // update total paid amount with partial payment
        scss.amountPaid[0] = scss.amountPaid[0].add(amount);

        // if total amount paid then go for checkout using parent method
        BigDecimal total = scss.total();
        if (total.compareTo(scss.amountPaid[0]) <= 0) {
            scss.checkout();
        }

        // if total partial payment not match with total amount,
        // do nothing just enable scanner so user can scan items
        else {
            scss.scs.mainScanner.endConfigurationPhase();
            scss.scs.mainScanner.enable();
        }
    }

    public BigDecimal getAmountPaid() {
        return scss.amountPaid[0];
    }
}
