package org.lsmr.selfcheckout.software.gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class RequestAssistanceGUI extends JFrame {

	private JPanel contentPane;

	private boolean done = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RequestAssistanceGUI frame = new RequestAssistanceGUI();
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
	public RequestAssistanceGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1363, 636);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextField attendantText = new JTextField();
		attendantText.setText("Attendant has been notified.");
		attendantText.setHorizontalAlignment(SwingConstants.CENTER);
		attendantText.setFont(new Font("Tahoma", Font.BOLD, 40));
		attendantText.setEditable(false);
		attendantText.setColumns(10);
		attendantText.setBounds(95, 31, 1179, 187);
		contentPane.add(attendantText);
		
		JTextField scanLargeText = new JTextField();
		scanLargeText = new JTextField();
		scanLargeText.setText("Request Assistance ");
		scanLargeText.setFont(new Font("Tahoma", Font.PLAIN, 35));
		scanLargeText.setEditable(false);
		scanLargeText.setColumns(10);
		scanLargeText.setBounds(190, 250, 960, 298);
		contentPane.add(scanLargeText);
	}

	public void setDone(boolean bool) {
		done = bool;
	}
	
	public boolean getDone() {
		return done;
	}
	
}
