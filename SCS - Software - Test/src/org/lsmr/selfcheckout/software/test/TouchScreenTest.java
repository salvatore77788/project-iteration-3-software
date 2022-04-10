package org.lsmr.selfcheckout.software.test;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.TouchScreen;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.lsmr.selfcheckout.devices.observers.TouchScreenObserver;
import org.lsmr.selfcheckout.software.AttendantStation;
import org.lsmr.selfcheckout.software.ElectronicScaleSoftware;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;
import org.lsmr.selfcheckout.software.TouchScreenSoftware;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Assert;

public class TouchScreenTest {

	public SelfCheckoutStation scs;
	public SelfCheckoutStationSoftware scss;
	public ElectronicScale electronicScale;
	
	@Before
	public void setup() throws Exception {
		
		Currency currency = Currency.getInstance(Locale.CANADA);
		int[] banknoteDenoms = new int[] {5, 10, 20, 50, 100};
		BigDecimal[] coinDenoms = new BigDecimal[] {
					new BigDecimal("0.05"), 
					new BigDecimal("0.10"), 
					new BigDecimal("0.25"), 
					new BigDecimal("1.00"), 
					new BigDecimal("2.00")
				};
		int defaultMaxScaleWeight = 1000;
		int defaultSensitivity = 1;
		
		scs = new SelfCheckoutStation(currency, banknoteDenoms, coinDenoms, defaultMaxScaleWeight, defaultSensitivity);
		
		scss = new SelfCheckoutStationSoftware(scs);
		
		electronicScale = new ElectronicScale(1,1);
		
	}

	@Test
	public void testDisabled() {
		scss.scs.screen.disable();
		Assert.assertTrue("TouchScreenSoftware.isDisabled should be true", scss.touchSnObserver.getIsDisabled() == true);
	}

	@Test
	public void testEnabled() {
		scss.scs.screen.enable();
		Assert.assertTrue("TouchScreenSoftware.isDisabled should be false",  scss.touchSnObserver.getIsDisabled() == false);
	}

	//
	@Test
	public void testAddBagsWhileEnabled() {
		//scss.scs.screen.enable();
		try { 
			scss.touchSnObserver.addBags(scss.scs.baggingArea); 
		} catch (DisabledException e) {
		  System.err.println("Touchscreen should be enabled"); 
		 }
		 
		Assert.assertTrue("Scale should be enabled", scss.scs.baggingArea.isDisabled() == false);
	}

	@Test
	public void testAddBagsWhileDisabled() {
		scss.scs.screen.disable();
		try { 
			scss.touchSnObserver.addBags(electronicScale); 
		} catch (DisabledException e) { 
			return; 
		}
		  System.err.println("Touchscreen should be disabled");
		 
	}

}
