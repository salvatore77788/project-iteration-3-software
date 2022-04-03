package org.lsmr.software;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Item;
import org.lsmr.software.ShoppingCart;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

import org.lsmr.selfcheckout.devices.OverloadException;

import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import java.util.ArrayList;
import java.util.Map;

public class PlaceItemInBaggingArea implements ElectronicScaleObserver{
	public SelfCheckoutStation station;
	public ArrayList<Item> items = new ArrayList<>();
	public boolean isAdded = false;
	public boolean isRemoved = false;
	public boolean isOverloaded = false;
	private Data   data = Data.getInstance();
	
	private double expectedWeight;
	
	//Constructor 
	public PlaceItemInBaggingArea(SelfCheckoutStation sct) {
		station = sct;
		sct.baggingArea.attach(eso);
	}
	
	/**
	 * An event announcing that n item has been added to the bagging area.
	 * 
	 * @param anItem
	 * 			An item that has yet to be added to the bagging area.
	 */
//	public void addItem(Item anItem) {
//		station.baggingArea.add(anItem);
//		
//		if (isAdded || !isOverloaded) {
//			items.add(anItem);
//			isAdded = false;
//			isOverloaded= false;
//		}
//	}
	
	/**
	 * An event announcing that an item has been removed from the bagging area
	 * 
	 * @param anItem
	 * 			An item already located in the bagging area.
	 */
//	public void removeItem(Item anItem) {
//		station.baggingArea.remove(anItem);
//		if (isRemoved || isAdded) {
//			int index = items.indexOf(anItem);
//			items.remove(index);
//			isRemoved = false;
//			isAdded = false;
//		}
//	}

	public ElectronicScaleObserver eso = new ElectronicScaleObserver() {
		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		data.setIsDisabled(false);
		
		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		data.setIsDisabled(true);
		
		}

		@Override
		public void weightChanged(ElectronicScale scale, double weightInGrams) {
		    double expectedWeight = 0;
		    ShoppingCart s    = ShoppingCart.getInstance();
		    
	        ArrayList <ShoppingCart.ShoppingCartEntry> items = s.getEntries();
	        
	        for (int i = 0; i < items.size(); i ++)
	            {
	            expectedWeight += items.get(i).getWeight().doubleValue();
	            }
			
		     if(expectedWeight - weightInGrams > scale.getSensitivity()) {
	            System.out.println("Please return item to the bagging area.");
	            data.setIsDisabled(true);
	        } else if(weightInGrams - expectedWeight > scale.getSensitivity()) {
	            System.out.println("Please remove unscanned item from the bagging area.");
	            data.setIsDisabled(true);
	        } else {
	         isAdded = true;
	         data.setItemAdded(true);
	         data.setIsDisabled(false);
	         data.cancelBaggingTimerTask();
	        }
		}

		@Override
		public void overload(ElectronicScale scale) {
		Data            data               = Data.getInstance();
        isOverloaded = true;  
        data.setIsDisabled(true);
		}

		@Override
		public void outOfOverload(ElectronicScale scale) {
		Data            data               = Data.getInstance();
        data.setIsDisabled(false);
        isRemoved = true;
		
		}
	};

    @Override
    public void enabled (AbstractDevice<? extends AbstractDeviceObserver> device)
        {
        data.setIsDisabled(false);
        
        }

    @Override
    public void disabled (AbstractDevice<? extends AbstractDeviceObserver> device)
        {
        data.setIsDisabled(true);
        }

    @Override
    public void weightChanged (ElectronicScale scale, double weightInGrams)
        {
        expectedWeight = 0;
        ShoppingCart    s                  = ShoppingCart.getInstance();
        ProductDatabase productDatabase    = ProductDatabase.getInstance();
        Data            data               = Data.getInstance();
        
        ArrayList <ShoppingCart.ShoppingCartEntry> items = s.getEntries();
        
        System.out.println("weightInGrams: " + weightInGrams);
        
        
        for (int i = 0; i < items.size(); i ++)
            {
            expectedWeight += items.get(i).getWeight().doubleValue();
            }
        
         if (expectedWeight - weightInGrams > scale.getSensitivity())
             {
             System.out.println("Please return item to the bagging area.");
             data.setIsDisabled(true);
             }
         else if (weightInGrams - expectedWeight > scale.getSensitivity())
             {
             System.out.println("Please remove unscanned item from the bagging area.");
             data.setIsDisabled(true);
             }
         else
             {
             isAdded = true;
             data.setItemAdded(true);
             data.setIsDisabled(false);
             data.cancelBaggingTimerTask();
             }
        
        }

    @Override
    public void overload (ElectronicScale scale)
        {
        Data            data               = Data.getInstance();
        isOverloaded = true;  
        data.setIsDisabled(true);
        }

    @Override
    public void outOfOverload (ElectronicScale scale)
        {
        Data            data               = Data.getInstance();
        data.setIsDisabled(false);
        isRemoved = true;
        }
    
    public double getExpectedWeight()
    {
    return expectedWeight;
    }


}
