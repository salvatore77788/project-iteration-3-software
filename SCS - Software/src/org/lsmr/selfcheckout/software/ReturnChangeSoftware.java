package org.lsmr.selfcheckout.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class ReturnChangeSoftware {

	private SelfCheckoutStation scs;
	private Currency currency = Currency.getInstance("CAD");
	private List<Integer> banknoteDenominations = new ArrayList<Integer>();

	public ReturnChangeSoftware(SelfCheckoutStation station) throws Exception {
		// connect the checkout station from the SelfCheckoutStationSoftware to this
		// class
		this.scs = station;

		// initialize the banknoteDenominations since the original copy provides a
		// hashcode instead of an array.
		for (int i : this.scs.banknoteDenominations) {
			banknoteDenominations.add(Integer.valueOf(i));
		}

		// for each type of denomination, load the denomination into their respective
		// dispenser to the maximum capacity.
		for (int i : this.scs.banknoteDenominations) {
			int counter = 0;
			while (counter < SelfCheckoutStation.BANKNOTE_DISPENSER_CAPACITY) {
				Banknote note = new Banknote(currency, i);
				this.refillBanknoteDispenser(note);
				counter++;
			}
		}

		// for each type of denomination, load the denomination into their respective
		// dispenser to the maximum capacity.
		for (BigDecimal i : this.scs.coinDenominations) {
			int counter = 0;
			while (counter < SelfCheckoutStation.COIN_DISPENSER_CAPACITY) {
				Coin coin = new Coin(currency, i);
				this.refillCoinDispenser(coin);
				counter++;
			}
		}

	}

	/**
	 * Purpose: function to load coins into its dispenser
	 * 
	 * @param Coin... coins a list of coins to be loaded
	 * @return void
	 * @throws Exception
	 */
	public void refillCoinDispenser(Coin... coins) throws Exception {
		for (Coin c : coins) {
			if (c == null) {
				throw new Exception("CoinThis coin is null.");
			}
			this.scs.coinDispensers.get(c.getValue()).load(c);
		}
	}

	/**
	 * Purpose: function to load banknotes into its dispenser
	 * 
	 * @param Banknote... banknotes a list of banknotes to be loaded
	 * @return void
	 * @throws Exception
	 */
	public void refillBanknoteDispenser(Banknote... banknotes) throws Exception {
		for (Banknote b : banknotes) {
			if (b == null) {
				throw new Exception("This coin is null.");
			}
			this.scs.banknoteDispensers.get(b.getValue()).load(b);
		}
	}

	/**
	 * Purpose: get the array of Banknote Denominations.
	 */
	public List<Integer> getBanknoteDenomination() {
		return banknoteDenominations;
	}
}
