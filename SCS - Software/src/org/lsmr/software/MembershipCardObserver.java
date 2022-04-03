package org.lsmr.software;

import java.util.ArrayList;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.Card.CardSwipeData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;


public class MembershipCardObserver implements CardReaderObserver{

	public SelfCheckoutStation sc_Station;
	// The Database would be stubbed, having some members prior
	// to authenticating whether the current card being passed is 
	// actually a member or not.
	public MembersDatabase memberDatabase;
	ShoppingCartReceiptPrinter printerObserver;
	
	
	
	// Constructor
	public MembershipCardObserver(SelfCheckoutStation station, ShoppingCartReceiptPrinter printerObs, MembersDatabase fromTestDB) {
		this.sc_Station = station;
		this.printerObserver = printerObs;
		this.memberDatabase = fromTestDB;
		station.cardReader.attach(this);
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
		// How would I know that it's a memberships card?
		// Look at the card class -> there's a "type" String
		// This method seems pretty useless, there's no information about the card.
		
	}

	@Override
	public void cardRemoved(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardTapped(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	// for membership, I only care about the swipe method.
	@Override
	public void cardSwiped(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	// only this seems to have information about the card.
	@Override
	public void cardDataRead(CardReader reader, CardData data) {
		// 
		
		// Only care about swipe or manual entry for membership card
		// no device for manual entry.
		if(data.getClass() == CardSwipeData.class) {
			
			// match to some String? credit, debit, membership?
			// many ways to write member though.
			if(data.getType()=="member" || data.getType()=="membership") {
				// just make sure that it gets printed on the receipt.
				// sc_Station. 
				
				// is it already a member?
				if(memberDatabase.authenticateMember(data)) {
					this.printerObserver.setMembership(data);
				}
				
			}
			
		}
		
	}

}
