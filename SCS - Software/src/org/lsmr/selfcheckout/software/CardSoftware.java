package org.lsmr.selfcheckout.software;

import java.io.IOException;
import java.math.BigDecimal;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.Card.CardInsertData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;
import org.lsmr.selfcheckout.external.CardIssuer;

/**
 * Class CardSoftware that simulates the use cases where the customer pays with either a debit card, a credit card, or a giftcard, at the checkout.
 */

/**
 * implements the CardReaderObserver
 */

public class CardSoftware implements CardReaderObserver {

    public final CardReader cardReader;
    private CardData cardData;
    public boolean cardTapped = false;
    public boolean cardSwiped = false;
    public boolean cardInsert = false;
    public BigDecimal paymentAmount;
    private SelfCheckoutStationSoftware scss;
    private SelfCheckoutStation scs;
    private int holdNumber;
    public CardIssuer cardIssuer;

    /**
     * Parameterized constructor for the CardSoftware class
     * the cardReader is attached to the selfcheckoutstation
     * @param selfCheckoutStation the selfCheckoutStation used
     */
    public CardSoftware(SelfCheckoutStationSoftware software) {
    	scss = software;
        scs = scss.scs;
        scs.cardReader.attach(this);
        cardReader = new CardReader();
    }
    
    /**
     * Called whenever a card is swiped, inserted, or tapped. Performs the payment on the software side.
     * @param data Card data of the card inputed.
     */
    public void payWithCard(CardData data) {
    	String cardType = data.getType();
    	
    	// Not a paying card
    	if(cardType != "credit" && cardType != "debit" && cardType != "gift")
    		return;
    	
    	String cardNumber = data.getNumber();
    	
    	// Verify the card is good through the card issuer
    	BigDecimal actualAmount = paymentAmount.min(scss.getAmountLeftToPay());
    	
    	int c = actualAmount.compareTo(BigDecimal.ZERO);
    	if(c > 0) {
	    	int holdNumber = cardIssuer.authorizeHold(cardNumber, actualAmount);
	    	if(holdNumber != -1 && cardIssuer.postTransaction(cardNumber, holdNumber, actualAmount)) {
	    		// Amount has been paid in full
	    		cardIssuer.releaseHold(cardNumber, holdNumber);
	    		scss.addAmountPaid(actualAmount);
	    	}
	    	else
	    		System.out.println("Something went wrong during card authorization/transaction.");
    	}
    }

    /**
     * Simulates paying with a credit card at the checkout.
     * 
     * @param creditcard 
     * 			the type of card used for the payment (credit card in this case)
     * @param paymentMethod 
     * 			the method of payment (paymentOption) has to be either tap, swipe or insert.
     * @param totalBalance 
     * 			the card limit (totalBalance) needs to be more than the amount owed.
     * @param pin 
     * 			the pin entered by the user (pin) has to be correct
     * @param total 
     * 			the total amount the customer has to pay at checkout
     * @param payinfo 
     * 			the CardIssuer
     * 
     * @return true if payment is successful, or false otherwise
     * 
     * @throws IOException 
     * 			if the credit card is declined
     */

    public boolean PayWithcreditcard(Card creditcard, int paymentMethod, BigDecimal totalBalance, String pin,
            BigDecimal total, CardIssuer payinfo) throws IOException {
        CardData cardInformation = null;
        boolean cardReadApproved = false;
        double balance = totalBalance.doubleValue();
        double cost = total.doubleValue();

        if (creditcard == null) {
            return false;
        }
        if (paymentMethod > 3 || paymentMethod < 1) {
            return false;
        }

        if (paymentMethod == 1) {
            cardInformation = scs.cardReader.tap(creditcard);
            if (cardData.getType() == "credit") {
                if (cardInformation == cardData) {
                    cardReadApproved = true;
                    cardTapped(cardReader);
                }

            }

        }

        if (paymentMethod == 2) {
            cardInformation = scs.cardReader.swipe(creditcard);
            if (cardData.getType() == "credit") {
                if (cardInformation == cardData) {
                    cardReadApproved = true;
                    cardSwiped(cardReader);
                }

            }

        }

        if (paymentMethod == 3) {
            cardInformation = scs.cardReader.insert(creditcard, pin);
            if (cardData.getType() == "credit") {
                if (cardInformation == cardData) {
                    cardReadApproved = true;
                    cardInserted(cardReader);
                }

            }
        }
        if (cardReadApproved == true && balance >= cost) {
            holdNumber = payinfo.authorizeHold(cardData.getNumber(), total);
            if (payinfo.postTransaction(cardData.getNumber(), holdNumber, total)) {
                if (payinfo.releaseHold(cardData.getNumber(), holdNumber)) {
                    return true;
                }
            } else {
                throw new IOException("The credit card has been declined");
            }

        }

        return false;

    }

    /**
     * Simulates paying with a debit card at the checkout.
     * 
     * @param debitcard 
     * 			the type of card used for the payment (debit card in this case)
     * @param paymentMethod 
     * 			the method of payment (paymentOption) has to be either tap, swipe or insert.
     * @param totalBalance 
     * 			the card limit (totalBalance) needs to be more than the amount owed.
     * @param pin 
     * 			the pin entered by the user (pin) has to be correct
     * @param total 
     * 			the total amount the customer has to pay at checkout
     * @param payinfo 
     * 			the CardIssuer
     * 
     * @return true if payment is successful, or false otherwise
     * 
     * @throws IOException 
     * 			if the debit card is declined
     */

    public boolean PayWithDebitCard(Card debitCard, int paymentMethod, BigDecimal totalBalance, String pin,
            BigDecimal total, CardIssuer payinfo) throws IOException {
        CardData cardInformation = null;
        boolean cardReadApproved = false;
        double balance = totalBalance.doubleValue();
        double cost = total.doubleValue();

        if (debitCard == null) {
            return false;
        }
        if (paymentMethod > 3 || paymentMethod < 1) {
            return false;
        }

        if (paymentMethod == 1) {
            cardInformation = scs.cardReader.tap(debitCard);
            if (cardData.getType() == "debit") {
                if (cardInformation == cardData) {
                    cardReadApproved = true;
                    cardTapped(cardReader);
                }

            }

        }

        if (paymentMethod == 2) {
            cardInformation = scs.cardReader.swipe(debitCard);
            if (cardData.getType() == "debit") {
                if (cardInformation == cardData) {
                    cardReadApproved = true;
                    cardSwiped(cardReader);

                }

            }

        }

        if (paymentMethod == 3) {
            cardInformation = scs.cardReader.insert(debitCard, pin);
            if (cardData.getType() == "debit") {
                if (cardInformation == cardData) {
                    cardReadApproved = true;
                    cardInserted(cardReader);
                }

            }
        }
        if (cardReadApproved == true && balance >= cost) {
            holdNumber = payinfo.authorizeHold(cardData.getNumber(), total);
            if (payinfo.postTransaction(cardData.getNumber(), holdNumber, total)) {
                if (payinfo.releaseHold(cardData.getNumber(), holdNumber)) {
                    return true;
                }
            } else {
                throw new IOException("The debit card has been declined");
            }
        }

        return false;

    }

    /**
     * Simulates paying with a gift card at the checkout.
     * 
     * @param giftcard 
     * 			the type of card used for the payment (gift card in this case)
     * @param paymentMethod 
     * 			the method of payment (paymentOption) has to be swipe.
     * @param totalBalance 
     * 			the card limit (totalBalance) needs to be more than the amount owed.
     * @param total 
     * 			the total amount the customer has to pay at checkout
     * @param payinfo 
     * 			the CardIssuer
     * 
     * @return true if payment is successful, or false otherwise
     * 
     * @throws IOException 
     * 			if the gift card is declined
     */

    public boolean PaywithGiftCard(Card giftcard, int paymentMethod, BigDecimal totalBalance,
            BigDecimal total, CardIssuer payinfo) throws IOException {
        CardData cardInformation = null;
        boolean cardReadApproved = false;
        double balance = totalBalance.doubleValue();
        double cost = total.doubleValue();

        if (giftcard == null) {
            return false;
        }

        if (paymentMethod == 1) {
            cardInformation = scs.cardReader.swipe(giftcard);
            if (cardData.getType() == "gift card") {
                if (cardInformation == cardData) {
                    cardReadApproved = true;
                    cardSwiped(cardReader);
                }

            }

        }
        if (cardReadApproved == true && balance >= cost) {
            holdNumber = payinfo.authorizeHold(cardData.getNumber(), total);
            if (payinfo.postTransaction(cardData.getNumber(), holdNumber, total)) {
                if (payinfo.releaseHold(cardData.getNumber(), holdNumber)) {
                    return true;
                }
            } else {
                throw new IOException("The gift card has been declined!");
            }

        }

        return false;

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
    public void cardInserted(CardReader reader) {
        cardInsert = true;

    }

    @Override
    public void cardRemoved(CardReader reader) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cardTapped(CardReader reader) {
        cardTapped = true;

    }

    @Override
    public void cardSwiped(CardReader reader) {
        cardSwiped = true;

    }

    @Override
    public void cardDataRead(CardReader reader, CardData data) {
        this.cardData = data;
        payWithCard(data);
    }

}
