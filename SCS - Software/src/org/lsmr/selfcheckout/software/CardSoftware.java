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

public class CardSoftware implements CardReaderObserver {

    public final CardReader cardReader;
    private CardData cardData;
    public boolean cardTapped = false;
    public boolean cardSwiped = false;
    public boolean cardInsert = false;
    private SelfCheckoutStation scs;
    private int holdNumber;

    public CardSoftware(SelfCheckoutStation selfCheckoutStation) {
        scs = selfCheckoutStation;
        scs.cardReader.attach(this);
        cardReader = new CardReader();
    }

    /**
     * This class is a simulation of paying with a credit card at the checkout.
     * 
     * The type of payment (creditcard) needs to be credit card.
     * The option of payment (paymentOption) has to be either tap, swipe or insert.
     * The card limit (totalBalance) needs to be more than the amount owed.
     * The pin (pin) entered by the user has to be correct.
     * Return true if payment is successful, false otherwise.
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
     * This class is a simulation of paying with a debit card at the checkout.
     * 
     * The type of payment (creditcard) needs to be debit card.
     * The option of payment (paymentOption) has to be either tap, swipe or insert.
     * The card limit (totalBalance) needs to be more than the amount owed.
     * The pin (pin) entered by the user has to be correct.
     * Return true if payment is successful, false otherwise.
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
     * This class is a simulation of paying with a credit card at the checkout.
     * 
     * The type of payment (giftcard) needs to be credit card.
     * The option of payment (paymentOption) has to be either tap, swipe or insert.
     * The card limit (totalBalance) needs to be more than the amount owed.
     * The pin (pin) entered by the user has to be correct.
     * Return true if payment is successful, false otherwise.
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

    }

}
