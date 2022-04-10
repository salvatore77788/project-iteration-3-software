package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.assertEquals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.Document;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.TouchScreen;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.TouchScreenObserver;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;
import org.lsmr.selfcheckout.software.gui.EnterMemberCard;
import org.lsmr.selfcheckout.software.gui.VirtualKeypad;

public class EnterMemberCardTest {
	
	// For Keypad test.
	int sevenNum = 0;
	int eightNum = 0;
	int nineNum = 0;
	
	int fourNum = 0;
	int fiveNum = 0;
	int sixNum = 0;
	
	int oneNum = 0;
	int twoNum = 0;
	int threeNum = 0;
	
	int zeroNum = 0;
	
	int enterNum = 0;
	int returnNum = 0;
	
	
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
    // The press is done automatically.
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
    
    // Testing that no click is registered.
    @Test
    public void testMainMenuScreen_NoClick() {
    
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
    	
		
		//enterMemberNumberButton.doClick();
		assertEquals(0,found);
		
    }
    
    
    
    
    
    
    // Basically has the code of the VirtualKeypad class in Software.
    // it then tests that 7, 4, 1 and 5 got pressed.
    @Test
    public void testingKeypad() {
    	
    	Document display = null;
    	
    	
    	
    	
    	JFrame frame = new JFrame("Keypad");

	      // We want the application to exit when the window is closed

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	     
	    BorderLayout theLayout = new BorderLayout();
	      
	      
	      
	      JPanel buttons = new JPanel(new GridLayout(4, 3,5,5));
	
	      
	      JButton seven = new JButton("7");
	      seven.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent e) {
	            	 sevenNum++;
	             }
	         });
	      buttons.add(seven);
	      
	      
	      JButton eight = new JButton("8");
	      eight.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent e) {
	            	 eightNum++;
	             }
	         });
	      buttons.add(eight);
	      
	      JButton nine = new JButton("9");
	      nine.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent e) {
	            	 nineNum++;
	             }
	         });
	      buttons.add(nine);
	      
	      
	      JButton four = new JButton("4");
	      four.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent e) {
	            	 fourNum++;
	             }
	         });
	      buttons.add(four);
	      
	      
	      JButton five = new JButton("5");
	      five.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent e) {
	            	 fiveNum++;
	             }
	         });
	      buttons.add(five);
	      
	      JButton six = new JButton("6");
	      six.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent e) {
	            	 sixNum++;
	             }
	         });
	      buttons.add(six);
	      
	      
	      JButton one = new JButton("1");
	      one.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent e) {
	            	 oneNum++;
	             }
	         });
	      buttons.add(one);
	      
	      JButton two = new JButton("2");
	      two.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent e) {
	            	 twoNum++;
	             }
	         });
	      buttons.add(two);
	      
	      JButton three = new JButton("3");
	      three.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent e) {
	            	 threeNum++;
	             }
	         });
	      buttons.add(three);
	      
	      
	      JButton zero = new JButton("0");
	      zero.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent e) {
	            	 zeroNum++;
	             }
	         });
	      buttons.add(zero);
	      
	      
	      
	    
	
	     JButton enterButton = new JButton("Enter");
		 enterButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 enterNum++;
	         }
	     });
		 buttons.add(enterButton);
		 
		 JButton returnMenuButton = new JButton("Return to Menu");
		 returnMenuButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 returnNum++;
	         }
	     });
		 buttons.add(returnMenuButton);
	 
	 
	 

      // Create a text field to display the numbers entered

      JTextField displayField = new JTextField();
      // what does this do?
      display = displayField.getDocument();
      
      //add(theLayout.CENTER, buttons);
      //add(theLayout.SOUTH, displayField);
   
	      
	      
	      
	      
	      // Add the key pad to window

	      //frame.getContentPane().add(new VirtualKeypad());

	      // Display the window.

	  frame.pack();
	  frame.setVisible(true);
    
	  // simulating it presses 7, 4, 1 and 5.
	  seven.doClick();
	  four.doClick();
	  one.doClick();
	  five.doClick();
	  
	  
	  assertEquals(1,sevenNum);
	  assertEquals(1,fourNum);
	  assertEquals(1,oneNum);
	  assertEquals(1,fiveNum);
	  
	  
	  
    	
    } // End of test.


  
    
}
