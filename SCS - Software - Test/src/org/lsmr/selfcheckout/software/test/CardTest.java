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
import org.lsmr.selfcheckout.software.CardSoftware;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

public class CardTest {
	private SelfCheckoutStationSoftware scss;
    private SelfCheckoutStation scs;
    CardSoftware pay;
    Card creditCard_tap;
    Card creditCard_noTap;
    Card debitCard_tap;
    Card debitCard_noTap;
    Card debitcard;
    Card creditCard;
    Card giftcard;
    int paymentMethod;
    Card notIssuedCredit;
    Card notIssuedDebit;
    Card notIssuedGiftCard;
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
        try {
			scss = new SelfCheckoutStationSoftware(scs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        creditCard_tap = new Card("credit", "3924294505943847", "Bubby", "056", "1234", true, true);
        debitcard = new Card("debit", "3924294505943847", "Bubby", "056", "1234", true, true);
        creditCard_noTap = new Card("credit", "3924294505943847", "bubby", "056", "1234", false, true);
        notIssuedCredit = new Card("credit", "3924294505943847", "Bubby", "066", "1234", true, true);
        debitCard_tap = new Card("debit", "3924294505943847", "Bubby", "056", "1234", true, true);
        creditCard = new Card("credit", "3924294505943847", "Bubby", "056", "1234", true, true);
        debitCard_noTap = new Card("debit", "3924294505943847", "bubby", "056", "1234", false, true);
        notIssuedDebit = new Card("debit", "3924294505943847", "Bubby", "066", "1234", true, true);
        giftcard = new Card("gift card", "3924294505943847", "Bubby", null, null, false, false);
        notIssuedGiftCard = new Card("gift card", "3924294505943847", "bubby", null, null, false, false);

        payinfo = new CardIssuer("Binance");
        payinfo.addCardData("3924294505943847", "Bubby", expiry, "056", new BigDecimal(10000));
        pay = scss.cardSoftware;
        pay.cardIssuer = payinfo;
        scss.cardSoftware.paymentAmount = new BigDecimal("1000000");

    }
    
    @Test
    public void testPayWithCard() {
    	scss.amountDue = new BigDecimal("100.00");
    	scss.cardSoftware.paymentAmount = new BigDecimal("100.00");

    	try {
			scs.cardReader.swipe(creditCard);
			
			// Should pay full amount
			assertEquals("Amount paid not correct.", BigDecimal.ZERO, scss.getAmountPaid());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testPayWithCardPartial() {
    	scss.amountDue = new BigDecimal("100.00");
    	scss.cardSoftware.paymentAmount = new BigDecimal("50.00");
    	
    	try {
			scs.cardReader.swipe(creditCard);
			
			assertEquals("Amount paid not correct.", new BigDecimal("50.00"), scss.getAmountPaid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testPayWithUnfullCard() {
    	scss.amountDue = new BigDecimal("100.00");
    	scss.cardSoftware.paymentAmount = new BigDecimal("100.00");
    	
    	Calendar.Builder b = new Calendar.Builder();
    	b.setDate(2024, 8, 13);
    	payinfo.addCardData("123456789", "Emu", b.build(), "016", new BigDecimal("50.00"));
    	Card testCard = new Card("credit", "123456789", "Emu", "016", "1234", true, true);
    	
    	try {
			scs.cardReader.swipe(testCard);
			
			assertEquals("No amount should have been paid", new BigDecimal("0"), scss.getAmountPaid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Following testing is for Credit Card.
     */
    @Test
    public void Credit_isNull() throws IOException {

        assertFalse(pay.PayWithcreditcard(null, 1, new BigDecimal(5), "0000", new BigDecimal(6), payinfo));

    }

    @Test
    public void Credit_isValid() throws IOException {
        assertTrue(pay.PayWithcreditcard(creditCard_tap, 1, new BigDecimal(6), "0000", new BigDecimal(6), payinfo));

    }

    @Test
    public void invalid_credit_payment_method() throws IOException {
        assertFalse(pay.PayWithcreditcard(creditCard_noTap, 0, new BigDecimal(6), "0000", new BigDecimal(6), payinfo));
    }

    @Test
    public void not_Credit() throws IOException {
        assertFalse(pay.PayWithcreditcard(debitcard, 1, new BigDecimal(6), "0000", new BigDecimal(6), payinfo));
    }

    @Test
    public void Credit_Bal_too_small() throws IOException {
        assertFalse(pay.PayWithcreditcard(creditCard_tap, 2, new BigDecimal(5), "0000", new BigDecimal(6), payinfo));
    }

    @Test
    public void Validcreditcard_tapOrSwipe_test() throws IOException {
        scs.cardReader.tap(creditCard_tap);
        scs.cardReader.swipe(creditCard_tap);
        assertTrue(pay.cardTapped);
        assertTrue(pay.cardSwiped);

    }

    @Test
    public void Credit_InvalidCard_tap_test() throws IOException {
        scs.cardReader.tap(creditCard_noTap);
        assertFalse(pay.cardTapped);

    }

    @Test
    public void insert_Credit_test_validPIN() throws IOException {
        assertTrue(pay.PayWithcreditcard(creditCard_noTap, 3, new BigDecimal(6), "1234", new BigDecimal(6), payinfo));
    }

    @Test(expected = InvalidPINException.class)
    public void insert_Credit_test_invalidPIN() throws IOException {
        assertFalse(pay.PayWithcreditcard(creditCard_noTap, 3, new BigDecimal(6), "1224", new BigDecimal(6), payinfo));
    }

    @Test
    public void FakeCreditCard() throws IOException {
        assertTrue(
                pay.PayWithcreditcard(notIssuedCredit, 3, new BigDecimal(10000), "1234", new BigDecimal(6), payinfo));
    }

    @Test
    public void CreditNotEnoughFunds() throws IOException {
        assertFalse(pay.PayWithcreditcard(notIssuedCredit, 3, new BigDecimal(10000), "1234", new BigDecimal(10001),
                payinfo));
    }

    /**
     * Following testing is for Debit Card.
     * 
     */

    @Test
    public void Debit_is_Null() throws IOException {

        assertFalse(pay.PayWithDebitCard(null, 1, new BigDecimal(5), "0000", new BigDecimal(6), payinfo));

    }

    @Test
    public void Debit_isValid() throws IOException {

        assertTrue(pay.PayWithDebitCard(debitCard_tap, 1, new BigDecimal(6), "0000", new BigDecimal(6), payinfo));

    }

    @Test
    public void Debit_invalid_payment_method() throws IOException {
        assertFalse(pay.PayWithDebitCard(debitCard_noTap, 0, new BigDecimal(6), "0000", new BigDecimal(6), payinfo));
    }

    @Test
    public void not_Debit() throws IOException {
        assertFalse(pay.PayWithDebitCard(creditCard, 1, new BigDecimal(6), "0000", new BigDecimal(6), payinfo));
    }

    @Test
    public void Debit_Bal_too_small() throws IOException {
        assertFalse(pay.PayWithDebitCard(debitCard_tap, 2, new BigDecimal(5), "0000", new BigDecimal(6), payinfo));
    }

    @Test
    public void ValidDebitcard_tapOrSwipe_test() throws IOException {
        scs.cardReader.tap(debitCard_tap);
        scs.cardReader.swipe(debitCard_tap);
        assertTrue(pay.cardTapped);
        assertTrue(pay.cardSwiped);

    }

    @Test
    public void InvalidDebitCard_tap_test() throws IOException {
        scs.cardReader.tap(debitCard_noTap);
        assertFalse(pay.cardTapped);

    }

    @Test
    public void insert_debit_test_validPIN() throws IOException {
        assertTrue(pay.PayWithDebitCard(debitCard_noTap, 3, new BigDecimal(6), "1234", new BigDecimal(6), payinfo));
    }

    @Test(expected = InvalidPINException.class)
    public void insert_debit_test_invalidPIN() throws IOException {
        assertFalse(pay.PayWithDebitCard(debitCard_noTap, 3, new BigDecimal(6), "1224", new BigDecimal(6), payinfo));
    }

    @Test
    public void FakeDebitCard() throws IOException {
        assertTrue(pay.PayWithDebitCard(notIssuedDebit, 1, new BigDecimal(1000), "1234", new BigDecimal(6), payinfo));
    }

    @Test
    public void DebitNotEnoughFunds() throws IOException {
        assertFalse(
                pay.PayWithDebitCard(debitCard_noTap, 3, new BigDecimal(1000), "1234", new BigDecimal(1001), payinfo));
    }

    /**
     * Following testing is for Gift Card.
     * 
     */

    @Test
    public void Gift_isNull() throws IOException {

        assertFalse(pay.PaywithGiftCard(null, 1, new BigDecimal(5), new BigDecimal(6), payinfo));

    }

    @Test
    public void giftCard_isValid() throws IOException {

        assertTrue(pay.PaywithGiftCard(giftcard, 1, new BigDecimal(6), new BigDecimal(6), payinfo));

    }

    @Test
    public void not_giftCard() throws IOException {
        assertFalse(pay.PaywithGiftCard(creditCard, 1, new BigDecimal(6), new BigDecimal(6), payinfo));
    }

    @Test
    public void FakeGiftCard() throws IOException {
        assertFalse(
                pay.PaywithGiftCard(notIssuedGiftCard, 3, new BigDecimal(10000), new BigDecimal(6), payinfo));
    }

    @Test
    public void GiftCard_Bal_too_small() throws IOException {
        assertFalse(pay.PaywithGiftCard(giftcard, 2, new BigDecimal(30000), new BigDecimal(6), payinfo));
    }
}
