package org.lsmr.selfcheckout.software.test;


import org.lsmr.selfcheckout.software.ElectronicScaleSoftware;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;

public class ElectronicScaleSoftwareTest 
{
	private ElectronicScaleSoftware ess = new ElectronicScaleSoftware();
	private ElectronicScale scale = new ElectronicScale(1000, 1);
	
	@Test
	public void testWeight()
	{
		assertEquals(ess.getCurrentWeight(), 0, 0);	
		assertEquals(ess.getWeightAtLastEvent(), 0, 0);
	}	

	
	@Test
	public void testConstructor() {
		ElectronicScaleSoftware test = new ElectronicScaleSoftware();
		Assert.assertTrue("Current weight should be zero at initialization.", test.getCurrentWeight() == 0);
		Assert.assertTrue("Weight at last event should be zero.", test.getWeightAtLastEvent() == 0);;
	}
	
	
	@Test
	public void testWeightChanged()
	{
		double newWeight = 2.0;
		ess.weightChanged(null, newWeight);
		//ess.disabled(null);
		Assert.assertTrue("The current weight in ElectronicScaleSoftware should be the new weight.", ess.getCurrentWeight() == newWeight);
		Assert.assertTrue("ElectronicScaleSoftware should update the previous weight", ess.getWeightAtLastEvent() != newWeight);
	}


    @Test
    public void testWeightChangedNegative(){
    	double possibleWeight = 1.5;
    	ess.weightChanged(null, 1.5);
        ess.weightChanged(null, -1.0);
        Assert.assertTrue("Weight should not have been changed", ess.getCurrentWeight() == possibleWeight);
    }

	@Test
	public void testEnabled() {
		int newWeight = 3;
		ess.enabled(scale);
		ess.weightChanged(null, 3);
		Assert.assertTrue("Should not change weight.", ess.getCurrentWeight() == newWeight);
	}
	
	@Test
	public void testDisabled()
	{
		ess.disabled(null);
		ess.weightChanged(null, 1);
		Assert.assertTrue("Should not change weight.", ess.getCurrentWeight() == ess.getWeightAtLastEvent());
		ess.enabled(null);
	}
	
	@Test
	public void testOutOfOverload()
	{
		ess.outOfOverload(scale);
	}
	
	@Test
	public void testOverload()
	{
		ess.overload(scale);
	}
}
