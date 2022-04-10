package org.lsmr.selfcheckout.software.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Payment extends JFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Payment frame = new Payment();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Payment() {
		setTitle("Checkout Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		frame = new JFrame();
		frame.setLayout(new BorderLayout(0, 0));
		frame.setBounds(100, 100, 450, 300); 					//setting Bounds
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	//when page is closed program shuts down
		frame.setResizable(false); 								//It is not resizable 
		frame.setTitle(" Login Screen "); 						//Title of the screen
		setContentPane(frame);
	}

}
