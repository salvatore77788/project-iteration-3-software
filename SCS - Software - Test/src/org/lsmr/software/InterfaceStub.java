package org.lsmr.software;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

public class InterfaceStub implements ElectronicScaleObserver
    {

    private boolean overloadCalled = false;
    private boolean outOfOverload  = false;
    private boolean weightChanged  = false;
    
	/*
	 * protected enum Phase { CONFIGURATION, NORMAL, ERROR }
	 * 
	 * public Phase phase = Phase.NORMAL;
	 */
	
    public InterfaceStub(){}

       
    @Override
    public void enabled (AbstractDevice<? extends AbstractDeviceObserver> device)
        {       
        }

    @Override
    public void disabled (AbstractDevice<? extends AbstractDeviceObserver> device)
        {
        }

    @Override
    public void weightChanged (ElectronicScale scale, double weightInGrams)
        {
        weightChanged = true;
        }

    @Override
    public void overload (ElectronicScale scale)
        {
        overloadCalled = true;
        }

    @Override
    public void outOfOverload (ElectronicScale scale)
        {
        outOfOverload = true;
        }
    
    public boolean getOverload()
        {
        return overloadCalled;
        }
    
    public boolean getOutOfOverload()
        {
        return outOfOverload;
        }    
    
    public boolean getWeightChanged()
        {
        return weightChanged;
        }    
    }