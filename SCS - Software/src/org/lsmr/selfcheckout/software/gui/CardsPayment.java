package org.lsmr.selfcheckout.software.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.external.CardIssuer;
import org.lsmr.selfcheckout.software.BanknoteSlotSoftware;
import org.lsmr.selfcheckout.software.CardSoftware;
import org.lsmr.selfcheckout.software.CoinSlotSoftware;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;

import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

public class CardsPayment extends JFrame {

	private JPanel contentPane;
	CardLayout cardLayout = new CardLayout();
	public SelfCheckoutStationSoftware scs;
	public CoinSlotSoftware coin;
	public BanknoteSlotSoftware banknote;
	public CardSoftware Payment;
	public static Calendar expiry = Calendar.getInstance();

	/**
	 * Launch the application.
	 */
	JLabel change = new JLabel("Change due: ");
	JLabel total = new JLabel("Outstanding Balance: $" + scs.total());
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardsPayment frame = new CardsPayment();
					frame.setSize(100,100);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	public CardsPayment() {
		setResizable(false);
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setTitle("Checkout Screen");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pick your payment method from below");
		lblNewLabel.setBounds(95, 50, 245, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnDebitButton = new JButton("Debit Card");
		btnDebitButton.setBounds(6, 78, 117, 29);
		contentPane.add(btnDebitButton);
		
		JButton btnCreditButton = new JButton("Credit Card");
		btnCreditButton.setBounds(145, 78, 117, 29);
		contentPane.add(btnCreditButton);
		
		JButton btnGiftButton = new JButton("Gift Card");
		btnGiftButton.setBounds(71, 138, 117, 29);
		contentPane.add(btnGiftButton);
		
		JButton btnMembershipButton = new JButton("Membership Card");
		btnMembershipButton.setBounds(257, 138, 167, 29);
		contentPane.add(btnMembershipButton);
		
		JButton btnCashButton = new JButton("Cash");
		btnCashButton.setBounds(307, 78, 117, 29);
		contentPane.add(btnCashButton);
		
		
		//Creditcard information
		Card creditCard = new Card("credit", "3924294505943847", "Bubby", "056", "1234", true, true);
		CardIssuer Bank = new CardIssuer("Binance");
		Bank.addCardData("3924294505943847", "Bubby", expiry, "056", new BigDecimal(10000));
	}
	

	//Method for checkout
	public void checkout() {
		BigDecimal endTotal = BigDecimal.ZERO;
		change.setText("Change due: $" + endTotal.abs());
		cardLayout.show(contentPane, "receipt");
	}
}
