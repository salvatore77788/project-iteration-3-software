package org.lsmr.selfcheckout.software;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;


public class ElectronicScaleSoftware implements ElectronicScaleObserver{
	private double currentWeight;
	private double weightAtLastEvent;
	private boolean isDisabled;
	private SelfCheckoutStation scs;
	private boolean attendantRemoved;

	
	public ElectronicScaleSoftware(SelfCheckoutStation aSCS) {
		this.scs = aSCS;
		this.currentWeight = 0;
		this.weightAtLastEvent = 0;
		this.isDisabled = false;
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		this.isDisabled = false;
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		this.isDisabled = true;
	}

	//We check the weight of the item compared to the db in barcodeScanned
	@Override
	public void weightChanged(ElectronicScale scale, double weightInGrams) {
		if(this.isDisabled == true)
			return;
	
		this.weightAtLastEvent = this.currentWeight;
		if (weightInGrams >= 0)
			this.currentWeight = weightInGrams;
		else if(attendantRemoved) { 
			// If attendant removed item, weight change will be negative
			
			// Update weight after product removal 
			this.weightAtLastEvent = weightInGrams;
			this.currentWeight = weightInGrams;

			// Reset boolean to false
			attendantRemoved = false; 
		} else 
			System.err.println("weightChanged in ElectronicScaleSoftware is negative. weightInGrams: " + weightInGrams);
	}

	@Override
	public void overload(ElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}
	
	public double getCurrentWeight() {
		return this.currentWeight;
	}
	
	public double getWeightAtLastEvent() {
		return this.weightAtLastEvent;
	}

	public void setAttendantRemovedItem() {
		this.attendantRemoved = true;
	}
}
