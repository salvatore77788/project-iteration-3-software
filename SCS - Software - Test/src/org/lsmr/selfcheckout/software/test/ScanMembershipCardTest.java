package org.lsmr.selfcheckout.software.test;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
//import org.lsmr.selfcheckout.devices.theSoftware.scs.cardReader;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.ScanMembershipCard;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

import junit.framework.Assert;

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

	private TestHardware testHardware;
	private SelfCheckoutStationSoftware theSoftware;

	@Before
	public void setup() throws Exception {

		// we only need this to instantiate a proper scs (coin and banknote
		// denominations)
		testHardware = new TestHardware();

		theSoftware = new SelfCheckoutStationSoftware(testHardware.scs);

		pin = "4567";

		cardTest = new Card(
				"member",
				"85802420",
				"John Smith",
				"567", // cvv
				pin, // pin
				false, // Tap is not enabled.
				false); // does not have a chip.

		
		theSoftware.membersRecord.members.put("85802420", "John Smith");
		
	}

	// The test consists of obtaining the number from cardTest card.
	// and makes sure that it gets set as memberNumber in theSoftware.
	@Test
	public void SwipeTest() {

		boolean swipedWorked = false;
		do {
			try {
				theSoftware.scs.cardReader.swipe(cardTest);
				swipedWorked = true;
			} catch (IOException e) {
				// System.out.println("Failed");
				// TODO Auto-generated catch block

				e.printStackTrace();
				continue;
			}

		} while (!swipedWorked);

		assertTrue("85802420" == theSoftware.getMemberCardNumber());

	}
	
	
	// The member number should not have been updated since the card 
	// being swiped is a debit card.
	@Test
	public void SwipeTest2() {

		Card cardTest2 = new Card(
				"debit",
				"85802420",
				"John Smith",
				"567", // cvv
				pin, // pin
				false, // Tap is not enabled.
				false); 

		boolean swipedWorked = false;
		do {
			try {
				theSoftware.scs.cardReader.swipe(cardTest2);
				swipedWorked = true;
			} catch (IOException e) {
				// System.out.println("Failed");
				// TODO Auto-generated catch block

				e.printStackTrace();
				continue;
			}

		} while (!swipedWorked);

		assertTrue(null == theSoftware.getMemberCardNumber());

	}
	
	
	// The member number should not have been updated since the card 
	// being swiped is a credit card.
	@Test
	public void SwipeTest3() {

		Card cardTest3 = new Card(
				"credit",
				"85802420",
				"John Smith",
				"567", // cvv
				pin, // pin
				false, // Tap is not enabled.
				false); 

		boolean swipedWorked = false;
		do {
			try {
				theSoftware.scs.cardReader.swipe(cardTest3);
				swipedWorked = true;
			} catch (IOException e) {
				// System.out.println("Failed");
				// TODO Auto-generated catch block

				e.printStackTrace();
				continue;
			}

		} while (!swipedWorked);

		assertTrue(null == theSoftware.getMemberCardNumber());

	}
	
	// If the member card that was swiped is invalid
	// then it should not set it as as the memberNumber in 
	// SelfCheckoutStationSoftware.
	@Test
	public void SwipeTest4() {

		Card cardTest4 = new Card(
				"debit",
				"90012420",
				"Sean Brown",
				"567", // cvv
				pin, // pin
				false, // Tap is not enabled.
				false); 

		boolean swipedWorked = false;
		do {
			try {
				theSoftware.scs.cardReader.swipe(cardTest4);
				swipedWorked = true;
			} catch (IOException e) {
				// System.out.println("Failed");
				// TODO Auto-generated catch block

				e.printStackTrace();
				continue;
			}

		} while (!swipedWorked);

		assertTrue(null == theSoftware.getMemberCardNumber());

	}


}
