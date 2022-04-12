package org.lsmr.selfcheckout.software.gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class WrongWeightGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WrongWeightGUI frame = new WrongWeightGUI();
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
	public WrongWeightGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1364, 615);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextField alertText = new JTextField();
		alertText.setFont(new Font("Tahoma", Font.PLAIN, 35));
		alertText.setText("Bagging area weight does not match expected weight.");
		alertText.setBounds(208, 242, 960, 298);
		alertText.setEditable(false);
		contentPane.add(alertText);
		alertText.setColumns(10);
		
		JTextField reasonText = new JTextField();
		reasonText.setHorizontalAlignment(SwingConstants.CENTER);
		reasonText.setFont(new Font("Tahoma", Font.BOLD, 40));
		reasonText.setText("Attendant has been notified.");
		reasonText.setBounds(68, 45, 1179, 187);
		reasonText.setEditable(false);
		contentPane.add(reasonText);
		reasonText.setColumns(10);
	}

}
