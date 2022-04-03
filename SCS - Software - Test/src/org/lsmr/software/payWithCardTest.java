package org.lsmr.software;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.SimulationException;


public class payWithCardTest {
	
	private SelfCheckoutStationSetup setup = new SelfCheckoutStationSetup();
	private SelfCheckoutStation station;
	private Checkout test;
	private CardReader reader;
	
	@Before
	public void setup() {
		station = new SelfCheckoutStation(setup.currency, setup.banknoteDenoms, 
				setup.coinDenoms, setup.defaultMaxScaleWeight, setup.defaultSensitivity);
		test = new Checkout(station);
		reader = new CardReader();
		
		try {
			station.printer.addInk(500);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			station.printer.addPaper(500);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	/*
	 * This test method tests if when the card is swiped in the Configuration Phase
	 * the method should throw a simulation error
	 * 
	 * Expected = SimulationException
	 */
	@Test (expected = SimulationException.class)
 	public void testCardSwiped_configuration_simerror() throws IOException {
 		Card custCard = new Card("debit", "123456", "Vincent", "121", "1234", true, true);
 		
		test.wouldLikeToCheckOut(50.00); 
		reader.swipe(custCard);
		//station.cardReader.swipe(custCard);
	}
	
	/*
	 * This test method tests if when the card is swiped in the Normal Phase
	 * the method should not throw a simulation error
	 * 
	 */
	@Test 
 	public void testCardSwiped_configuration_normal() throws IOException {
 		Card custCard = new Card("debit", "123456", "Vincent", "121", "1234", true, true);
 		try { 
 			//interface stub is used to end phases that would otherwise trigger sim error
 			//station.scanningArea.endConfigurationPhase();
 			test = new Checkout(station);
 			test.wouldLikeToCheckOut(50.00);
 			double amtDue = test.getAmountDue();
 			station.cardReader.swipe(custCard);
 			
 			assertTrue("Card paid amount due:" + test.getAmountPaid(), test.getAmountDue() == test.getAmountPaid());
 		}
 		catch (SimulationException e) {
 			e.printStackTrace();
 		}
		
	}
	
	/*
	 * This test method tests if when the card is tapped in the Configuration Phase
	 * the method should throw a simulation error
	 * 
	 * Expected = SimulationException
	 */
	@Test (expected = SimulationException.class)
 	public void testCardTapped_configuration_simerror() throws IOException {
 		Card custCard = new Card("debit", "123456", "Vincent", "121", "1234", true, true);
 		
		test.wouldLikeToCheckOut(50.00); 
		reader.tap(custCard);
		//station.cardReader.swipe(custCard);
	}
	
	/*
	 * This test method tests if when the card is swiped in the Normal Phase
	 * the method should not throw a simulation error
	 * 
	 */
	@Test 
 	public void testCardTapped_configuration_normal() throws IOException {
 		Card custCard = new Card("debit", "123456", "Vincent", "121", "1234", true, true);
 		try { 
 			//station.scanningArea.endConfigurationPhase();
 			test = new Checkout(station);
 			test.wouldLikeToCheckOut(50.00);
 			double amtDue = test.getAmountDue();
 			station.cardReader.tap(custCard);
 			
 			assertTrue("Card paid amount due:" + test.getAmountPaid(), test.getAmountDue() == test.getAmountPaid());
 		}
 		catch (SimulationException e) {
 			e.printStackTrace();
 		}
		
	}
	
	/*
	 * This test method tests if when the card is inserted in the Configuration Phase
	 * the method should throw a simulation error
	 * 
	 * Expected = SimulationException
	 */
	@Test (expected = SimulationException.class)
 	public void testCardInsert_configuration_simerror() throws IOException {
 		Card custCard = new Card("debit", "123456", "Vincent", "121", "1234", true, true);
 		
		test.wouldLikeToCheckOut(50.00); 
		reader.insert(custCard, "1234");
		//station.cardReader.swipe(custCard);
	}
	
	/*
	 * This test method tests if when the card is inserted in the Normal Phase
	 * the method should not throw a simulation error
	 * 
	 */
	@Test 
 	public void testCardInsert_configuration_normal() throws IOException {
 		Card custCard = new Card("debit", "123456", "Vincent", "121", "1234", true, true);
 		try { 
 			//station.scanningArea.endConfigurationPhase();
 			test = new Checkout(station);
 			test.wouldLikeToCheckOut(50.00);
 			double amtDue = test.getAmountDue();
 			station.cardReader.insert(custCard, "1234");
 			
 			assertTrue("Card paid amount due:" + test.getAmountPaid(), test.getAmountDue() == test.getAmountPaid());
 		}
 		catch (SimulationException e) {
 			e.printStackTrace();
 		}
		
	}
	
	/*
	 * This test method tests if when the card is swiped in the Normal Phase
	 * with a invalid card type. The method should not throw a simulation error
	 * 
	 */
	@Test (expected = SimulationException.class)
 	public void testCard_InvalidCard() throws IOException {
 		Card custCard = new Card(null, "123456", "Vincent", "121", "1234", true, true);
 		//station.scanningArea.endConfigurationPhase();
 		test = new Checkout(station);
 		
	}
	
	/*
	 * This test method tests if when the card is inserted in the Normal Phase
	 * with null pin, the method should not throw a simulation error
	 * 
	 */
	@Test (expected = SimulationException.class)
 	public void testCardInsert_configuration_invalidpin() throws IOException {
 		Card custCard = new Card("debit", "123456", "Vincent", "121", null, true, true);
 		//station.scanningArea.endConfigurationPhase();
 		test = new Checkout(station);		
	}
	
	/*
	 * This test method tests if when the card is swiped with no name , in the Normal Phase
	 * the method should throw a simulation error
	 * 
	 */
	@Test (expected = SimulationException.class)
 	public void testCardSwiped_normal_noname() throws IOException {
 		Card custCard = new Card("debit", "123456", null , "121", "1234", true, true); 
 		//station.scanningArea.endConfigurationPhase();
 		test = new Checkout(station);

 		}
  	 	
  		
}
