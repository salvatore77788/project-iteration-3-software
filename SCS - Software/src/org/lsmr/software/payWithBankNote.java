package org.lsmr.software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

public class payWithBankNote{
		public SelfCheckoutStation station;
		
		private int banknoteValue;
		
		public BigDecimal totalPaid = BigDecimal.valueOf(0);
		
		public payWithBankNote(SelfCheckoutStation station) {
			this.station = station;
			
			station.banknoteValidator.attach(validatorListener);
			station.banknoteStorage.attach(storageListener);
		}
		
		public void banknoteAccepted() {
			totalPaid = totalPaid.add(new BigDecimal(banknoteValue));
		}
		
		public BanknoteValidatorObserver validatorListener = new BanknoteValidatorObserver() {

			@Override
			public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
				// TODO Auto-generated method stub
				banknoteValue = value;
			}

			@Override
			public void invalidBanknoteDetected(BanknoteValidator validator) {
				// TODO Auto-generated method stub
				
			}
			
				
		};
		
		public BanknoteStorageUnitObserver storageListener = new BanknoteStorageUnitObserver () 
		{

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
				// TODO Auto-generated method stub
				
			}

			@Override
			public void banknoteAdded(BanknoteStorageUnit unit) {
				// TODO Auto-generated method stub
				banknoteAccepted();
			}

			@Override
			public void banknotesLoaded(BanknoteStorageUnit unit) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void banknotesUnloaded(BanknoteStorageUnit unit) {
				// TODO Auto-generated method stub
				
			}
			
		};
		

}
