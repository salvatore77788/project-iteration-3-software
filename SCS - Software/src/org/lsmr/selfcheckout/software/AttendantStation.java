package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;

public class AttendantStation {

	public AttendantStation() {
		// TODO Auto-generated constructor stub
	}

	public void BagsAdded(ElectronicScale scale) {
		scale.enable();
	}
}
