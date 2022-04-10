package org.lsmr.selfcheckout.software.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.ItemInfo;
import org.lsmr.selfcheckout.software.ScanAndBag;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SelfCheckoutSystemSoftwareGUI extends JFrame {

	private JPanel contentPane;
	private JTextField welcomeText;

	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
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
		SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testStation);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelfCheckoutSystemSoftwareGUI frame = new SelfCheckoutSystemSoftwareGUI(testStation, software);
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
	public SelfCheckoutSystemSoftwareGUI(SelfCheckoutStation scs, SelfCheckoutStationSoftware software) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1023, 632);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		welcomeText = new JTextField();
		welcomeText.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeText.setFont(new Font("Tahoma", Font.BOLD, 40));
		welcomeText.setText("Welcome!");
		welcomeText.setBounds(42, 28, 867, 170);
		contentPane.add(welcomeText);
		welcomeText.setColumns(10);
		
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				software.startScanGUI();
				ItemInfo item = new ItemInfo(new BigDecimal("2.00"), 1.0, "testRemove1");
				software.scanAndBag.addToList(item);
				dispose();
				//ScanAndBag scan = new ScanAndBag(scs, software.db, software);
				//ItemInfo item = new ItemInfo(new BigDecimal("2.00"), 1.0, "testRemove1");
				//scan.addToList(item);
				//dispose();
			}
		});
		startButton.setFont(new Font("Tahoma", Font.PLAIN, 40));
		startButton.setBounds(291, 232, 403, 200);
		contentPane.add(startButton);
		

	}

}
