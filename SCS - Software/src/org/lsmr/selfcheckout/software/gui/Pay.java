package org.lsmr.selfcheckout.software.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

public class Pay extends JPanel {
	private SelfCheckoutStationSoftware control;
	private JTextArea receipt;
	private JTextArea infoText;
	private JButton returnToAddingItems;
	private JButton restartGUI;
	private BigDecimal entered;
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private ArrayList<Banknote> banknotes = new ArrayList<Banknote>();
	/**
	 * Create the panel.
	 */
	public Pay(SelfCheckoutStationSoftware control) {
		
		returnToAddingItems.setOpaque(true);
		returnToAddingItems.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		returnToAddingItems.setBorder(new LineBorder(new Color(15, 17, 26), 1, true));
		returnToAddingItems.setBackground(new Color(204, 62, 68));
		returnToAddingItems.setBounds(980, 475, 280, 55);
		add(returnToAddingItems);

		JButton credit = new JButton("Pay with Credit Card");
		credit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String cardNum = JOptionPane.showInputDialog("Please enter the number of the credit card!", "");
				if(cardNum.equals("")) {
					JOptionPane.showMessageDialog(new JPanel(),
						"Invalid Inputs!",
						"Please Try Again!",
						JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(control.creditCards.containsKey(cardNum)) {
					Card card = control.creditCards.get(cardNum);
					Object[] options = { "Tap", "Insert", "Swipe" };
					int cardPayType = JOptionPane.showOptionDialog(
						new JPanel(), 
						"Please choose how you wish to use your card.", 
						"Pay With Credit Card!", 
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.PLAIN_MESSAGE, 
						null, 
						options, 
						options[0]
					);
					boolean success = false;
					try {
						if(cardPayType == 0) {
							success = control.tapCard(card);
						} else if(cardPayType == 1) {
							String pin = JOptionPane.showInputDialog("Please enter the card's pin!", "");
							success = control.insertCard(card, pin);
						} else if(cardPayType == 2) {
							success = control.swipeCard(card, null);
						} else {
							JOptionPane.showMessageDialog(new JPanel(),
								"You did not choose a valid method!",
								"Error!",
								JOptionPane.ERROR_MESSAGE);
								return;
						}
					} catch (Exception err) {
						success = false;
					}
					if(success) {
						entered = entered.add(control.getTotal());
						control.amountEntered = entered;
						JOptionPane.showMessageDialog(new JPanel(),
							"Payment Successful!",
							"Success!",
							JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(new JPanel(),
						"Payment Failed!",
						"Error!",
						JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else {
					JOptionPane.showMessageDialog(new JPanel(),
						"Card DNE!",
						"Error!",
						JOptionPane.ERROR_MESSAGE);
						return;
				}
			}
		});
		credit.setOpaque(true);
		credit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		credit.setBorder(new LineBorder(new Color(15, 17, 26), 1, true));
		credit.setBackground(new Color(255, 203, 107));
		credit.setBounds(980, 400, 280, 55);
		add(credit);
		
		JButton debit = new JButton("Pay with Debit Card");
		debit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String cardNum = JOptionPane.showInputDialog("Please enter the number of the debit card!", "");
				if(cardNum.equals("")) {
					JOptionPane.showMessageDialog(new JPanel(),
						"Invalid Inputs!",
						"Please Try Again!",
						JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(control.debitCards.containsKey(cardNum)) {
					Card card = control.debitCards.get(cardNum);
					Object[] options = { "Tap", "Insert", "Swipe" };
					int cardPayType = JOptionPane.showOptionDialog(
						new JPanel(), 
						"Please choose how you wish to use your card.", 
						"Pay With Debit Card!", 
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.PLAIN_MESSAGE, 
						null, 
						options, 
						options[0]
					);
					boolean success = false;
					try {
						if(cardPayType == 0) {
							success = control.tapCard(card);
						} else if(cardPayType == 1) {
							String pin = JOptionPane.showInputDialog("Please enter the card's pin!", "");
							success = control.insertCard(card, pin);
						} else if(cardPayType == 2) {
							success = control.swipeCard(card, null);
						} else {
							JOptionPane.showMessageDialog(new JPanel(),
								"You did not choose a valid method!",
								"Error!",
								JOptionPane.ERROR_MESSAGE);
								return;
						}
					} catch (Exception err) {
						success = false;
					}
					if(success) {
						entered = entered.add(control.getTotal());
						control.amountEntered = entered;
						JOptionPane.showMessageDialog(new JPanel(),
							"Payment Successful!",
							"Success!",
							JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(new JPanel(),
						"Payment Failed!",
						"Error!",
						JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else {
					JOptionPane.showMessageDialog(new JPanel(),
						"Card DNE!",
						"Error!",
						JOptionPane.ERROR_MESSAGE);
						return;
				}
			}
		});
		debit.setOpaque(true);
		debit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		debit.setBorder(new LineBorder(new Color(15, 17, 26), 1, true));
		debit.setBackground(new Color(255, 203, 107));
		debit.setBounds(980, 325, 280, 55);
		add(debit);
		
		JButton giftCard = new JButton("Pay with Giftcard");
		giftCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String cardNum = JOptionPane.showInputDialog("Please enter the number of the gift card!", "");
				if(cardNum.equals("")) {
					JOptionPane.showMessageDialog(new JPanel(),
						"Invalid Inputs!",
						"Please Try Again!",
						JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(control.giftCards.containsKey(cardNum)) {
					Card card = control.giftCards.get(cardNum);
					Object[] options = { "Swipe", "Insert" };
					int cardPayType = JOptionPane.showOptionDialog(
						new JPanel(), 
						"Please choose how you wish to use your card.", 
						"Pay With Gift Card!", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.PLAIN_MESSAGE, 
						null, 
						options, 
						options[0]
					);
					boolean success = false;
					try {
						if(cardPayType == 0) {
							success = control.swipeCard(card, null);
						} else if(cardPayType == 1) {
							String pin = JOptionPane.showInputDialog("Please enter the card's pin!", "");
							success = control.insertCard(card, pin);
						} else {
							JOptionPane.showMessageDialog(new JPanel(),
								"You did not choose a valid method!",
								"Error!",
								JOptionPane.ERROR_MESSAGE);
								return;
						}
					} catch (Exception err) {
						success = false;
					}
					if(success) {
						entered = entered.add(control.getTotal());
						control.amountEntered = entered;
						JOptionPane.showMessageDialog(new JPanel(),
							"Payment Successful!",
							"Success!",
							JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(new JPanel(),
						"Payment Failed!",
						"Error!",
						JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else {
					JOptionPane.showMessageDialog(new JPanel(),
						"Card DNE!",
						"Error!",
						JOptionPane.ERROR_MESSAGE);
						return;
				}
			}
		});
		giftCard.setOpaque(true);
		giftCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		giftCard.setBorder(new LineBorder(new Color(15, 17, 26), 1, true));
		giftCard.setBackground(new Color(255, 203, 107));
		giftCard.setBounds(980, 250, 280, 55);
		add(giftCard);
		
		
	}
}
