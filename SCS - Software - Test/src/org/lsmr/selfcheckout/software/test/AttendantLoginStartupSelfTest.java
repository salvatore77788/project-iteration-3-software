package org.lsmr.selfcheckout.software.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.devices.observers.BanknoteDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteSlotObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;
import org.lsmr.selfcheckout.devices.observers.CoinDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.CoinSlotObserver;
import org.lsmr.selfcheckout.devices.observers.CoinStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.CoinTrayObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.lsmr.selfcheckout.devices.observers.KeyboardObserver;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;
import org.lsmr.selfcheckout.devices.observers.TouchScreenObserver;
import org.lsmr.selfcheckout.software.AttendantLoginStartup;
import org.lsmr.selfcheckout.software.AttendantStation;
import org.lsmr.selfcheckout.software.ElectronicScaleSoftware;
import org.lsmr.selfcheckout.software.PasswordDatabase;
import org.lsmr.selfcheckout.software.TouchScreenSoftware;

/**
 * Tests the startup and shutdown functionality of AttendantLoginStartup in the context of starting up and shutting down SelfCheckoutStations. 
 * IMPORTANT: UserID = "admin", password = "admin"
 * 
 */

public class AttendantLoginStartupSelfTest {
	AttendantLoginStartup als;
	PasswordDatabase pd;
	AttendantStation as;
	SupervisionStation ss;
	SelfCheckoutStation scs;
	ElectronicScaleSoftware ess;
	SelfCheckoutStation nullStation;
	
	ArrayList<ElectronicScaleObserver> baggingAreaObservers;
	ArrayList<ElectronicScaleObserver> scanningAreaObservers;
	ArrayList<TouchScreenObserver> screenObservers;
	ArrayList<ReceiptPrinterObserver> printerObservers;
	ArrayList<CardReaderObserver> cardReaderObservers;
	ArrayList<BarcodeScannerObserver> mainScannerObservers;
	ArrayList<BarcodeScannerObserver> handheldScannerObservers;
	ArrayList<BanknoteSlotObserver> banknoteInputObservers;
	ArrayList<BanknoteSlotObserver> banknoteOutputObservers;
	ArrayList<BanknoteValidatorObserver> banknoteValidatorObservers;
	ArrayList<BanknoteStorageUnitObserver> banknoteStorageObservers;
	HashMap<Integer, ArrayList<BanknoteDispenserObserver>> banknoteDispenserObservers;
	ArrayList<CoinSlotObserver> coinSlotObservers;
	ArrayList<CoinValidatorObserver> coinValidatorObservers;
	ArrayList<CoinStorageUnitObserver> coinStorageObservers;
	HashMap<BigDecimal, ArrayList<CoinDispenserObserver>> coinDispenserObservers;
	ArrayList<CoinTrayObserver> coinTrayObservers;
	
	
	@Before
	public void testSetup() {
		als = new AttendantLoginStartup();
		pd = new PasswordDatabase(new HashMap<String, String>());
		as = new AttendantStation();
		ss = new SupervisionStation();
		
		Currency currency = Currency.getInstance("CAD");
        int[] banknoteDenominations = { 5 };
        BigDecimal[] coinDenominations = { new BigDecimal(0.25) };
		scs = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 1, 1);
		ess = new ElectronicScaleSoftware(scs);
		
		baggingAreaObservers = new ArrayList<ElectronicScaleObserver>();
		baggingAreaObservers.add(ess);
		scanningAreaObservers = new ArrayList<ElectronicScaleObserver>();
		screenObservers = new ArrayList<TouchScreenObserver>();
		printerObservers = new ArrayList<ReceiptPrinterObserver>();
		cardReaderObservers = new ArrayList<CardReaderObserver>();
		mainScannerObservers = new ArrayList<BarcodeScannerObserver>();
		handheldScannerObservers = new ArrayList<BarcodeScannerObserver>();
		banknoteInputObservers = new ArrayList<BanknoteSlotObserver>();
		banknoteOutputObservers = new ArrayList<BanknoteSlotObserver>();
		banknoteValidatorObservers = new ArrayList<BanknoteValidatorObserver>();
		banknoteStorageObservers = new ArrayList<BanknoteStorageUnitObserver>();
		banknoteDispenserObservers = new HashMap<Integer, ArrayList<BanknoteDispenserObserver>>();
		coinSlotObservers = new ArrayList<CoinSlotObserver>();
		coinValidatorObservers = new ArrayList<CoinValidatorObserver>();
		coinStorageObservers = new ArrayList<CoinStorageUnitObserver>();
		coinDispenserObservers = new HashMap<BigDecimal, ArrayList<CoinDispenserObserver>>();
		coinTrayObservers = new ArrayList<CoinTrayObserver>();
		
		ArrayList<BanknoteDispenserObserver> banknoteDispenserObserverList = new ArrayList<BanknoteDispenserObserver>();
		ArrayList<CoinDispenserObserver> coinDispenserObserverList = new ArrayList<CoinDispenserObserver>();
		banknoteDispenserObservers.put(5, banknoteDispenserObserverList);
		coinDispenserObservers.put(new BigDecimal(0.25), coinDispenserObserverList);
		
	}
	
	@Test
	public void addStandard() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);
		als.startup(scs);
		scs.baggingArea.disable();
		Assert.assertEquals(true, ess.getIsDisabled());
	}
	
	@Test
	public void addLoggedOut() {
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);
		als.startup(scs);
		scs.baggingArea.disable();
		Assert.assertEquals(false, ess.getIsDisabled());
	}
	
	@Test
	public void addStationAlreadyThere() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);		als.startup(scs);
		scs.baggingArea.disable();
		Assert.assertEquals(true, ess.getIsDisabled());
		
		int stationCountA = als.getAllSuperStations().size();
		
		ArrayList<TouchScreenObserver> newScreenObservers = new ArrayList<TouchScreenObserver>();
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);		
		//test that list of stations didn't grow
		int stationCountB = als.getAllSuperStations().size();
		Assert.assertEquals(stationCountA, stationCountB);
		
		//test that observers not overwritten
		scs.baggingArea.enable();
		Assert.assertEquals(false, ess.getIsDisabled());
	}
	
	@Test
	public void addNullStation() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);
		Assert.assertEquals(false, als.getAllSuperStations().contains(null));
	}
	
	@Test
	public void removeStandard() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);
		als.removeStation(scs);
		als.startup(scs);
		scs.baggingArea.disable();
		Assert.assertEquals(false, ess.getIsDisabled());
	}
	
	@Test
	public void removeLoggedOut() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);
		als.logout(ss);
		als.removeStation(scs);
		als.login(ss, pd);
		als.startup(scs);
		scs.baggingArea.disable();
		Assert.assertEquals(true, ess.getIsDisabled());
	}
	
	
	@Test
	public void removeStationMissing() {
		als.login(ss, pd);
		als.removeStation(scs);
		
		// test passes if no exception is thrown
	}
	
	@Test
	public void removeNullStation() {
		als.login(ss, pd);
		als.removeStation(nullStation);
		
		//test passes if no exception is thrown
	}
	
	@Test
	public void startupStandard() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);
		scs.baggingArea.disable();
		Assert.assertEquals(false, ess.getIsDisabled());
		als.startup(scs);
		scs.baggingArea.disable();
		Assert.assertEquals(true, ess.getIsDisabled());
	}
	
	@Test
	public void startupLoggedOut() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);		
		als.logout(ss);
		als.startup(scs);
		als.login(ss, pd);
		scs.baggingArea.disable();
		Assert.assertEquals(false, ess.getIsDisabled());
	}
	
	@Test
	public void startupStationMissing() {
		als.login(ss, pd);
		als.startup(scs);
		scs.baggingArea.disable();
		Assert.assertEquals(false, ess.getIsDisabled());
	}
	
	@Test
	public void startupNullStation() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);		
		als.startup(nullStation);
		
		// test passes if no exception is thrown
	}
	
	@Test
	public void shutdownStandard() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);		
		als.startup(scs);
		als.shutdown(scs);
		scs.baggingArea.disable();
		Assert.assertEquals(false, ess.getIsDisabled());
	}
	
	@Test
	public void shutdownLoggedOut() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);		
		als.startup(scs);
		als.logout(ss);
		als.shutdown(scs);
		als.login(ss, pd);
		scs.baggingArea.disable();
		Assert.assertEquals(true, ess.getIsDisabled());
	}
	
	@Test
	public void shutdownMissingStation() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);
		als.startup(scs);
		als.removeStation(scs);
		als.shutdown(scs);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);		
		scs.baggingArea.disable();
		Assert.assertEquals(true, ess.getIsDisabled());
	}
	
	@Test
	public void shutdownNullStation() {
		als.login(ss, pd);
		als.addStation(scs, baggingAreaObservers, scanningAreaObservers, screenObservers, printerObservers, cardReaderObservers,
				mainScannerObservers, handheldScannerObservers, banknoteInputObservers, banknoteOutputObservers, banknoteValidatorObservers,
				banknoteStorageObservers, banknoteDispenserObservers, coinSlotObservers, coinValidatorObservers, coinStorageObservers,
				coinDispenserObservers, coinTrayObservers);		
		als.startup(nullStation);
		als.shutdown(nullStation);
		
		// test passes if no exception is thrown
	}
}
