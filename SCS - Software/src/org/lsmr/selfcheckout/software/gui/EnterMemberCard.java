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

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

public class EnterMemberCard implements ActionListener{
	
	public SelfCheckoutStationSoftware theSoftware;
	// The keyPadFrame provided by screen in scs.
	JFrame stationFrame;
	// needs to be able to "dispose" of frame from the other class.
	static JFrame keyPadFrame;
	JButton enterMemberNumberButton;
	public String memberNumberProvided;

	public EnterMemberCard(SelfCheckoutStationSoftware aSCSsoftware) {
		
		this.theSoftware = aSCSsoftware;
		theSoftware.scs.screen.enable();
		
		//theStation.screen.
		
	}
	
	public void mainMenu() {
		//System.out.println("Entered windowShows");
		
		stationFrame = theSoftware.scs.screen.getFrame();
		stationFrame.setSize(700,700);
		stationFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
		stationFrame.setVisible(true);
		// Gives us full control of where we want to place our GUI
		// components.
		stationFrame.setLayout(null);
		
		// Button for membershipnumber.
		enterMemberNumberButton = new JButton("Enter member card number");
		enterMemberNumberButton.setSize(200,75);
		stationFrame.add(enterMemberNumberButton);
		enterMemberNumberButton.setLocation(450,550);
		
		enterMemberNumberButton.addActionListener(this);

	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == enterMemberNumberButton) {
			
			keyPadFrame = new JFrame("Keypad");
			keyPadFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			// Creates Pane with buttons along with its own Action Listener.
			VirtualKeypad theKeypad = new VirtualKeypad();
			keyPadFrame.getContentPane().add(theKeypad);
			
			memberNumberProvided = theKeypad.getMemberNumber();
			
			if(theSoftware.membersRecord.authenticateByNumber(memberNumberProvided) == true) {
				theSoftware.setMemberCardNumber(memberNumberProvided);
			}
			 
			keyPadFrame.pack();
			keyPadFrame.setVisible(true);
		}
		
	}
	
	
	public String getMemberNumberProvided() {
		return memberNumberProvided;
	}
	
	
	// so far not used.
	public void showKeypad() {
		VirtualKeypad theKeypad = new VirtualKeypad();
		theKeypad.createAndShowGUI();
	}
	
	
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
		EnterMemberCard aGUI = new EnterMemberCard(theSoftware);
		aGUI.mainMenu();
	}

	
	
	
	
	
	
}