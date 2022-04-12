package org.lsmr.selfcheckout.software.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSetup;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

public class SelfCheckoutSystemSoftwareGUI extends JFrame {

	private JPanel contentPane;
	private JTextField welcomeText;

	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//SelfCheckoutStation testStation = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
		//SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware(testStation);
		//DemoGUI demo = new DemoGUI(software);
		//demo.setVisible(true);
		new AttendantGui(true);
		
		/*
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
		*/
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
		welcomeText.setEditable(false);
		welcomeText.setBounds(42, 28, 867, 170);
		contentPane.add(welcomeText);
		welcomeText.setColumns(10);
		
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				software.startScanGUI();
				//ItemInfo item = new ItemInfo(new BigDecimal("2.00"), 1.0, "testRemove1");
				//software.scanAndBag.addToList(item);
				software.scanAndBag.startGUI();
				System.out.println(software.scanAndBag.getCurrentWeight());
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
