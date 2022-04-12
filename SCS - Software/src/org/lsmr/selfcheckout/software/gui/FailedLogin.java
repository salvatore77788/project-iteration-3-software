package org.lsmr.selfcheckout.software.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class FailedLogin extends JDialog {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FailedLogin frame = new FailedLogin();
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
	public FailedLogin() {
		setModal(true);
		setTitle("Login Failed");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 354, 164);
		contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtrLoginFailedPlease = new JTextArea();
		txtrLoginFailedPlease.setEditable(false);
		txtrLoginFailedPlease.setText("Login failed, please try again");
		txtrLoginFailedPlease.setBackground(UIManager.getColor("Button.background"));
		txtrLoginFailedPlease.setBounds(39, 25, 258, 22);
		contentPane.add(txtrLoginFailedPlease);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		okButton.setBounds(118, 71, 89, 23);
		contentPane.add(okButton);
	}

}
