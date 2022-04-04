package org.lsmr.selfcheckout.software.test;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.software.ScanMembershipCard;

import static org.junit.Assert.*;
/*
 * in AbstractDevice current should be 'NORMAL' run test
 *
 * protected Phase phase = Phase.NORMAL;
 */

public class ScanMembershipCardTest {

    private Card card;
    private String pin;
    private CardReader cardReader;
    private ScanMembershipCard membershipCard;

    @Before
    public void setup() throws Exception {

        pin = "4567";
        // create card
        /*
         * public Card(
         * 		String type,
         *     String number,
         *     String cardholder,
         *     String cvv,
         *     String pin,
         *     boolean isTapEnabled,
         *     boolean hasChip )
         */
        card = new Card(
                ScanMembershipCard.TYPE,
                "1234567890123456",
                "John Smith",
                "567",
                pin,
                true,
                true
        );

        // init card reader and set device ConfigurationPhase = NORMAL
        cardReader = new CardReader();
        cardReader.endConfigurationPhase();

    }

    @Test
    public void InsertTest() {
        // init membership card
        membershipCard = new ScanMembershipCard(card, pin);

        // before cardInserted result should be false
        assertFalse(membershipCard.isCardInsert());

        // insert card
        membershipCard.cardInserted(cardReader);

        //after should be true
        assertTrue(membershipCard.isCardInsert());
    }

    @Test
    public void TapTest() {
        // init membership card
        membershipCard = new ScanMembershipCard(card, pin);

        // before tapped result should be false
        assertFalse(membershipCard.isCardTapped());

        // tapped card
        membershipCard.cardTapped(cardReader);

        // after should be true
        assertTrue(membershipCard.isCardTapped());
    }

    @Test
    public void SwipeTest() {
        // init membership card
        membershipCard = new ScanMembershipCard(card, pin);

        // before CardSwiped result should be false
        assertFalse(membershipCard.isCardSwiped());

        // tapped card
        membershipCard.cardSwiped(cardReader);

        // after should be true
        assertTrue(membershipCard.isCardSwiped());
    }

    @Test
    public void RemoveTest() {

        // init membership card
        membershipCard = new ScanMembershipCard(card, pin);

        // trigger all events
        membershipCard.cardTapped(cardReader);
        membershipCard.cardSwiped(cardReader);
        membershipCard.cardInserted(cardReader);

        // before remove card all result should be true
        assertTrue(membershipCard.isCardSwiped());
        assertTrue(membershipCard.isCardInsert());
        assertTrue(membershipCard.isCardTapped());

        // removed card
        membershipCard.cardRemoved(cardReader);

        // after remove card all result should be true
        assertFalse(membershipCard.isCardSwiped());
        assertFalse(membershipCard.isCardInsert());
        assertFalse(membershipCard.isCardTapped());
    }
}
