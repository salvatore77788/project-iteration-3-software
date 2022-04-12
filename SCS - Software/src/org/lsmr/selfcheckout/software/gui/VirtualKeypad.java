package org.lsmr.selfcheckout.software.gui;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
// Modified code from the following source: https://learn-java-by-example.com/java/calculator-keypad/


public class VirtualKeypad extends JPanel implements ActionListener {


	private Document display = null;
	JButton enterButton;
	JButton returnMenuButton;
	JTextField displayField;
	public JFrame keyPadFrame = new JFrame("Keypad");
	
	private String memberNumberProvided;
	
	

	public VirtualKeypad(JFrame aKeyPadFrame) {
		
		
		  
		 	
	      super(new BorderLayout());

	      keyPadFrame = aKeyPadFrame;
	      
	      
	      // Create a panel for the buttons
	      // We'll use a GridLayout to display the buttons in a grid
	      JPanel buttons = new JPanel(new GridLayout(4, 3,5,5));

	      // There are 10 buttons in total.
	      for (int i=7; i < 10; i++) {
	         JButton button = new JButton(Integer.toString(i));

	         // By adding an ActionListener to our button we
	         // can get notified when the button has been pressed 

	         button.addActionListener(this);
	         buttons.add(button);
	      }

	      for (int i=4; i < 7; i++) {
		         JButton button = new JButton(Integer.toString(i));

		         // By adding an ActionListener to our button we
		         // can get notified when the button has been pressed 

		         button.addActionListener(this);
		         buttons.add(button);
		      }

	      for (int i=1; i < 4 ; i++) {
		         JButton button = new JButton(Integer.toString(i));

		         // By adding an ActionListener to our button we
		         // can get notified when the button has been pressed 

		         button.addActionListener(this);
		         buttons.add(button);
		      }



			  // To add the 0 at the end. 
	     JButton button = new JButton(Integer.toString(0)); 
	     button.addActionListener(this);
		 buttons.add(button);

		 enterButton = new JButton("Enter");
		 enterButton.addActionListener(this);
		 buttons.add(enterButton);
		 
		 returnMenuButton = new JButton("Return to Menu");
		 returnMenuButton.addActionListener(this);
		 buttons.add(returnMenuButton);
		 
		 
		 

	      // Create a text field to display the numbers entered

	      JTextField displayField = new JTextField();
	      // what does this do?
	      display = displayField.getDocument();
	      add(BorderLayout.CENTER, buttons);
	      add(BorderLayout.SOUTH, displayField);
	   }
	
	
	   public String getMemberNumber() {
		   return memberNumberProvided;
	   }

	   /**
	    * Create and setup the main window
	    */

	   /*
	   public void createAndShowGUI() {
	      //frame = ;

	      // We want the application to exit when the window is closed

		   keyPadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      // Add the key pad to window

		   keyPadFrame.getContentPane().add(new VirtualKeypad());

	      // Display the window.

		   keyPadFrame.pack();
		   keyPadFrame.setVisible(true);
	   }

	   */
	   
	   /**
	    * Called when a button is pressed
	    */

	   @Override
	   public void actionPerformed(ActionEvent event) {

	      
		   if(event.getSource() == enterButton) {
			   //Document theDocument = displayField.getDocument();
			   
				try {
					memberNumberProvided = new String(display.getText(0,display.getLength()));
					System.out.println("Member number provided is:\n" + memberNumberProvided);
					// close Frame.
					keyPadFrame.dispose();
					
				} 
				catch (BadLocationException e) {
					// TODO Auto-generated catch block
					System.out.println("Trying to extract the number didn't work");
					e.printStackTrace();
				}
			   
		   }
		   
		   if(event.getSource() == returnMenuButton) {
			   keyPadFrame.dispose();
		   }
		   
		   
		   // Determine which key was pressed

	      String key = event.getActionCommand();

	      // Insert the pressed key into our text fields Document

	      try {
	         display.insertString(display.getLength(), key, null);
	      } catch (BadLocationException e) {
	         e.printStackTrace();
	      }
	   }

	   // main method.
	   /*
	   public void main(String[] args) {
	      SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	            createAndShowGUI();
	         }
	      });
	   }

*/



}