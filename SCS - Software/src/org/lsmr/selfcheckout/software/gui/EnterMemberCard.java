package org.lsmr.selfcheckout.software.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class EnterMemberCard {
	
	public SelfCheckoutStation theStation;
	// I'm going to need some sort of touchscreen observer.
	// All the touchscreen class is doing is providing a JFrame.
	JFrame theFrame;
	JPanel thePanel;

	
	
	public EnterMemberCard(SelfCheckoutStation aSCS) {
		
		this.theStation = aSCS;
		theStation.screen.enable();
		
		//theStation.screen.
		
	}
	
	
	
	public void thePopUp() {
		
		
		
	}

	public static void main(String[] args) {
		
	}
	
	

}
