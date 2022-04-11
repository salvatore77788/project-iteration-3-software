package org.lsmr.selfcheckout.software;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.CoinDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.CoinStorageUnitObserver;

public class AvailableFunds {
	private SelfCheckoutStation station;
//	private boolean disbursingChange;
	
//	private BigDecimal changeAmount;
//	private int bnChangeIndex;
	
	private boolean isCoinStorageFull;
	private boolean isBanknoteStorageFull;

	private Map<Integer, BanknoteDispenserWatcher> banknoteDispenserWatchers;
	private Map<BigDecimal, CoinDispenserWatcher> coinDispenserWatchers;
	private CoinStorageUnitObserver coinStorageObserver = new CoinStorageUnitObserver() {

		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void coinsFull(CoinStorageUnit unit) {
			isCoinStorageFull = true;
		}

		@Override
		public void coinAdded(CoinStorageUnit unit) {
			// TODO Can keep track of how much is added to the storage unit here to get percentage full
		}

		@Override
		public void coinsLoaded(CoinStorageUnit unit) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void coinsUnloaded(CoinStorageUnit unit) {
			isCoinStorageFull = false;
		}
		
	};
	private BanknoteStorageUnitObserver banknoteStorageObserver = new BanknoteStorageUnitObserver() {

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
			isBanknoteStorageFull = true;
		}

		@Override
		public void banknoteAdded(BanknoteStorageUnit unit) {
			// TODO Can keep track of how much is added to the storage unit here to get percentage full
			
		}

		@Override
		public void banknotesLoaded(BanknoteStorageUnit unit) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void banknotesUnloaded(BanknoteStorageUnit unit) {
			isBanknoteStorageFull = false;
		}
		
	};
	
	/**
	 * Maintains a count of the banknotes for a hardware banknote dispenser
	 */
	private class BanknoteDispenserWatcher implements BanknoteDispenserObserver {
		private int denomination;
		private int count;
		
		public BanknoteDispenserWatcher(int denomination) {
			this.denomination = denomination;
			count = SelfCheckoutStation.BANKNOTE_DISPENSER_CAPACITY;
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
			count = 0;
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
			count = SelfCheckoutStation.COIN_DISPENSER_CAPACITY;
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
			count = 0;
		}
		
		public BigDecimal getAmountStored() {
			return denomination.multiply(new BigDecimal(count));
		}
	}
	
	// Methods
	public AvailableFunds(SelfCheckoutStation station) {
		this.station = station;
		
//		disbursingChange = false;
//		
//		banknoteOutputSlotWatcher = new BanknoteOutputSlotWatcher();
//		station.banknoteOutput.attach(banknoteOutputSlotWatcher);
		
		// Create and connect banknote dispenser watchers
		int[] bnDenoms = station.banknoteDenominations;
		//Arrays.sort(bnDenoms);
		banknoteDispenserWatchers = new HashMap<Integer, BanknoteDispenserWatcher>();
		for(int i = 0; i < bnDenoms.length; i++) {
			int denom = bnDenoms[i];
			BanknoteDispenserWatcher watcher = new BanknoteDispenserWatcher(denom);
			
			banknoteDispenserWatchers.put(denom, watcher);
		}
		
		// Create and connect coin dispenser watchers
		List<BigDecimal> cDenoms = station.coinDenominations;
		coinDispenserWatchers = new HashMap<BigDecimal, CoinDispenserWatcher>();
		for(int i = 0; i < cDenoms.size(); i++) {
			BigDecimal denom = cDenoms.get(i);
			CoinDispenserWatcher watcher = new CoinDispenserWatcher(denom);
			
			coinDispenserWatchers.put(denom, watcher);
		}
	}
	
	public void attachAll() {
		// Connect all observers
		for(BanknoteDispenserWatcher w : banknoteDispenserWatchers.values())
			station.banknoteDispensers.get(w.getDenomination()).attach(w);
		
		for(CoinDispenserWatcher w : coinDispenserWatchers.values())
			station.coinDispensers.get(w.getDenomination()).attach(w);
		
		station.coinStorage.attach(coinStorageObserver);
		station.banknoteStorage.attach(banknoteStorageObserver);
	}
	
	public void detachAll() {
		// Disconnect all observers
		for(BanknoteDispenserWatcher w : banknoteDispenserWatchers.values())
			station.banknoteDispensers.get(w.getDenomination()).detach(w);
		
		for(CoinDispenserWatcher w : coinDispenserWatchers.values())
			station.coinDispensers.get(w.getDenomination()).detach(w);
		
		station.coinStorage.detach(coinStorageObserver);
		station.banknoteStorage.detach(banknoteStorageObserver);
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
		for(CoinDispenserWatcher w : coinDispenserWatchers.values())
			total = total.add(w.getAmountStored());
		
		return total;
	}
	
	private BigDecimal getTotalBanknoteAmount() {
		BigDecimal total = BigDecimal.ZERO;
		for(BanknoteDispenserWatcher w : banknoteDispenserWatchers.values())
			total = total.add(w.getAmountStored());
		
		return total;
	}
	
	/**
	 * Returns whether the coin storage unit is full.
	 * @return True if the coin storage unit is full. False otherwise.
	 */
	public boolean getIsCoinStorageFull() {
		return isCoinStorageFull;
	}
	
	/**
	 * Returns whether the banknote storage unit is full.
	 * @return True if the banknote storage unit is full. False otherwise.
	 */
	public boolean getIsBanknoteStorageFull() {
		return isBanknoteStorageFull;
	}
	
	/**
	 * Retrieves the number of coins of a given denomination are available to dispense.
	 * @param coinDenom Coin denomination to check.
	 * @return Number of the given coin denomination stored in the station dispensers or zero if the coin denomination does not map to a CoinDispenser.
	 */
	public int getCoinCount(BigDecimal coinDenom) {
		if(coinDispenserWatchers.containsKey(coinDenom))
			return coinDispenserWatchers.get(coinDenom).getCount();
		else
			return 0;
	}
	
	/**
	 * Retrieves the number of banknotes of a given denomination are available to dispense.
	 * @param coinDenom Banknote denomination to check.
	 * @return Number of the given banknote denomination stored in the station dispensers or zero if the banknote denomination does not map to a BanknoteDispenser.
	 */
	public int getBanknoteCount(int bnDenom) {
		if(banknoteDispenserWatchers.containsKey(bnDenom))
			return banknoteDispenserWatchers.get(bnDenom).getCount();
		else
			return 0;
	}
	
	public void emptyAll() {
		for(CoinDispenserWatcher w : coinDispenserWatchers.values()) {
			station.coinDispensers.get(w.denomination).unload();
		}
		
		for(BanknoteDispenserWatcher w: banknoteDispenserWatchers.values())
			station.banknoteDispensers.get(w.denomination).unload();
		
		station.coinStorage.unload();
		station.banknoteStorage.unload();
	}
}
