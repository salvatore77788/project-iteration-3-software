package org.lsmr.software;

	import java.util.ArrayList;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;


/*
 * @Author
 * Sean Stacey and Mitchell Wilson
 * 
 * 100% coverage is not possible because of the getAttendantPermission() method.
 */

public class CustomerAddsBags implements ElectronicScaleObserver {
	public SelfCheckoutStation station;
	public boolean placed = false;
	public boolean commenceScanningItems = false;
	public ArrayList<Item> bagList = new ArrayList<>();
	
	/**
	 * CustomerAddsBags constructor.
	 * 
	 * @param checkout
	 * 				Constructor accepts SelfCheckoutStation argument.
	 */
	public CustomerAddsBags(SelfCheckoutStation checkout) {
		station = checkout;
		station.baggingArea.attach(this);
	}
	
	/**
	 * Allows the customer to place their own (likely reusable) bag in the bagging area.
	 * 
	 * @param bag
	 * 				Customer adds bag of type Item.
	 * 				
	 * @throws OverloadException
	 */
	public void customerAddsTheirOwnBag(Item bag) throws OverloadException {
		if((station.baggingArea.getWeightLimit() > station.baggingArea.getCurrentWeight() + bag.getWeight()) && (getAttendantPermission(bag))) {
			station.baggingArea.add(bag);
			placed = true;
			bagList.add(bag);
		}
		else {
			placed = false;
			overload(station.baggingArea);
			throw new OverloadException();
		}
	}
	
	/**
	 * Customer removes their bag from the bagging area. This requires intervention by a monitoring attendant.
	 * 
	 * @param bag
	 * 				Customer removes this bag of type Item.
	 * @return 
	 * 			True: If this bag is found in bagging area, it is removed and the method returns True.
	 * 			False: If this bag cannot be found in the bagging area, the method returns False.
	 */
	public boolean removeBag(Item bag) {
		boolean bagRemoved = false;
		
		if((bagList.contains(bag) == true) && (getAttendantPermission(bag))) {
			bagList.remove(bag);
			station.baggingArea.remove(bag);
			System.out.printf("%s was successfully removed from the bagging area.\n", bag.toString());
			bagRemoved = true;
			return bagRemoved;
		}
		
		System.out.printf("%s was not found in the bagging area.\n", bag.toString());
		return bagRemoved;
	}
	
	/**
	 * A monitoring attendant confirms that the item added to the bagging area is a bag.
	 * 
	 * @param bag
	 * @return
	 * 			True: If the Item is a bag, the method returns True.
	 * 			False: If the Item is not a bag, the method returns False.
	 * 	
	 */
	public boolean getAttendantPermission(Item bag) {
		return true;
		/*
		 * I don't know how to distinguish between a bag and a not-bag, so there is no way this can be false.
		 * In future iterations, we may be able to add a class Bag which extends Item, but then all of these methods would accept Bag objects,
		 * and getAttendantPermission(Bag bag) would be redundant. 
		 */
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

	public void placeStoreBag(Item normalWeightCustomerBag) {
		// TODO Auto-generated method stub
		
	}
}
