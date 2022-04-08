package org.lsmr.selfcheckout.software.gui;

import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

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
	
	
	
	public void windowShows() {
		theFrame = theStation.screen.getFrame();
		thePanel = new JPanel();
		thePanel.setLayout(new GridLayout(100,50));
		
		createKeypad();
		
		
		
		
	}
	
	public void createKeypad() {
		
	}
	
	
	

	
	// for testing the GUI,
	
	
	public static void main(String[] args) {
		
		// SelfCheckoutStation requires 5 attributes.
		SelfCheckoutStation testStation = 
				new SelfCheckoutStation(
				Currency.getInstance(Locale.CANADA),
				new int[]{5, 10, 20, 50, 100},
				new BigDecimal[]{
		        		new BigDecimal("0.05"), 
		        		new BigDecimal("0.10"),
		        		new BigDecimal("0.25"),
		        		new BigDecimal("1.00"),
		        		new BigDecimal("2.00"),
		        		},
				5000,2);

		
		EnterMemberCard testEMC = new EnterMemberCard(testStation);
		
		testEMC.windowShows();
		
	}
	
	

}
