package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Card.CardSwipeData;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
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
    public SelfCheckoutStation theStation;
    public SelfCheckoutStationSoftware theSoftware;
    public boolean cardSwipedNew = false;
    // Where we'll store members that are registered.
    public MembersDatabase memberDatabase;
    

    // card type
    public static final String TYPE = "MEMBERSHIP";

    // should null at beginning, after insert card data will be available
    private Card.CardData cardData;

    // card status
    //private boolean cardTapped = false;
    private boolean cardSwiped = false;
    //private boolean cardInsert = false;

    // is applicable for discount
    private boolean isApplicableForDiscount = false;

    // default constructor
    public ScanMembershipCard(SelfCheckoutStationSoftware aSoftware) {
        // validate card type
    	this.theSoftware = aSoftware;
    	this.theStation = theSoftware.scs;
       
       
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        //device.enable();
    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        //device.disable();
    }

    @Override
    public void cardInserted(CardReader reader) {
		
    }

    @Override
    public void cardTapped(CardReader reader) {
		
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
    	this.cardSwipedNew = true;
        
    }

    @Override
    public void cardRemoved(CardReader reader) {
        // remove the card from device, for this imagine in the
        // reader we can read single card at a time
        reader.remove();

        // after remove update all value,
        // so we can process another card using same object
        //cardTapped = false;
        cardSwiped = false;
        //cardInsert = false;
    }

    // if any of cardReader operation happened we will validate type and
    // update cardData object in this class
    @Override
    public void cardDataRead(CardReader reader, Card.CardData data) {
    
    	// Only care about swipe or manual entry for membership card
    	if(data.getClass() == CardSwipeData.class) {
    		if(data.getType()=="member" || data.getType()=="membership") {
    			if(memberDatabase.authenticateMember(data)) {
    				
    				theSoftware.setMemberCardNumber(data.getNumber());
    				//include it in the receipt.
    			}
    		}
    	}
    	
    	//cardData = data;
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

    

    public boolean isCardSwiped() {
        return cardSwiped;
    }

   
}
