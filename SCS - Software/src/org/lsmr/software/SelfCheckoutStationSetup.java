package org.lsmr.software;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

/**
 * Contains initial data for the self checkout station such as
 * currency, monetary denominations, and other.
 *
 * A more realistic way would be to load this from somewhere else
 * because it should not be hardcoded, but for simplicity keep it
 * this way.
 */
public final class SelfCheckoutStationSetup {
	public static final Currency currency = Currency.getInstance(Locale.CANADA);
	public static final int[] banknoteDenoms = new int[] {5, 10, 20, 50, 100};
	public static final BigDecimal[] coinDenoms = new BigDecimal[] {
			new BigDecimal("0.05"), 
			new BigDecimal("0.10"), 
			new BigDecimal("0.25"), 
			new BigDecimal("1.00"), 
			new BigDecimal("2.00")
		};
	public static final int defaultMaxScaleWeight = 1000;
	public static final int defaultSensitivity = 1;
	
	public static SelfCheckoutStation createSelfCheckoutStationFromInit() {
		return new SelfCheckoutStation(currency, banknoteDenoms, coinDenoms, defaultMaxScaleWeight, defaultSensitivity);
	}
}
