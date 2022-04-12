package org.lsmr.selfcheckout.software.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.software.AttendantStation;
import org.lsmr.selfcheckout.software.ItemInfo;


public class AttendantRemoveItemGUI extends JFrame {

	private JPanel contentPane;
	ArrayList<ItemInfo> items = new ArrayList<ItemInfo>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AttendantRemoveItemGUI frame = new AttendantRemoveItemGUI();
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
	public AttendantRemoveItemGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 0, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("2L Milk $5 1");
		testList.add("2L Milk $5 2");
		testList.add("2L Milk $5 3");
		testList.add("2L Milk $5 4");
		testList.add("2L Milk $5 5");
		testList.add("2L Milk $5 6");
		testList.add("2L Milk $5 7");
		testList.add("2L Milk $5 8");
		testList.add("2L Milk $5 9");
		testList.add("2L Milk $5 10");
		testList.add("test remve");
		testList.add("2L Milk $5 11");
		*/


		JButton stationOne = new JButton("Station 1");
		stationOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 1");
				StationItemGUI stationItemGUI = new StationItemGUI(AttendantStation.scanAndBagConnected.get(0).getItemsScanned(), 0);
				stationItemGUI.setVisible(true);
				
			}
		});
		stationOne.setFont(new Font("Tahoma", Font.PLAIN, 30));

		stationOne.setBounds(100, 50, 233, 64);
		contentPane.add(stationOne);
		
		JButton stationTwoButton = new JButton("Station 2");
		stationTwoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 2");
				StationItemGUI stationItemGUI = new StationItemGUI(AttendantStation.scanAndBagConnected.get(1).getItemsScanned(), 1);
				stationItemGUI.setVisible(true);
				
			}
		});
		stationTwoButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		stationTwoButton.setBounds(100, 200, 233, 64);
		contentPane.add(stationTwoButton);
		
		JButton stationThreeButton = new JButton("Station 3");
		stationThreeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 3");
				StationItemGUI stationItemGUI = new StationItemGUI(AttendantStation.scanAndBagConnected.get(2).getItemsScanned(), 2);
				stationItemGUI.setVisible(true);
			}
		});
		stationThreeButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		stationThreeButton.setBounds(100, 360, 233, 64);
		contentPane.add(stationThreeButton);
		
		JButton stationFourButton = new JButton("Station 4");
		stationFourButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 4");
				StationItemGUI stationItemGUI = new StationItemGUI(AttendantStation.scanAndBagConnected.get(3).getItemsScanned(), 3);
				stationItemGUI.setVisible(true);
			}
		});
		stationFourButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		stationFourButton.setBounds(100, 520, 233, 64);
		contentPane.add(stationFourButton);
		
		JButton stationFiveButton = new JButton("Station 5");
		stationFiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 5");
				StationItemGUI stationItemGUI = new StationItemGUI(AttendantStation.scanAndBagConnected.get(4).getItemsScanned(), 4);
				stationItemGUI.setVisible(true);
			}
		});
		stationFiveButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		stationFiveButton.setBounds(100, 680, 233, 64);
		contentPane.add(stationFiveButton);
		
		JButton stationSixButton = new JButton("Station 6");
		stationSixButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 6");
				StationItemGUI stationItemGUI = new StationItemGUI(AttendantStation.scanAndBagConnected.get(5).getItemsScanned(), 5);
				stationItemGUI.setVisible(true);
			}
		});
		stationSixButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		stationSixButton.setBounds(500, 50, 233, 64);
		contentPane.add(stationSixButton);
		
		JButton stationSevenButton = new JButton("Station 7");
		stationSevenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 7");
				StationItemGUI stationItemGUI = new StationItemGUI(AttendantStation.scanAndBagConnected.get(6).getItemsScanned(), 6);
				stationItemGUI.setVisible(true);
			}
		});
		stationSevenButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		stationSevenButton.setBounds(500, 200, 233, 64);
		contentPane.add(stationSevenButton);
		
		JButton stationEightButton = new JButton("Station 8");
		stationEightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 8");
				StationItemGUI stationItemGUI = new StationItemGUI(AttendantStation.scanAndBagConnected.get(7).getItemsScanned(), 7);
				stationItemGUI.setVisible(true);
			}
		});
		stationEightButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		stationEightButton.setBounds(500, 360, 233, 64);
		contentPane.add(stationEightButton);
		
		JButton stationNineButton = new JButton("Station 9");
		stationNineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 9");
				StationItemGUI stationItemGUI = new StationItemGUI(AttendantStation.scanAndBagConnected.get(8).getItemsScanned(), 8);
				stationItemGUI.setVisible(true);
			}
		});
		stationNineButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		stationNineButton.setBounds(500, 520, 233, 64);
		contentPane.add(stationNineButton);
		
		JButton stationTenButton = new JButton("Station 10");
		stationTenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 10");
				StationItemGUI stationItemGUI = new StationItemGUI(AttendantStation.scanAndBagConnected.get(9).getItemsScanned(), 9);
				stationItemGUI.setVisible(true);
			}
		});
		stationTenButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		stationTenButton.setBounds(500, 680, 233, 64);
		contentPane.add(stationTenButton);
		
		JButton cancelButton = new JButton("Done");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Done");
				dispose();
			}
		});
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		cancelButton.setBounds(900, 50, 233, 64);
		contentPane.add(cancelButton);
		
		JTextArea txtrAttendantRemovalScreen = new JTextArea();
		txtrAttendantRemovalScreen.setEditable(false);
		txtrAttendantRemovalScreen.setFont(new Font("Monospaced", Font.BOLD, 13));
		txtrAttendantRemovalScreen.setText("Attendant Item Removal Screen");
		txtrAttendantRemovalScreen.setBounds(879, 312, 244, 64);
		contentPane.add(txtrAttendantRemovalScreen);
	
	}
}
