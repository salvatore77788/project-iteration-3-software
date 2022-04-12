package org.lsmr.selfcheckout.software.test;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.devices.observers.KeyboardObserver;
import org.lsmr.selfcheckout.devices.observers.TouchScreenObserver;
import org.lsmr.selfcheckout.software.AttendantLoginStartup;
import org.lsmr.selfcheckout.software.AttendantStation;
import org.lsmr.selfcheckout.software.PasswordDatabase;
import org.lsmr.selfcheckout.software.TouchScreenSoftware;


/**
 * Tests the startup and shutdown functionality of AttendantLoginStartup in the context of starting up and shutting down SupervisionStations. 
 * IMPORTANT: UserID = "admin", password = "admin"
 * 
 */
public class AttendantLoginStartupSuperTest {
	private AttendantLoginStartup als;
	private PasswordDatabase pd;
	AttendantStation as;
	private SupervisionStation ss1;
	SupervisionStation ss2;
	TouchScreenSoftware tss;
	SupervisionStation nullStation;
	
	ArrayList<TouchScreenObserver> screenObservers;
	ArrayList<KeyboardObserver> keyboardObservers;
	
	
	@Before
	public void testSetup() {
		als = new AttendantLoginStartup();
		pd = new PasswordDatabase(new HashMap<String, String>());
		as = new AttendantStation();
		ss1 = new SupervisionStation();
		ss2 = new SupervisionStation();
		tss = new TouchScreenSoftware(as);
		
		screenObservers = new ArrayList<TouchScreenObserver>();
		screenObservers.add(tss);
		keyboardObservers = new ArrayList<KeyboardObserver>();
	}
	
	@Test
	public void addStandard() {
		als.login(ss1, pd);
		als.addStation(ss2, screenObservers, keyboardObservers);
		als.startup(ss2);
		ss2.screen.disable();
		Assert.assertEquals(true, tss.getIsDisabled());
	}
	
	@Test
	public void addLoggedOut() {
		als.addStation(ss2, screenObservers, keyboardObservers);
		als.startup(ss2);
		ss2.screen.disable();
		Assert.assertEquals(false, tss.getIsDisabled());
	}
	
	@Test
	public void addStationAlreadyThere() {
		als.login(ss1, pd);
		als.addStation(ss2, screenObservers, keyboardObservers);
		als.startup(ss2);
		ss2.screen.disable();
		Assert.assertEquals(true, tss.getIsDisabled());
		
		int stationCountA = als.getAllSuperStations().size();
		
		ArrayList<TouchScreenObserver> newScreenObservers = new ArrayList<TouchScreenObserver>();
		als.addStation(ss2, newScreenObservers, keyboardObservers);
		
		//test that list of stations didn't grow
		int stationCountB = als.getAllSuperStations().size();
		Assert.assertEquals(stationCountA, stationCountB);
		
		//test that observers not overwritten
		ss2.screen.enable();
		Assert.assertEquals(false, tss.getIsDisabled());
	}
	
	@Test
	public void addNullStation() {
		als.login(ss1, pd);
		als.addStation(nullStation, screenObservers, keyboardObservers);
		Assert.assertEquals(false, als.getAllSuperStations().contains(null));
	}
	
	@Test
	public void removeStandard() {
		als.login(ss1, pd);
		als.addStation(ss2, screenObservers, keyboardObservers);
		als.removeStation(ss2);
		als.startup(ss2);
		ss2.screen.disable();
		Assert.assertEquals(false, tss.getIsDisabled());
	}
	
	@Test
	public void removeLoggedOut() {
		als.login(ss1, pd);
		als.addStation(ss2, screenObservers, keyboardObservers);
		als.logout(ss1);
		als.removeStation(ss2);
		als.login(ss1, pd);
		als.startup(ss2);
		ss2.screen.disable();
		Assert.assertEquals(true, tss.getIsDisabled());
	}
	
	
	@Test
	public void removeStationMissing() {
		als.login(ss1, pd);
		als.removeStation(ss2);
		
		// test passes if no exception is thrown
	}
	
	@Test
	public void removeNullStation() {
		als.login(ss1, pd);
		als.removeStation(nullStation);
		
		//test passes if no exception is thrown
	}
	
	@Test
	public void startupStandard() {
		als.login(ss1, pd);
		als.addStation(ss2, screenObservers, keyboardObservers);
		ss2.screen.disable();
		Assert.assertEquals(false, tss.getIsDisabled());
		als.startup(ss2);
		ss2.screen.disable();
		Assert.assertEquals(true, tss.getIsDisabled());
	}
	
	@Test
	public void startupLoggedOut() {
		als.login(ss1, pd);
		als.addStation(ss2, screenObservers, keyboardObservers);
		als.logout(ss1);
		als.startup(ss2);
		als.login(ss1, pd);
		ss2.screen.disable();
		Assert.assertEquals(false, tss.getIsDisabled());
	}
	
	@Test
	public void startupStationMissing() {
		als.login(ss1, pd);
		als.startup(ss2);
		ss2.screen.disable();
		Assert.assertEquals(false, tss.getIsDisabled());
	}
	
	@Test
	public void startupNullStation() {
		als.login(ss1, pd);
		als.addStation(nullStation, screenObservers, keyboardObservers);
		als.startup(nullStation);
		
		// test passes if no exception is thrown
	}
	
	@Test
	public void shutdownStandard() {
		als.login(ss1, pd);
		als.addStation(ss2, screenObservers, keyboardObservers);
		als.startup(ss2);
		als.shutdown(ss2);
		ss2.screen.disable();
		Assert.assertEquals(false, tss.getIsDisabled());
	}
	
	@Test
	public void shutdownLoggedOut() {
		als.login(ss1, pd);
		als.addStation(ss2, screenObservers, keyboardObservers);
		als.startup(ss2);
		als.logout(ss1);
		als.shutdown(ss2);
		als.login(ss1, pd);
		ss2.screen.disable();
		Assert.assertEquals(true, tss.getIsDisabled());
	}
	
	@Test
	public void shutdownMissingStation() {
		als.login(ss1, pd);
		als.addStation(ss2, screenObservers, keyboardObservers);
		als.startup(ss2);
		als.removeStation(ss2);
		als.shutdown(ss2);
		als.addStation(ss2, screenObservers, keyboardObservers);
		ss2.screen.disable();
		Assert.assertEquals(true, tss.getIsDisabled());
	}
	
	@Test
	public void shutdownNullStation() {
		als.login(ss1, pd);
		als.addStation(nullStation, screenObservers, keyboardObservers);
		als.startup(nullStation);
		als.shutdown(nullStation);
		
		// test passes if no exception is thrown
	}
}
