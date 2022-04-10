package org.lsmr.selfcheckout.software.test;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.software.AttendantStation;
import org.lsmr.selfcheckout.software.ElectronicScaleSoftware;
import org.lsmr.selfcheckout.software.TouchScreenSoftware;

import org.junit.Assert;

public class TouchScreenTest {

	// Has to be fixed due to endConfigurationPhase() error

	private AttendantStation as;
	private TouchScreenSoftware tss;
	private ElectronicScale es;
	private TouchScreenStub ts;
	private ElectronicScaleSoftware escaleObserver;

	private TestHardware testHardware;

	private class TouchScreenStub extends AbstractDevice<TouchScreenSoftware> {

	}

	@Before
	public void setup() {

		testHardware = new TestHardware();

		as = new AttendantStation();
		tss = new TouchScreenSoftware(as);
		es = new ElectronicScale(1, 1);
		// es.endConfigurationPhase();
		ts = new TouchScreenStub();
		// ts.endConfigurationPhase();
		ts.attach(tss);
	}

	@Test
	public void testDisabled() {
		ts.disable();
		Assert.assertTrue("TouchScreenSoftware.isDisabled should be true", tss.getIsDisabled() == true);
	}

	@Test
	public void testEnabled() {
		ts.enable();
		Assert.assertTrue("TouchScreenSoftware.isDisabled should be false", tss.getIsDisabled() == false);
	}

	@Test
	public void testAddBagsWhileEnabled() {
		try {
			tss.addBags(es);
		} catch (DisabledException e) {
			System.err.println("Touchscreen should be enabled");
		}
		Assert.assertTrue("Scale should be enabled", es.isDisabled() == false);
	}

	@Test
	public void testAddBagsWhileDisabled() {
		ts.disable();
		try {
			tss.addBags(es);
		} catch (DisabledException e) {
			return;
		}
		System.err.println("Touchscreen should be disabled");
	}
}
