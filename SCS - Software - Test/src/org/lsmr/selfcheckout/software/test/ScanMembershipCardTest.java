package org.lsmr.selfcheckout.software.test;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
//import org.lsmr.selfcheckout.devices.theSoftware.scs.cardReader;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.ScanMembershipCard;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

import static org.junit.Assert.*;
/*
 * in AbstractDevice current should be 'NORMAL' run test
 *
 * protected Phase phase = Phase.NORMAL;
 */

import java.io.IOException;

public class ScanMembershipCardTest {

    private Card cardTest;
    private String pin;
    //private theSoftware.scs.cardReader theSoftware.scs.cardReader;
    //private ScanMembershipCard membershipCard; // this is an observer. this 
    //private SelfCheckoutStation scs;
    private TestHardware testHardware;
    private SelfCheckoutStationSoftware theSoftware;
    

    @Before
    public void setup() throws Exception {
    	
    	// we only need this to instantiate a proper scs (coin and banknote denominations)
    	testHardware = new TestHardware();
    	//theStation = new SelfCheckoutStation();
    	theSoftware = new SelfCheckoutStationSoftware(testHardware.scs);
    	
    	// theSoftware.scs.theSoftware.scs.cardReader;
    	
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
        cardTest = new Card(
                ScanMembershipCard.TYPE,
                "1234567890123456",
                "John Smith",
                "567",
                pin,
                true,
                true
        );

        // init card reader and set device ConfigurationPhase = NORMAL
        //theSoftware.scs.cardReader = new theSoftware.scs.cardReader();
       // theSoftware.scs.cardReader.endConfigurationPhase();

    }

	/*
	 * @Test public void InsertTest() { // init membership card membershipCard = new
	 * ScanMembershipCard(testHardware.scs);
	 * 
	 * // before cardInserted result should be false
	 * assertFalse(membershipCard.isCardInsert());
	 * 
	 * // insert card membershipCard.cardInserted(theSoftware.scs.cardReader);
	 * 
	 * //after should be true assertTrue(membershipCard.isCardInsert()); }
	 */

	/*
	 * @Test public void TapTest() { // init membership card membershipCard = new
	 * ScanMembershipCard(testHardware.scs);
	 * 
	 * // before tapped result should be false
	 * assertFalse(membershipCard.isCardTapped());
	 * 
	 * // tapped card membershipCard.cardTapped(theSoftware.scs.cardReader);
	 * 
	 * // after should be true assertTrue(membershipCard.isCardTapped()); }
	 */

    @Test
    public void SwipeTest() {
        
    	// should be false at first.
    	assertFalse(theSoftware.memberCardObserver.cardSwipedNew);
    	
    	boolean swipedWorked = false;
    	do {
    		try {
    			theSoftware.scs.cardReader.swipe(cardTest);
    			swipedWorked = true;
    		}
    		catch(IOException e) {
				//System.out.println("Failed");
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				continue;
    		}
    	
    	
    	}while(!swipedWorked);
    	
    	assertTrue(theSoftware.memberCardObserver.cardSwipedNew);
    	
    	
    }

	/*
	 * @Test public void RemoveTest() {
	 * 
	 * // init membership card membershipCard = new
	 * ScanMembershipCard(testHardware.scs);
	 * 
	 * membershipCard.cardSwiped(theSoftware.scs.cardReader);
	 * 
	 * // before remove card all result should be true
	 * assertTrue(membershipCard.isCardSwiped());
	 * 
	 * // after remove card all result should be true
	 * assertFalse(membershipCard.isCardSwiped());
	 * 
	 * }
	 */
}
