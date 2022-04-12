package org.lsmr.selfcheckout.software;

import java.util.Random;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Card.CardSwipeData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;

/**
 * Class ScanMembershipCard that reads and verifies the costumer's membership card
 * if card verified then we can use "isApplicableForDiscount" method to
 * know this card user isApplicableForDiscount status then by this method we can provide
 * discount when customer is paying using credit or debit card.
 */

/**
 * implements CardReaderObserver
 */

public class ScanMembershipCard implements CardReaderObserver {

    // An instance of "theSoftware" would call up this class.
    public SelfCheckoutStationSoftware theSoftware;
    public boolean cardSwiped = false;
    
    // Where we'll store members that are registered.
    public MembersDatabase memberDatabase;
    

    // card type
    public static final String TYPE = "MEMBERSHIP";

    // should be null at the beginning, after inserting the card, data will be available
    private Card.CardData cardData;

    //private boolean cardInsert = false;

    // is applicable for discount
    private boolean isApplicableForDiscount = false;

    /**
     * Parameterized constructor for the class ScanMembershipCard
     * @param aSoftware 
     * 			the SelfCheckoutStationSoftware used
     */
    public ScanMembershipCard(SelfCheckoutStationSoftware aSoftware) {
        // validate card type
    	this.theSoftware = aSoftware;    
    	this.memberDatabase = theSoftware.membersRecord;
       
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
     * cardSwiped will verify card type, the card type should be "membership" in this case
     *
     * @param reader 
     * 			the cardReader used
     */
    @Override
    public void cardSwiped(CardReader reader) {
        // swipe and update card data
    	this.cardSwiped = true;
        
    }

    @Override
    public void cardRemoved(CardReader reader) {
      
      
    }


    /**
     * Reads the card Data and checks if the card is valid
     * if any of cardReader operations happen we will validate type and 
     * update cardData object in this class
     * @param reader
     * 			the CardReader used
     * @param data
     * 			the membership card data
     */
    @Override
    public void cardDataRead(CardReader reader, Card.CardData data) {
    	
    	// based on the assumption, the swiped would have already happened. (reset.)
    	this.cardSwiped = false;
    	// Only care about swipe or manual entry for membership card
    	if(data.getClass() == CardSwipeData.class) {
    		if(data.getType()=="member" || data.getType()=="membership") {
    			if(this.memberDatabase.authenticateMember(data)) {
    				
    				theSoftware.setMemberCardNumber(data.getNumber());
    				//include it in the receipt.
    			}
    		}
    	}
    	
    	
    	//cardData = data;
    }

    /**
     * Function isApplicableForDiscount decides whether the membership card is applicable for discount
     * @return isApplicableForDiscount's current status
     */
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
