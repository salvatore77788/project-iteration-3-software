package org.lsmr.selfcheckout.software.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.TouchScreen;
import org.lsmr.selfcheckout.external.CardIssuer;
import org.lsmr.selfcheckout.software.BanknoteSlotSoftware;
import org.lsmr.selfcheckout.software.CardSoftware;
import org.lsmr.selfcheckout.software.CoinSlotSoftware;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

public class CardsPayment {
	
	// Parameters for SelfCheckoutStation
	public static final Currency CAD = Currency.getInstance(Locale.CANADA);
	public static final int[] banknoteDenominations = {5, 10, 20, 50, 100, 500};
	public static final BigDecimal[] coinDenominations = {new BigDecimal("0.05"), new BigDecimal("0.10"), new BigDecimal("0.25"), new BigDecimal("1.00"), new BigDecimal("2.00")};
	private SelfCheckoutStationSoftware scs;
	// JPanel for containing other machine workspaces
	JPanel container = new JPanel();
	CardLayout cardLayout = new CardLayout();
	
	// JPanel for start up
	JPanel startUp = new JPanel();
	public static volatile JButton startUpButton = new JButton("Start Up");
	
	// JPanel for machine welcome
	JPanel welcome = new JPanel();
	public static volatile JButton startButton = new JButton("Start");
	public static volatile JButton ownBagsButton = new JButton("I brought my own bag");
	
	// JPanel for membership entry 
	JPanel enterMemberNum = new JPanel();
	JLabel memberLabel = new JLabel("Please enter your member information or scan your card.");
	JLabel memberNumLabel = new JLabel("Number:");
	JLabel memberNameLabel = new JLabel("Name:");
	
	// JPanel for selecting payment type
	JPanel selectPaymentType = new JPanel();
	JLabel paymentTypeLabel = new JLabel("Please select your payment type.");
	public static volatile JButton cashButton = new JButton("Cash");
	public static volatile JButton creditButton = new JButton("Credit");
	public static volatile JButton debitButton = new JButton("Debit");
	public static volatile JButton giftCardButton = new JButton("Gift Card");
	public static volatile JButton paymentGoBackButton = new JButton("Go Back");
	
	//JPanel for pay with credit
	JPanel payWithCredit = new JPanel();
	public static volatile JButton swipeCredit = new JButton("Swipe");
	public static volatile JButton tapCredit = new JButton("Tap");
	public static volatile JButton insertCredit = new JButton("Insert");
	
	//JPanel for pay with debit
	JPanel payWithDebit = new JPanel();
	public static volatile JButton swipeDebit = new JButton("Swipe");
	public static volatile JButton tapDebit = new JButton("Tap");
	public static volatile JButton insertDebit = new JButton("Insert");
	
	


	
	// Declaring software classes
	public static CardSoftware cardPayment;
	public static CoinSlotSoftware coinPayment;
	public static BanknoteSlotSoftware notePayment;
	
	// Declaring other relevant parameters
	public static Calendar expiry = Calendar.getInstance();
	
	// Constructor 
	public void CardsPayment(SelfCheckoutStation selfCheckout) {
		
		container.setLayout(cardLayout);
		
		container.add(selectPaymentType, "paymenttype");
		container.add(payWithCredit, "credit");
		container.add(payWithDebit, "debit");
		cardLayout.show(container, "startup");
		
		constructSelectPaymentPanel();
		constructPayWithCreditPanel();
		constructPayWithDebitPanel();
		selfCheckout.screen.getFrame().add(container);
		selfCheckout.screen.getFrame().setVisible(true);
		
	}

	public void constructSelectPaymentPanel() {
		
		// Format the panel
		selectPaymentType.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.gridx = 0; gbc.gridy = 0;
		gbc.gridwidth = 2;
		selectPaymentType.add(paymentTypeLabel, gbc);
		gbc.gridx = 0; gbc.gridy = 1;
		gbc.gridwidth = 1;
		selectPaymentType.add(cashButton, gbc);
		cashButton.setPreferredSize(new Dimension(200, 120));
		gbc.gridx = 1; gbc.gridy = 1;
		selectPaymentType.add(creditButton, gbc);
		creditButton.setPreferredSize(new Dimension(200, 120));
		gbc.gridx = 0; gbc.gridy = 2;
		selectPaymentType.add(debitButton, gbc);
		debitButton.setPreferredSize(new Dimension(200, 120));
		gbc.gridx = 1; gbc.gridy = 2;
		selectPaymentType.add(giftCardButton, gbc);
		giftCardButton.setPreferredSize(new Dimension(200, 120));
		gbc.gridx = 0; gbc.gridy = 3;
		gbc.gridwidth = 2;
		selectPaymentType.add(paymentGoBackButton, gbc);
		paymentGoBackButton.setPreferredSize(new Dimension(400, 120));
		
		// Action Listener for creditButton
		creditButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(container, "credit");
			}
		});
		
		// Action Listener for debitButton
		debitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(container, "debit");
			}
		});
		
		// Action Listener for giftCardButton
		giftCardButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Card giftCard = new Card("gift card", "6750056347755", "Andrew", null, null, false, false);
				CardIssuer coop = new CardIssuer("Co-op");
				coop.addCardData("6750056347755", "Andrew", expiry, "000", new BigDecimal(100));
				try {
					cardPayment.PaywithGiftCard(giftCard, 1, new BigDecimal (6), scs.getTotalGUI(), coop);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Action Listener for paymentGoBackButton
		paymentGoBackButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(container, "member");
			}
		});
		
	}
	
	public void constructPayWithCreditPanel() {
		// Format the panel
		payWithCredit.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.gridx = 0; gbc.gridy = 0;
		payWithCredit.add(swipeCredit, gbc);
		swipeCredit.setPreferredSize(new Dimension(200, 120));
		gbc.gridx = 1; gbc.gridy = 0;
		payWithCredit.add(tapCredit, gbc);
		tapCredit.setPreferredSize(new Dimension(200, 120));
		gbc.gridx = 2; gbc.gridy = 0;
		payWithCredit.add(insertCredit, gbc);
		insertCredit.setPreferredSize(new Dimension(200, 120));
		
		Card creditCard = new Card("credit", "6750094154567755", "Billy", "043", "9876", true, true);
		CardIssuer Bank = new CardIssuer("TD Canada");
		Bank.addCardData("6750094154567755", "Billy", expiry, "043", new BigDecimal(500));
		
		// Action Listener for swipeCredit
		swipeCredit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					cardPayment.PayWithcreditcard(creditCard, 2, new BigDecimal(6), "9876", scs.getTotalGUI(), Bank);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Action Listener for tapCredit
		tapCredit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					cardPayment.PayWithcreditcard(creditCard, 1, new BigDecimal(6), "9876", scs.getTotalGUI(), Bank);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Action Listener for insertCredit
		insertCredit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					cardPayment.PayWithcreditcard(creditCard, 3, new BigDecimal(6),"9876",scs.getTotalGUI(), Bank);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void constructPayWithDebitPanel() {
		// Format the panel
		payWithDebit.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.gridx = 0; gbc.gridy = 0;
		payWithDebit.add(swipeDebit, gbc);
		swipeDebit.setPreferredSize(new Dimension(200, 120));
		gbc.gridx = 1; gbc.gridy = 0;
		payWithDebit.add(tapDebit, gbc);
		tapDebit.setPreferredSize(new Dimension(200, 120));
		gbc.gridx = 2; gbc.gridy = 0;
		payWithDebit.add(insertDebit, gbc);
		insertDebit.setPreferredSize(new Dimension(200, 120));
		
		Card debitCard = new Card("debit", "6750094154567755", "Billy", "043", "9876", true, true);
		CardIssuer Bank = new CardIssuer("TD Canada");
		Bank.addCardData("6750094154567755", "Billy", expiry, "043", new BigDecimal(500));
		
		// Action Listener for swipeCredit
		swipeDebit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					cardPayment.PayWithDebitCard(debitCard, 2, new BigDecimal(6), "9876",scs.getTotalGUI(), Bank);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Action Listener for tapCredit
		tapDebit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					cardPayment.PayWithDebitCard(debitCard, 1, new BigDecimal(6), "9876", scs.getTotalGUI(), Bank);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Action Listener for insertCredit
		insertDebit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					cardPayment.PayWithDebitCard(debitCard, 3, new BigDecimal(6), "9876",scs.getTotalGUI(), Bank);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
	
	

	// Executes the GUI startup
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Initialization of the self checkout station and software classes
		SelfCheckoutStation selfCheckout = new SelfCheckoutStation(CAD, banknoteDenominations, coinDenominations, 1000, 1);

		cardPayment = new CardSoftware(selfCheckout);

		
		// Initialization of relavent parameters

		
		expiry.add(Calendar.MONTH, 2);
		expiry.add(Calendar.YEAR, 2);

		// Calling GUI constructor
		new CardsPayment();

	}

}
