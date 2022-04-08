package org.lsmr.selfcheckout.software.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

public class EnterMemberCard extends JPanel implements ActionListener  {
	
	public SelfCheckoutStation theStation;
	// I'm going to need some sort of touchscreen observer.
	// All the touchscreen class is doing is providing a JFrame.
	JFrame stationFrame;
	JFrame keypadFrame;
	JPanel thePanel;
	// Some navigation buttons
	
	JButton enterNumberButton;
	// this will actually be created in keypad.
	JButton returntoMain;
	


	public EnterMemberCard(SelfCheckoutStation aSCS) {
		
		this.theStation = aSCS;
		theStation.screen.enable();
		
		//theStation.screen.
		
	}
	
	public void createButtons() {
		enterNumberButton = new JButton("Enter member card number");
		enterNumberButton.setSize(200,100);
		stationFrame.add(enterNumberButton);
		enterNumberButton.setLocation(450,550);
		
		
		returntoMain = new JButton("Return to Main Screen");
	}
	
	
	
	public void buttonInteraction() {
		// Seems to work when I take out the ActionEvent type out.
		enterNumberButton.addActionListener((ActionEvent e) ->{
					// when I press the EnterNumber Button
					// I want the main menu keypadFrame to dissappear
					stationFrame.setVisible(false);
					keypadFrame.setVisible(true);
					});
	
		 
	}
	
	
	
	public void windowShows() {
		System.out.println("Entered windowShows");
		
		stationFrame = theStation.screen.getFrame();
		stationFrame.setSize(700,700);
		stationFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
		stationFrame.setVisible(true);
		// Gives us full control of where we want to place our GUI
		// components.
		stationFrame.setLayout(null);
		
		// Basically what's inside createAndShowGUI.
		// Keypad keypadFrame.
		keypadFrame = new JFrame("Keypad");   
	      // We want the application to exit when the window is closed
	    keypadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	      // Add the key pad to window
	    // This actually add the buttons to this keypadFrame
	    // It has its own panel.
	    
	    keypadFrame.getContentPane().add(new VirtualKeypad());
	 
	    
	      // Display the window.
	    keypadFrame.pack();
	    keypadFrame.setVisible(true);
	
		
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	// for testing the GUI.
	public static void main(String[] args) throws Exception {
		
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

		// Contains observers within.
		SelfCheckoutStationSoftware theSoftware = new SelfCheckoutStationSoftware(testStation);
		
		// Object of this class.
		EnterMemberCard testEMC = new EnterMemberCard(theSoftware.scs);
		
		testEMC.windowShows();
		testEMC.createButtons();
		
	}

	
	
	

}
