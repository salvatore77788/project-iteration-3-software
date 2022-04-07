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

public class GiftCard implements CardReaderObserver {
    public final CardReader cardReader;
    private CardInsertData cardInsertData;
    private CardData cardData;
    public boolean cardSwiped = false;

    private SelfCheckoutStation scs;
    private int holdNumber;

    public GiftCard(SelfCheckoutStation selfCheckoutStation) {
        scs = selfCheckoutStation;
        scs.cardReader.attach(this);
        cardReader = new CardReader();
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
        int value = 0;
        boolean cardReadApproved = false;
        double balance = totalBalance.doubleValue();
        double cost = total.doubleValue();

        if (giftcard == null) {
            return false;
        }

        if (paymentMethod == 1) {
            cardInformation = scs.cardReader.swipe(giftcard);
            if (cardData.getType() == "giftcard") {
                if (cardInformation == cardData) {
                    cardReadApproved = true;
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
    public void cardInserted(CardReader reader) {
    }

    @Override
    public void cardRemoved(CardReader reader) {

    }

    @Override
    public void cardTapped(CardReader reader) {

    }

    @Override
    public void cardSwiped(CardReader reader) {
        cardSwiped = true;

    }

    @Override
    public void cardDataRead(CardReader reader, CardData data) {
        this.cardData = data;

    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // TODO Auto-generated method stub

    }

}