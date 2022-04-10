package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.AttendantActions;

public class AttendantActionsTestAUC2 {

	@Before
	public void setUp() throws Exception {
		Currency currency = Currency.getInstance("CAD");
		int[] banknoteDenominations = {1, 5, 10, 20, 50, 100};
		BigDecimal[] coinDenominations = {BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25)};
		
		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 1000, 1);
	}

	@Test
	public void overfillCoinDispenserTest() throws OverloadException{
		Currency currency = Currency.getInstance("CAD");
		int[] banknoteDenominations = {1, 5, 10, 20, 50, 100};
		BigDecimal[] coinDenominations = {BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25)};
		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 1000, 1);
		
		AttendantActions testAttendant = new AttendantActions();
		
		BigDecimal penny = new BigDecimal(0.01);
		Coin coin = new Coin(currency, penny);
		
		int capacity = 1;
		CoinDispenser dispenser = new CoinDispenser(capacity);
		
		station.coinDispensers.put(penny, dispenser);
		
		AttendantActions.fillCoinDispenser(station, penny, coin);
		AttendantActions.fillCoinDispenser(station, penny, coin);
	}
	
	@Test
	public void fillWithNullCoinDispenserTest() throws SimulationException {
		Currency currency = Currency.getInstance("CAD");
		int[] banknoteDenominations = {1, 5, 10, 20, 50, 100};
		BigDecimal[] coinDenominations = {BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25)};
		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 1000, 1);
		
		BigDecimal nullValue = new BigDecimal(1);
		
		BigDecimal nullCoin = new BigDecimal();
		
		
		int capacity = 1;
		CoinDispenser dispenser = new CoinDispenser(capacity);
		
		station.coinDispensers.put(nullCoin, dispenser);
		
		AttendantActions.fillCoinDispenser(station, nullCin, coin);
	}
}
