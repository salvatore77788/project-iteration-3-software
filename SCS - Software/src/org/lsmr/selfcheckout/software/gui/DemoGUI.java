package org.lsmr.selfcheckout.software.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.software.ItemInfo;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class DemoGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtControllerForTesting;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}

	/**
	 * Create the frame.
	 */
	public DemoGUI(SelfCheckoutStationSoftware scs) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1347, 625);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton scanItemButton = new JButton("Scan Item");
		scanItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ItemInfo item = new ItemInfo(new BigDecimal("1.58"), 1200, "Dairy Land 2% Milk");
				scs.scanAndBag.addToList(item);
			}
		});
		scanItemButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scanItemButton.setBounds(42, 39, 194, 97);
		contentPane.add(scanItemButton);
		
		
		JButton scanItem2Button = new JButton("Scan Item 2");
		scanItem2Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scanItem2Button.setBounds(42, 194, 194, 97);
		scanItem2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ItemInfo item = new ItemInfo(new BigDecimal("60.00"), 2000, "Guitar");
				scs.scanAndBag.addToList(item);
			}
		});
		contentPane.add(scanItem2Button);
		
		JButton overweightButton = new JButton("Overweight");
		overweightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scs.scanAndBag.scanGUI.wrongWeightOn();
			}
		});
		overweightButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		overweightButton.setBounds(368, 194, 194, 97);
		contentPane.add(overweightButton);
		
		JButton normalWeightButton = new JButton("Normal Weight");
		normalWeightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scs.scanAndBag.scanGUI.wrongWeightOff();
			}
		});
		normalWeightButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		normalWeightButton.setBounds(368, 39, 194, 97);
		contentPane.add(normalWeightButton);
		
		txtControllerForTesting = new JTextField();
		txtControllerForTesting.setEditable(false);
		txtControllerForTesting.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtControllerForTesting.setText("Controller for Testing and Demo ");
		txtControllerForTesting.setBounds(38, 370, 405, 126);
		contentPane.add(txtControllerForTesting);
		txtControllerForTesting.setColumns(10);
		

	}
}
