/**
 * An observer for the self checkout system's touch screen.
 * As the touch screen does not exist yet (will be implemented in the GUI),
 * users interact with the methods in this class through a command-line interface.
 *
 * Questions:
 * 		How should addBags communicate with the electronic scale?
 */

package org.lsmr.selfcheckout.software;

import java.util.Scanner;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.TouchScreenObserver;

public class TouchScreenSoftware implements TouchScreenObserver {
	private boolean isDisabled;
	private AttendantStation as;
	public SelfCheckoutStation theStation;
	
	
	public TouchScreenSoftware(SelfCheckoutStation aSCS) {
		this.theStation = aSCS;
	}
	
	

	public TouchScreenSoftware(AttendantStation as) {
		this.isDisabled = false;
		this.as = as;
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		this.isDisabled = false;

	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		this.isDisabled = true;

	}
	
	public void addBags(ElectronicScale scale) throws DisabledException {
		if (this.isDisabled == true) {
			throw new DisabledException();
		}
		scale.disable();
		System.out.println("Add your bags now!");
		notifyBagsAdded(scale);
	}
	
	public void notifyBagsAdded(ElectronicScale scale) {
		this.as.BagsAdded(scale);
	}
	
	public boolean getIsDisabled() {
		return this.isDisabled;
	}

}
