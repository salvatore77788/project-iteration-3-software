package org.lsmr.selfcheckout.software.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PaymentCompleteGUI extends JFrame {

	private JPanel contentPane;
	private JTextField completeText;
	private JTextField takeText;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the frame.
	 */
	public PaymentCompleteGUI(SelfCheckoutStationSoftware scs) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1006, 643);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		completeText = new JTextField();
		completeText.setHorizontalAlignment(SwingConstants.CENTER);
		completeText.setFont(new Font("Tahoma", Font.BOLD, 50));
		completeText.setText("Payment Complete!");
		completeText.setEditable(false);
		completeText.setBounds(10, 37, 933, 247);
		contentPane.add(completeText);
		completeText.setColumns(10);
		
		takeText = new JTextField();
		takeText.setFont(new Font("Tahoma", Font.PLAIN, 40));
		takeText.setText("Please take your receipt and bags");
		takeText.setEditable(false);
		takeText.setBounds(61, 323, 820, 86);
		contentPane.add(takeText);
		takeText.setColumns(10);
		
		btnNewButton = new JButton("Reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scs.resetVars();
				dispose();
				SelfCheckoutSystemSoftwareGUI scssGUI = new SelfCheckoutSystemSoftwareGUI(scs.scs, scs);
				scssGUI.setVisible(true);
				
			}
		});
		btnNewButton.setBounds(97, 472, 85, 21);
		contentPane.add(btnNewButton);
		
		
		
		
	}

}
