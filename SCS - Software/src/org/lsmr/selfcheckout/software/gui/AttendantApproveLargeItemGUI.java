package org.lsmr.selfcheckout.software.gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.software.AttendantStation;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AttendantApproveLargeItemGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AttendantApproveLargeItemGUI frame = new AttendantApproveLargeItemGUI();
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
	public AttendantApproveLargeItemGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton stationOne = new JButton("Approve Station 1");
		stationOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 1");
				AttendantStation.scanAndBagConnected.get(0).scanGUI.scanLargeItemOff();
				
			}
		});
		stationOne.setFont(new Font("Tahoma", Font.PLAIN, 20));

		stationOne.setBounds(100, 50, 233, 64);
		contentPane.add(stationOne);
		
		JButton stationTwoButton = new JButton("Approve Station 2");
		stationTwoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 2");
				AttendantStation.scanAndBagConnected.get(1).scanGUI.scanLargeItemOff();

			}
		});
		stationTwoButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stationTwoButton.setBounds(100, 200, 233, 64);
		contentPane.add(stationTwoButton);
		
		JButton stationThreeButton = new JButton("Approve Station 3");
		stationThreeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 3");
				AttendantStation.scanAndBagConnected.get(2).scanGUI.scanLargeItemOff();

			}
		});
		stationThreeButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stationThreeButton.setBounds(100, 360, 233, 64);
		contentPane.add(stationThreeButton);
		
		JButton stationFourButton = new JButton("Approve Station 4");
		stationFourButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 4");
				AttendantStation.scanAndBagConnected.get(3).scanGUI.scanLargeItemOff();

			}
		});
		stationFourButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stationFourButton.setBounds(100, 520, 233, 64);
		contentPane.add(stationFourButton);
		
		JButton stationFiveButton = new JButton("Approve Station 5");
		stationFiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 5");
				AttendantStation.scanAndBagConnected.get(4).scanGUI.scanLargeItemOff();

			}
		});
		stationFiveButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stationFiveButton.setBounds(100, 680, 233, 64);
		contentPane.add(stationFiveButton);
		
		JButton stationSixButton = new JButton("Approve Station 6");
		stationSixButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 6");
				AttendantStation.scanAndBagConnected.get(5).scanGUI.scanLargeItemOff();

			}
		});
		stationSixButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stationSixButton.setBounds(500, 50, 233, 64);
		contentPane.add(stationSixButton);
		
		JButton stationSevenButton = new JButton("Approve Station 7");
		stationSevenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 7");
				AttendantStation.scanAndBagConnected.get(6).scanGUI.scanLargeItemOff();

			}
		});
		stationSevenButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stationSevenButton.setBounds(500, 200, 233, 64);
		contentPane.add(stationSevenButton);
		
		JButton stationEightButton = new JButton("Approve Station 8");
		stationEightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 8");
				AttendantStation.scanAndBagConnected.get(7).scanGUI.scanLargeItemOff();

			}
		});
		stationEightButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stationEightButton.setBounds(500, 360, 233, 64);
		contentPane.add(stationEightButton);
		
		JButton stationNineButton = new JButton("Approve Station 9");
		stationNineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 9");
				AttendantStation.scanAndBagConnected.get(8).scanGUI.scanLargeItemOff();

			}
		});
		stationNineButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stationNineButton.setBounds(500, 520, 233, 64);
		contentPane.add(stationNineButton);
		
		JButton stationTenButton = new JButton("Approve Station 10");
		stationTenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Station 10");
				AttendantStation.scanAndBagConnected.get(9).scanGUI.scanLargeItemOff();

			}
		});
		stationTenButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
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

	}

}
