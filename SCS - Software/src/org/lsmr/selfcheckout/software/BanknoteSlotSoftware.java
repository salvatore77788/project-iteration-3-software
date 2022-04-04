package org.lsmr.selfcheckout.software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

public class BanknoteSlotSoftware implements BanknoteDispenserObserver, BanknoteStorageUnitObserver, BanknoteValidatorObserver{
	
	BigDecimal[] amountPaid;
	
	public BanknoteSlotSoftware(BigDecimal[] amountPaid) {
		this.amountPaid = amountPaid;
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
	public void banknotesFull(BanknoteStorageUnit unit) {
		System.err.println("Storage is full, please do not use station.");
		
	}

	@Override
	public void banknoteAdded(BanknoteStorageUnit unit) {
		//If the admin wants to check how many banknotes are in the storage.
		
	}

	@Override
	public void banknotesLoaded(BanknoteStorageUnit unit) {
		// Admin loads storage with banknotes
		
	}

	@Override
	public void banknotesUnloaded(BanknoteStorageUnit unit) {
		// Admin unloads all banknotes
	}

	@Override
	public void moneyFull(BanknoteDispenser dispenser) {
		// We should not emit more banknotes if the dispenser is full. Aka. check dispenser if it's full
		
	}

	@Override
	public void banknotesEmpty(BanknoteDispenser dispenser) {
		// ?? Who cares
		
	}

	@Override
	public void billAdded(BanknoteDispenser dispenser, Banknote banknote) {
		// If bill has been added, then 
		
	}

	@Override
	public void banknoteRemoved(BanknoteDispenser dispenser, Banknote banknote) {
		
	}

	@Override
	public void banknotesLoaded(BanknoteDispenser dispenser, Banknote... banknotes) {
		//For testing
		
	}

	@Override
	public void banknotesUnloaded(BanknoteDispenser dispenser, Banknote... banknotes) {
		//For testing
		
	}

	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		BigDecimal inserted = (amountPaid[0].add(BigDecimal.valueOf(value)));
		this.amountPaid[0] = inserted;
		
	}

	@Override
	public void invalidBanknoteDetected(BanknoteValidator validator) {
		System.err.println("InvalidBanknoteDetected! Banknote is returning.");
		
	}

}
