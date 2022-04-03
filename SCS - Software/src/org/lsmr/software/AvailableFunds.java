package org.lsmr.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteSlotObserver;
import org.lsmr.selfcheckout.devices.observers.CoinDispenserObserver;

public class AvailableFunds {
	private SelfCheckoutStation station;
	private boolean disbursingChange;
	
	private BigDecimal changeAmount;
	private int bnChangeIndex;
	
	private BanknoteDispenserWatcher[] banknoteDispenserWatchers;
	private CoinDispenserWatcher[] coinDispenserWatchers;
	private BanknoteOutputSlotWatcher banknoteOutputSlotWatcher;
	
	// Concrete observer classes
	/**
	 * A concrete implementation of a BanknoteSlotObserver
	 * Watches for when a banknote is removed, then if disbursing change,
	 * emit the next banknote/coins.
	 */
	private class BanknoteOutputSlotWatcher implements BanknoteSlotObserver { 

		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void banknoteInserted(BanknoteSlot slot) {
			// TODO Auto-generated method stub
			
		}

		public void banknoteEjected(BanknoteSlot slot) { //OVERRIDE REMOVED TO IGNORE ERROS NEEDS TO BE LOOKED AT
			// TODO Auto-generated method stub
		}

		@Override
		public void banknoteRemoved(BanknoteSlot slot) {
			// TODO Auto-generated method stub
			if(disbursingChange)
				disburseNextBanknote();
		}

		@Override
		public void banknotesEjected(BanknoteSlot slot) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * Maintains a count of the banknotes for a hardware banknote dispenser
	 */
	private class BanknoteDispenserWatcher implements BanknoteDispenserObserver {
		private int denomination;
		private int count;
		
		public BanknoteDispenserWatcher(int denomination) {
			this.denomination = denomination;
			count = 0;
		}
		
		public int getDenomination() {
			return denomination;
		}
		
		public int getCount() {
			return count;
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
		public void moneyFull(BanknoteDispenser dispenser) {
			// TODO Auto-generated method stub
		}

		@Override
		public void banknotesEmpty(BanknoteDispenser dispenser) {
			// TODO Auto-generated method stub
			count = 0;
		}

		@Override
		public void billAdded(BanknoteDispenser dispenser, Banknote banknote) {
			// TODO Auto-generated method stub
		}

		@Override
		public void banknoteRemoved(BanknoteDispenser dispenser, Banknote banknote) {
			// TODO Auto-generated method stub
			count--;
		}

		@Override
		public void banknotesLoaded(BanknoteDispenser dispenser, Banknote... banknotes) {
			// TODO Auto-generated method stub
			count += banknotes.length;
		}

		@Override
		public void banknotesUnloaded(BanknoteDispenser dispenser, Banknote... banknotes) {
			// TODO Auto-generated method stub
			count -= banknotes.length;
		}
		
		public BigDecimal getAmountStored() {
			return new BigDecimal(denomination*count);
		}
	}
	
	/**
	 * Maintains a count of the coins for a hardware coin dispenser.
	 */
	private class CoinDispenserWatcher implements CoinDispenserObserver {
		private BigDecimal denomination;
		private int count;
		
		public CoinDispenserWatcher(BigDecimal denom) {
			denomination = denom;
			count = 0;
		}
		
		public BigDecimal getDenomination() {
			return denomination;
		}
		
		public int getCount() {
			return count;
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
		public void coinsFull(CoinDispenser dispenser) {
			// TODO Auto-generated method stub
			count = station.COIN_DISPENSER_CAPACITY;
		}

		@Override
		public void coinsEmpty(CoinDispenser dispenser) {
			// TODO Auto-generated method stub
			count = 0;
		}

		@Override
		public void coinAdded(CoinDispenser dispenser, Coin coin) {
			// TODO Auto-generated method stub
			count++;
		}

		@Override
		public void coinRemoved(CoinDispenser dispenser, Coin coin) {
			// TODO Auto-generated method stub
			count--;
		}

		@Override
		public void coinsLoaded(CoinDispenser dispenser, Coin... coins) {
			// TODO Auto-generated method stub
			count += coins.length;
		}

		@Override
		public void coinsUnloaded(CoinDispenser dispenser, Coin... coins) {
			// TODO Auto-generated method stub
			count -= coins.length;
		}
		
		public BigDecimal getAmountStored() {
			return denomination.multiply(new BigDecimal(count));
		}
	}
	
	// Methods
	public AvailableFunds(SelfCheckoutStation station) {
		this.station = station;
		
		disbursingChange = false;
		
		banknoteOutputSlotWatcher = new BanknoteOutputSlotWatcher();
		station.banknoteOutput.attach(banknoteOutputSlotWatcher);
		
		// Create and connect banknote dispenser watchers
		int[] bnDenoms = station.banknoteDenominations.clone();
		Arrays.sort(bnDenoms);
		banknoteDispenserWatchers = new BanknoteDispenserWatcher[bnDenoms.length];
		for(int i = 0; i < bnDenoms.length; i++) {
			int denom = bnDenoms[i];
			BanknoteDispenserWatcher watcher = new BanknoteDispenserWatcher(denom);
			
			banknoteDispenserWatchers[i] = watcher;
			station.banknoteDispensers.get(denom).attach(watcher);
		}
		
		// Create and connect coin dispenser watchers
		List<BigDecimal> cDenoms = new ArrayList<BigDecimal>(station.coinDenominations);
		cDenoms.sort(null);
		coinDispenserWatchers = new CoinDispenserWatcher[cDenoms.size()];
		for(int i = 0; i < cDenoms.size(); i++) {
			BigDecimal denom = cDenoms.get(i);
			CoinDispenserWatcher watcher = new CoinDispenserWatcher(denom);
			
			coinDispenserWatchers[i] = watcher;
			station.coinDispensers.get(denom).attach(watcher);
		}
	}
	
	/**
	 * Retrieves the total amount of banknotes and coins available 
	 * to be dispensed in the selfcheckout station.
	 * @return Total amount of funds ready to be dispensed.
	 */
	public BigDecimal getTotalFundsStored() {
		return getTotalCoinsAmount().add(getTotalBanknoteAmount());
	}
	
	private BigDecimal getTotalCoinsAmount() {
		BigDecimal total = BigDecimal.ZERO;
		for(CoinDispenserWatcher w : coinDispenserWatchers)
			total = total.add(w.getAmountStored());
		
		return total;
	}
	
	private BigDecimal getTotalBanknoteAmount() {
		BigDecimal total = BigDecimal.ZERO;
		for(BanknoteDispenserWatcher w : banknoteDispenserWatchers)
			total = total.add(w.getAmountStored());
		
		return total;
	}
	
	/**
	 * Begins the process of disbursing change to the customer.
	 * @param amount The amount of change to disburse.
	 */
	public void beginDisbursingChange(BigDecimal amount) {
		if(disbursingChange)
			throw new IllegalStateException("Change is already being disbursed to the customer.");
		
		changeAmount = amount;
		bnChangeIndex = banknoteDispenserWatchers.length-1;
		disbursingChange = true;
		
		disburseNextBanknote();
	}
	
	/**
	 * Attempts to emit the next banknote as change.
	 * When the amount of change left to be dispensed is less than the denomination of the smallest
	 * bill or there are no bills left to be dispensed, disburses the rest as coins.
	 */
	private void disburseNextBanknote() {		
		BanknoteDispenserWatcher w = banknoteDispenserWatchers[bnChangeIndex];
		BigDecimal denom = new BigDecimal(w.getDenomination());
		
		if(w.getCount() > 0 && changeAmount.compareTo(denom) >= 0) {
			try {
				changeAmount = changeAmount.subtract(denom);
				station.banknoteDispensers.get(denom.intValue()).emit();
				//System.out.println(denom);
			} catch (EmptyException | DisabledException | OverloadException e) {
				// TODO handle more gracefully
				e.printStackTrace();
			}
		}
		else {
			bnChangeIndex--; 
			
			if(bnChangeIndex < 0) {
				disburseCoins();
			}
			else
				disburseNextBanknote();
		}
	}
	
	/**
	 * Disburses the rest of the change amount as coins, if possible, and finishes
	 * the process of disbursing change to the customer. 
	 */
	private void disburseCoins() {
		// Coins now
		if(changeAmount.compareTo(BigDecimal.ZERO) == 1) {
			for(int i = coinDispenserWatchers.length-1; i >= 0; i --) {
				CoinDispenserWatcher w = coinDispenserWatchers[i];
				BigDecimal denom = w.getDenomination();
				
				while(w.getCount() > 0 && changeAmount.compareTo(denom) >= 0) {
					// Output a coin
					try {
						station.coinDispensers.get(denom).emit();
						changeAmount = changeAmount.subtract(denom);
					} catch (OverloadException | EmptyException | DisabledException e) {
						// TODO handle more gracefully
						e.printStackTrace();
					}
				}
			}
		}
		
		// TODO some message or something that indicates whether the full amount of change has been disbursed or not
		
		disbursingChange = false;
	}
	
	/**
	 * Whether change is being given to the customer or not.
	 * @return True if change is being disbursed. False otherwise.
	 */
	public Boolean isDisbursingChange() {
		return disbursingChange;
	}
	
	/**
	 * The amount of change currently left to be returned. This is valid whether change is currently being returned or after.
	 * @return The amount of change left to be disbursed.
	 */
	public BigDecimal changeLeft() {
		return changeAmount;
	}
}
