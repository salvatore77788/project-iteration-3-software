package org.lsmr.software;
import java.math.BigDecimal;

import org.lsmr.selfcheckout.BlockedCardException;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.ChipFailureException;
import org.lsmr.selfcheckout.IllegalDigitException;
import org.lsmr.selfcheckout.InvalidPINException;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;

public class PayWithCard {
	
	//initiate station
	public SelfCheckoutStation station;
	//initialize total paid to zero
	public BigDecimal totalPaid;
	public boolean cardisSwiped = false;
	public boolean cardisTapped = false;
	public boolean cardisInserted = false;
	public boolean success = false;
	
	private class CardReaderWatcher implements CardReaderObserver {
		private CardData cardData;
		
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
			cardisInserted = true;
		}
	
		@Override
		public void cardRemoved(CardReader reader) {
		}
	
		@Override
		public void cardTapped(CardReader reader) {
			cardisTapped = true;

		}
	
		@Override
		public void cardSwiped(CardReader reader) {
			cardisSwiped = true;
		}
	
		@Override
		public void cardDataRead(CardReader reader, CardData data) {
			cardData = data;
			if (cardisSwiped = true && cardData.getCardholder()!=null && cardData.getNumber()!=null)
				if (cardData.getType()=="credit"||cardData.getType()=="debit") {
					success=true;
				}	
			if (cardisTapped = true && cardData.getCardholder()!=null && cardData.getNumber()!=null)
				if (cardData.getType()=="credit"||cardData.getType()=="debit") {
					success=true;
			
			if (cardisInserted = true && cardData.getCardholder()!=null && cardData.getNumber()!=null)
				if (cardData.getType()=="credit"||cardData.getType()=="debit") {
					success=true;}

			}else {System.out.println("Invalid Card Type");}
			
		}

	}
	
	public PayWithCard(SelfCheckoutStation station) {
		this.station = station;
		CardReaderWatcher mywatch = new CardReaderWatcher();
		station.cardReader.attach(mywatch);
			
	}
	
	public BigDecimal acceptMoney(BigDecimal totalOwed) {
		BigDecimal ret = BigDecimal.ZERO;
		if (success) {
			ret = totalOwed;
		}
		return ret;
	}
	
}
	