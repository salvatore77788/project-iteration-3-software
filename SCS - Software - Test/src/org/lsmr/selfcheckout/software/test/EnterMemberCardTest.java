package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.TouchScreen;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.TouchScreenObserver;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;
import org.lsmr.selfcheckout.software.gui.EnterMemberCard;

public class EnterMemberCardTest {
    private TouchScreen screen;
    JFrame frame;
    private volatile int found;
    //private 
    private SelfCheckoutStationSoftware theSoftware;

    @Before
    public void setup() throws Exception {
    	
    	
    	TestHardware hardware = new TestHardware();
    	theSoftware = new SelfCheckoutStationSoftware(hardware.scs);
    	
        screen = theSoftware.scs.screen;//new TouchScreen();
        frame = screen.getFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
        found = 0;
        
    }

        
    // Testing that "Enter member card number"
    // got pressed.
    @Test
    public void testMainMenuScreen() {
    
    	JFrame stationFrame = theSoftware.scs.screen.getFrame();
    	
    	stationFrame.setSize(700,700);
		stationFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
		stationFrame.setVisible(true);
		// Gives us full control of where we want to place our GUI
		// components.
		stationFrame.setLayout(null);
		
		// Button for membershipnumber.
		JButton enterMemberNumberButton = new JButton("Enter member card number");
		enterMemberNumberButton.setSize(200,75);
		stationFrame.add(enterMemberNumberButton);
		enterMemberNumberButton.setLocation(450,550);
		
		enterMemberNumberButton.addActionListener(
				new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// In essence the number of actions that occurred.
						found++;
						
					}
					
				});
    	
		
		enterMemberNumberButton.doClick();
		assertEquals(1,found);
		
    }
    
}
