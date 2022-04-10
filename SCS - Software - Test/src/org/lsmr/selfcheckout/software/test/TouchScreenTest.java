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
import org.lsmr.selfcheckout.software.TouchScreenSoftware;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Assert;




public class TouchScreenTest {
	

	// Has to be fixed due to endConfigurationPhase() error

	public SelfCheckoutStation scs;
	private AttendantStation as;
	private TouchScreenSoftware tss;
	private ElectronicScaleStub es;
	private ElectronicScale ess;
	private TouchScreenStub ts;
	private TouchScreen touchscreen;
	
	private enum Phase {
		CONFIGURATION,
		NORMAL,
		ERROR
	}
	
	private class TouchScreenStub implements TouchScreenObserver {
		
		protected Phase phase = Phase.NORMAL;
		
		protected void endConfigurationPhase() {
			phase = Phase.NORMAL;
		}
		protected void forceErrorPhase() {
			phase = Phase.ERROR;
		}
		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}

	}
	
	private class ElectronicScaleStub implements ElectronicScaleObserver {
		
		protected Phase phase = Phase.NORMAL;
		
		protected void endConfigurationPhase() {
			phase = Phase.NORMAL;
		}
		protected void forceErrorPhase() {
			phase = Phase.ERROR;
		}
		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void weightChanged(ElectronicScale scale, double weightInGrams) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void overload(ElectronicScale scale) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void outOfOverload(ElectronicScale scale) {
			// TODO Auto-generated method stub
			
		}

	}


	@Before
	public void setup() {
		
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
			SelfCheckoutStation giota = new SelfCheckoutStation(currency, banknoteDenoms, coinDenoms, defaultMaxScaleWeight, defaultSensitivity);
			
		new TestHardware();
		
		as = new AttendantStation();
		
		ts = new TouchScreenStub();
		tss = new TouchScreenSoftware(as);
		touchscreen =  new TouchScreen();
		ts.phase = Phase.NORMAL;
		ts.endConfigurationPhase();
		touchscreen.attach(ts);
		
		
		es = new ElectronicScaleStub();
		ess = new ElectronicScale(1, 1);
		
		es.endConfigurationPhase();
		ess.attach(es);
	}

	@Test
	public void testDisabled() {
		touchscreen.disable();
		Assert.assertTrue("TouchScreenSoftware.isDisabled should be true", tss.getIsDisabled() == true);
	}

	@Test
	public void testEnabled() {
		touchscreen.enable();
		Assert.assertTrue("TouchScreenSoftware.isDisabled should be false", tss.getIsDisabled() == false);
	}

	//
	@Test
	public void testAddBagsWhileEnabled() {
		try {
			tss.addBags(ess);
		} catch (DisabledException e) {
			System.err.println("Touchscreen should be enabled");
		}
		Assert.assertTrue("Scale should be enabled", ess.isDisabled() == false);
	}

	@Test
	public void testAddBagsWhileDisabled() {
		touchscreen.disable();
		try {
			tss.addBags(ess);
		} catch (DisabledException e) {
			return;
		}
		System.err.println("Touchscreen should be disabled");
	}

}
