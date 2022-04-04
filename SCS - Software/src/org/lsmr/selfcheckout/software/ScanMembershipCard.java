package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.observers.*;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.software.exceptions.InvalidCardTypeException;

import java.io.IOException;
import java.util.Random;

/**
 * This class will read and verify membership card,
 *
 * if card verified then we can use "isApplicableForDiscount" method to
 * know this card user isApplicableForDiscount status then buy this method we can provide
 * Discount when customer paying using credit or debit card.
 */
public class ScanMembershipCard implements CardReaderObserver {

    // we can't extend Card class and create MembershipCard class, so
    // we create a card object to represent membership card
    private Card membershipCard;
    private String pin;

    // card type
    public static final String TYPE = "MEMBERSHIP";

    // should null at beginning, after insert card data will be available
    private Card.CardData cardData;

    // card status
    private boolean cardTapped = false;
    private boolean cardSwiped = false;
    private boolean cardInsert = false;

    // is applicable for discount
    private boolean isApplicableForDiscount = false;

    // default constructor
    public ScanMembershipCard(Card membershipCard, String pin) {
        // validate card type
        this.membershipCard = membershipCard;
        this.pin = pin;
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        device.enable();
    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        device.disable();
    }

    @Override
    public void cardInserted(CardReader reader) {
        // insert the card in device, for this imagine in the
        // reader we can read single card at a time
        try {
            // verify pin, pin should not null
            if (pin == null) {
                throw new InvalidCardTypeException();
            }

            // update cardData and insert card
            cardDataRead(reader,
                    reader.insert(membershipCard, pin) // insert card
            );
            cardInsert = true;
        } catch (IOException e) {
            // show error message in GUI/TouchScreen
            TouchScreen.invalidCardMessage();
        } catch (InvalidCardTypeException e) {
            // show error message in GUI/TouchScreen
            TouchScreen.invalidPinMessage();
        }
    }

    @Override
    public void cardTapped(CardReader reader) {
        try {
            cardDataRead(reader,
                    reader.tap(membershipCard) // tap card
            );
            cardTapped = true;

            // after tap lets check is user verify for discount or not
            if (verifyDiscount()) {
                isApplicableForDiscount = true;
            }

        } catch (IOException e) {
            // show error message in GUI/TouchScreen
            TouchScreen.invalidCardMessage();
        }
    }

    // provide discount for random basis
    private boolean verifyDiscount() {
        Random random = new Random();
        return random.nextBoolean();
    }

    /**
     * card swap will verify card type, the card type should be "membership"
     *
     * @param reader cardReader
     */
    @Override
    public void cardSwiped(CardReader reader) {
        // swipe and update card data
        try {
            Card.CardData data = reader.swipe(membershipCard);// swap card
            // verify type
            if (data.getType().equals(TYPE)) {
                cardDataRead(reader, data);
            }
            // notify in touchscreen, card type is not valid.
            else {
                // show error message in GUI/TouchScreen
                TouchScreen.invalidMembershipCardMessage();
            }

            // update card swapped
            cardSwiped = true;
        } catch (IOException e) {
            TouchScreen.invalidCardMessage();
        }
    }

    @Override
    public void cardRemoved(CardReader reader) {
        // remove the card from device, for this imagine in the
        // reader we can read single card at a time
        reader.remove();

        // after remove update all value,
        // so we can process another card using same object
        cardTapped = false;
        cardSwiped = false;
        cardInsert = false;
    }

    // if any of cardReader operation happened we will validate type and
    // update cardData object in this class
    @Override
    public void cardDataRead(CardReader reader, Card.CardData data) {
        cardData = data;
    }

    // return isApplicableForDiscount's current status
    public boolean isApplicableForDiscount() {
        return isApplicableForDiscount;
    }

    /**
     * accessor methods
     */
    public Card.CardData getCardData() {
        return cardData;
    }

    public boolean isCardTapped() {
        return cardTapped;
    }

    public boolean isCardSwiped() {
        return cardSwiped;
    }

    public boolean isCardInsert() {
        return cardInsert;
    }
}
