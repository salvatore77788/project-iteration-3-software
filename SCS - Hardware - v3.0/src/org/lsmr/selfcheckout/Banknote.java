package org.lsmr.selfcheckout;

import java.util.Currency;

/**
 * Instances of this class represent individual banknotes. The value of a
 * banknote is assumed to always be a positive integer multiple of the base
 * currency.
 */
public class Banknote {
	private int value;
	private Currency currency;

	/**
	 * Constructs a banknote.
	 * 
	 * @param currency
	 *            The currency represented by this banknote.
	 * @param value
	 *            The value of the banknote, in multiples of the unit of currency.
	 * @throws SimulationException
	 *             If the value is &le;0.
	 * @throws SimulationException
	 *             If currency is null.
	 */
	public Banknote(Currency currency, int value) {
		if(currency == null)
			throw new NullPointerSimulationException("Null is not a valid currency.");

		if(value <= 0)
			throw new InvalidArgumentSimulationException(
				"The value must be greater than 0: the argument passed was " + value);

		this.value = value;
		this.currency = currency;
	}

	/**
	 * Accessor for the value.
	 * 
	 * @return The value of the banknote. Should always be &gt;0.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Accessor for the currency.
	 * 
	 * @return The currency for this banknote. Note that this is not the same as the
	 *             "denomination" (e.g., a Canadian $10 bill is worth 10 Canadian
	 *             dollars, so a Canadian $10 bill would have currency "Canadian
	 *             dollars").
	 */
	public Currency getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		return Integer.toString(value) + " " + currency;
	}
}
