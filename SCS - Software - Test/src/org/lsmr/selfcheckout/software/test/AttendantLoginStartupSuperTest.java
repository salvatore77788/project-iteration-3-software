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

public class AttendantLoginStartupSuperTest {
	AttendantLoginStartup als;
	PasswordDatabase pd;
	AttendantStation as;
	SupervisionStation ss;
	TouchScreenSoftware tss;
	
	ArrayList<TouchScreenObserver> screenObservers;
	ArrayList<KeyboardObserver> keyboardObservers;
	
	
	
	@Before
	public void testSetup() {
		als = new AttendantLoginStartup();
		pd = new PasswordDatabase(new HashMap<String, String>());
		as = new AttendantStation();
		ss = new SupervisionStation();
		tss = new TouchScreenSoftware(as);
		
		screenObservers = new ArrayList<TouchScreenObserver>();
		screenObservers.add(tss);
		keyboardObservers = new ArrayList<KeyboardObserver>();
	}
	
	@Test
	public void addStandard() {
		//als.login(ss, pd);
		als.addStation(ss, screenObservers, keyboardObservers);
		als.startup(ss);
		ss.screen.disable();
		Assert.assertEquals(true, tss.getIsDisabled());
	}
	
	@Test
	public void addLoggedOut() {
		als.addStation(ss, screenObservers, keyboardObservers);
		als.startup(ss);
		ss.screen.disable();
		Assert.assertEquals(false, tss.getIsDisabled());
	}
	
	@Test
	public void addStationAlreadyThere() {
		als.login(ss, pd);
		als.addStation(ss, screenObservers, keyboardObservers);
		als.startup(ss);
		ss.screen.disable();
		Assert.assertEquals(true, tss.getIsDisabled());
		
		int stationCountA = als.getAllSuperStations().size();
		
		ArrayList<TouchScreenObserver> newScreenObservers = new ArrayList<TouchScreenObserver>();
		als.addStation(ss, newScreenObservers, keyboardObservers);
		
		//test that list of stations didn't grow
		int stationCountB = als.getAllSuperStations().size();
		Assert.assertEquals(stationCountA, stationCountB);
		
		//test that observers not overwritten
		ss.screen.enable();
		Assert.assertEquals(false, tss.getIsDisabled());
	}
	
	@Test
	public void removeStandard() {
		//als.login(ss, pd);
		als.addStation(ss, screenObservers, keyboardObservers);
		als.removeStation(ss);
		als.startup(ss);
		ss.screen.disable();
		Assert.assertEquals(false, tss.getIsDisabled());
	}
	
	@Test
	public void removeLoggedOut() {
		//als.login(ss, pd);
		als.addStation(ss, screenObservers, keyboardObservers);
		als.logout(ss);
		als.removeStation(ss);
		als.startup(ss);
		ss.screen.disable();
		Assert.assertEquals(true, tss.getIsDisabled());
	}
	
	
	@Test
	public void removeStationMissing() {
		//als.login(ss, pd);
		als.removeStation(ss);
		
		// test passes if no exception is thrown
	}
	
	@Test
	public void startupStandard() {
		//als.login(ss, pd);
		als.addStation(ss, screenObservers, keyboardObservers);
		ss.screen.disable();
		Assert.assertEquals(false, tss.getIsDisabled());
		als.startup(ss);
		ss.screen.disable();
		Assert.assertEquals(true, tss.getIsDisabled());
	}
	
}
