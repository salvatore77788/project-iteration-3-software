package org.lsmr.selfcheckout.software;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.lsmr.selfcheckout.products.PLUCodedProduct;


public class ElectronicScaleSoftware implements ElectronicScaleObserver{
	private double currentWeight;
	private double weightAtLastEvent;
	private boolean isDisabled;
	private SelfCheckoutStation scs;
	private boolean attendantRemoved;
	private Data d;


	public ElectronicScaleSoftware(SelfCheckoutStation aSCS) {
		this.scs = aSCS;
		this.currentWeight = 0;
		this.weightAtLastEvent = 0;
		this.isDisabled = false;
		d = Data.getInstance();
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
		{
			this.currentWeight = weightInGrams;
			if (d.get_plu_product_scanned())
			{
				ArrayList <PLUCodedProduct> plu_products = d.getPluScanned();
				PLUCodedProduct plu_item = plu_products.get(plu_products.size() - 1);
				ArrayList <BigDecimal> plu_prices = d.getPluPrice();
				BigDecimal current_price = plu_item.getPrice();
				//potential problem
				BigDecimal price_for_weight = current_price.divide(new BigDecimal (1000), 4, RoundingMode.HALF_UP);

				plu_prices.add(price_for_weight);

				d.set_plu_product_scanned(false);
			}
		}
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
	
	public boolean getIsDisabled() {
		return isDisabled;
	}
}