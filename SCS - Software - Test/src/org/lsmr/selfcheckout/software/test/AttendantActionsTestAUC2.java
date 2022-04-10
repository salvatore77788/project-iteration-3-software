package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.AttendantActions;

public class AttendantActionsTestAUC2 {

	@Before
	public void setUp() throws Exception {
//		Currency currency = Currency.getInstance("CAD");
//		int[] banknoteDenominations = {1, 5, 10, 20, 50, 100};
//		BigDecimal[] coinDenominations = {BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25)};
//		
//		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 1000, 1);
	}

	@Test
	public void overfillCoinDispenserTest() throws OverloadException {
		Currency currency = Currency.getInstance("CAD");
		int[] banknoteDenominations = {5, 10, 20, 50, 100};
		BigDecimal[] coinDenominations = {BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), BigDecimal.valueOf(1), BigDecimal.valueOf(2)};
		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 1000, 1);
		
		AttendantActions testAttendant = new AttendantActions();
		
		BigDecimal penny = new BigDecimal(0.01);
		Coin coin = new Coin(currency, penny);
		
		int capacity = 1;
		CoinDispenser dispenser = new CoinDispenser(capacity);
		
		station.coinDispensers.put(penny, dispenser);
		
		testAttendant.fillCoinDispenser(station, penny, coin);
		testAttendant.fillCoinDispenser(station, penny, coin);
	}
	
	@Test
	public void overfillBanknoteDispenserTest() throws OverloadException {
		Currency currency = Currency.getInstance("CAD");
		int[] banknoteDenominations = {5, 10, 20, 50, 100};
		BigDecimal[] coinDenominations = {BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), BigDecimal.valueOf(1), BigDecimal.valueOf(2)};
		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 1000, 1);
		
		AttendantActions testAttendant = new AttendantActions();
		
		int banknoteValue = 5;
		Banknote banknote = new Banknote(currency, banknoteValue);
		
		
		int capacity = 1;
		BanknoteDispenser dispenser = new BanknoteDispenser(capacity);
		
		station.banknoteDispensers.put(banknoteValue, dispenser);
		
		testAttendant.fillBanknoteDispenser(station, banknoteValue, banknote);
		testAttendant.fillBanknoteDispenser(station, banknoteValue, banknote);
	}
	
	@Test
	public void emptyCoinStorageUnitTest() throws SimulationException, OverloadException {
		Currency currency = Currency.getInstance("CAD");
		int[] banknoteDenominations = {5, 10, 20, 50, 100};
		BigDecimal[] coinDenominations = {BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), BigDecimal.valueOf(1), BigDecimal.valueOf(2)};
		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 1000, 1);
		
		AttendantActions testAttendant = new AttendantActions();
		
		int capacity = 1000;
		CoinStorageUnit storage = new CoinStorageUnit(capacity);
		
		BigDecimal value = new BigDecimal(0.01);
		Coin coin = new Coin(currency, value);
		
		station.coinStorage.load(coin);
		
		List<Coin> storageContents = new ArrayList<Coin>();
		storageContents.add(coin);
		testAttendant.emptyCoinStorageUnit(station);
		Assert.assertEquals(storageContents, testAttendant.emptyCoinStorageUnit(station));
	
	}
	
	@Test
	public void blockStationTest() {
		Currency currency = Currency.getInstance("CAD");
		int[] banknoteDenominations = {1, 5, 10, 20, 50, 100};
		BigDecimal[] coinDenominations = {BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25)};
		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 1000, 1);
	
		AttendantActions testAttendant = new AttendantActions();
		
		testAttendant.attendantBlockStation(station);
	}
	
	@Test
	public void unBlockStationTest() {
		Currency currency = Currency.getInstance("CAD");
		int[] banknoteDenominations = {1, 5, 10, 20, 50, 100};
		BigDecimal[] coinDenominations = {BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25)};
		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 1000, 1);
		
		AttendantActions testAttendant = new AttendantActions();
		
		testAttendant.attendantUnBlockStation(station);
	}
}
