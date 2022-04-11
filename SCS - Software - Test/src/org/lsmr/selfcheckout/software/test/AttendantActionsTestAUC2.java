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
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.AttendantActions;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSetup;

public class AttendantActionsTestAUC2 {
	SelfCheckoutStation station;
	Currency currency;
	
	@Before
	public void setUp() throws Exception {
		currency = Currency.getInstance("CAD");
		
		station = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
	}

	@Test
	public void overfillCoinDispenserTest() throws OverloadException {	
		BigDecimal penny = new BigDecimal(0.01);
		Coin coin = new Coin(currency, penny);
		
		int capacity = 1;
		CoinDispenser dispenser = new CoinDispenser(capacity);
		
		station.coinDispensers.put(penny, dispenser);
		
		AttendantActions.fillCoinDispenser(station, penny, coin);
		AttendantActions.fillCoinDispenser(station, penny, coin);
	}
	
	@Test
	public void overfillBanknoteDispenserTest() throws OverloadException {
		int banknoteValue = 5;
		Banknote banknote = new Banknote(currency, banknoteValue);
		
		
		int capacity = 1;
		BanknoteDispenser dispenser = new BanknoteDispenser(capacity);
		
		station.banknoteDispensers.put(banknoteValue, dispenser);
		
		AttendantActions.fillBanknoteDispenser(station, banknoteValue, banknote);
		AttendantActions.fillBanknoteDispenser(station, banknoteValue, banknote);
	}
	
	@Test
	public void emptyCoinStorageUnitTest() throws SimulationException, OverloadException {
		BigDecimal value = new BigDecimal(0.01);
		Coin coin = new Coin(currency, value);
		
		station.coinStorage.load(coin);
		
		List<Coin> storageContents = new ArrayList<Coin>();
		storageContents.add(coin);
		//Assert.assertEquals(storageContents, AttendantActions.emptyCoinStorageUnit(station));
		Assert.assertEquals(coin, AttendantActions.emptyCoinStorageUnit(station).get(0));
	
	}
	
	@Test
	public void emptyBanknoteStorageUnitTest() throws SimulationException, OverloadException {
		Banknote bn = new Banknote(currency, 5);
		
		station.banknoteStorage.load(bn);
		
		//Assert.assertEquals(storageContents, AttendantActions.emptyCoinStorageUnit(station));
		Assert.assertEquals(bn, AttendantActions.emptyBanknoteStorageUnit(station).get(0));
	
	}
	
	@Test
	public void blockStationTest() {
		AttendantActions testAttendant = new AttendantActions();
		
		testAttendant.attendantBlockStation(station);
	}
	
	@Test
	public void unBlockStationTest() {
		AttendantActions testAttendant = new AttendantActions();
		
		testAttendant.attendantUnBlockStation(station);
	}
}
