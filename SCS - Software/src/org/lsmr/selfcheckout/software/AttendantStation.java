package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.ElectronicScale;

public class AttendantStation {

	public AttendantStation() {
		// TODO Auto-generated constructor stub
	}

	public void BagsAdded(ElectronicScale scale) {
		scale.enable();
	}
}
