package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;

public class AttendantStation implements ReceiptPrinterObserver {

	public AttendantStation() {
		// TODO Auto-generated constructor stub
	}

	public void BagsAdded(ElectronicScale scale) {
		scale.enable();
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
	public void outOfPaper(ReceiptPrinter printer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOfInk(ReceiptPrinter printer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paperAdded(ReceiptPrinter printer) {
	
	}

	@Override
	public void inkAdded(ReceiptPrinter printer) {
		
		
	}

}
