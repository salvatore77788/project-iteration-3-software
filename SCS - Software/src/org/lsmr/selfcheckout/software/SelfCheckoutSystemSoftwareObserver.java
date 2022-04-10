package org.lsmr.selfcheckout.software;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;

/**
 * Observes events emanating from a SCS software.
 */
public interface SelfCheckoutSystemSoftwareObserver extends AbstractDeviceObserver {
	
	void amountPaid(SelfCheckoutStationSoftware software, BigDecimal amount);
	
	void membershipCardScanned(SelfCheckoutStationSoftware software, String memberCN);
}
