package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.InvalidPINException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.external.CardIssuer;
import org.lsmr.selfcheckout.software.CreditCardSoftware;

public class CreditCardTest {
    private SelfCheckoutStation scs;
    CreditCardSoftware pay;
    Card creditCard_tap;
    Card creditCard_noTap;
    Card debitcard;
    int paymentMethod;
    Card notIssuedCredit;
    CardIssuer payinfo;

    private Currency currency;

    @Before
    public void setup() {
        currency = Currency.getInstance("CAD");
        int[] banknoteDenominations = { 5, 10, 20, 50 };
        BigDecimal[] coinDenominations = { new BigDecimal(0.05), new BigDecimal(0.1), new BigDecimal(0.25),
                new BigDecimal(1.00), new BigDecimal(2.00) };
        int weightLimitInGrams = 100;
        int sensitivity = 1;
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.MONTH, 11);
        expiry.add(Calendar.YEAR, 2022);
        scs = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, weightLimitInGrams,
                sensitivity);
        creditCard_tap = new Card("credit", "3924294505943847", "Bubby", "056", "1234", true, true);
        debitcard = new Card("debit", "3924294505943847", "Bubby", "056", "1234", true, true);
        creditCard_noTap = new Card("credit", "3924294505943847", "bubby", "056", "1234", false, true);
        notIssuedCredit = new Card("credit", "3924294505943847", "Bubby", "066", "1234", true, true);
        payinfo = new CardIssuer("Binance");
        payinfo.addCardData("3924294505943847", "Bubby", expiry, "056", new BigDecimal(10000));
        pay = new CreditCardSoftware(scs);

    }

    @Test
    public void Credit_isNull() throws IOException {

        assertFalse(pay.PayWithcreditcard(null, 1, new BigDecimal(5), "0000", new BigDecimal(6), payinfo));

    }

    @Test
    public void Credit_isValid() throws IOException {

        assertTrue(pay.PayWithcreditcard(creditCard_tap, 1, new BigDecimal(6), "0000", new BigDecimal(6), payinfo));

    }

    @Test
    public void invalid_payment_method() throws IOException {
        assertFalse(pay.PayWithcreditcard(creditCard_noTap, 0, new BigDecimal(6), "0000", new BigDecimal(6), payinfo));
    }

    @Test
    public void not_Credit() throws IOException {
        assertFalse(pay.PayWithcreditcard(debitcard, 1, new BigDecimal(6), "0000", new BigDecimal(6), payinfo));
    }

    @Test
    public void Bal_too_small() throws IOException {
        assertFalse(pay.PayWithcreditcard(creditCard_tap, 2, new BigDecimal(5), "0000", new BigDecimal(6), payinfo));
    }

    @Test
    public void Validcard_tapOrSwipe_test() throws IOException {
        scs.cardReader.tap(creditCard_tap);
        scs.cardReader.swipe(creditCard_tap);
        assertTrue(pay.cardTapped);
        assertTrue(pay.cardSwiped);

    }

    @Test
    public void InvalidCard_tap_test() throws IOException {
        scs.cardReader.tap(creditCard_noTap);
        assertFalse(pay.cardTapped);

    }

    @Test
    public void insert_test_validPIN() throws IOException {
        assertTrue(pay.PayWithcreditcard(creditCard_noTap, 3, new BigDecimal(6), "1234", new BigDecimal(6), payinfo));
    }

    @Test(expected = InvalidPINException.class)
    public void insert_test_invalidPIN() throws IOException {
        assertFalse(pay.PayWithcreditcard(creditCard_noTap, 3, new BigDecimal(6), "1224", new BigDecimal(6), payinfo));
    }

    @Test
    public void FakeCreditCard() throws IOException {
        assertTrue(
                pay.PayWithcreditcard(notIssuedCredit, 3, new BigDecimal(10000), "1234", new BigDecimal(6), payinfo));
    }

    @Test
    public void NotEnoughFunds() throws IOException {
        assertFalse(pay.PayWithcreditcard(notIssuedCredit, 3, new BigDecimal(10000), "1234", new BigDecimal(10001),
                payinfo));
    }

}