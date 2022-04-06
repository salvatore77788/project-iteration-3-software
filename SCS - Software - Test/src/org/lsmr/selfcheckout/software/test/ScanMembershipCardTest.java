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
				ScanMembershipCard.TYPE,
				"1234567890123456",
				"John Smith",
				"567",
				pin,
				true,
				true);

	}

	@Test
	public void SwipeTest() {

		// should be false at first.
		assertFalse(theSoftware.memberCardObserver.cardSwipedNew);

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

		assertTrue(theSoftware.memberCardObserver.cardSwipedNew);

	}

}
