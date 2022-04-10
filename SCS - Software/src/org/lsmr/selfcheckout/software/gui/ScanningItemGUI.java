package org.lsmr.selfcheckout.software.gui;

import org.lsmr.selfcheckout.software.*;

import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.observers.*;
import org.lsmr.selfcheckout.products.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ScanningItemGUI extends JFrame {

	private JPanel contentPane;
	
	// Fixed Texts 
	private JTextField scanText;
	private JTextField scannedItemText;
	
	// Total Price Text
	private JTextField totalPriceText = new JTextField();

	
	// Item Description Text
	private JTextField itemOneText = new JTextField();
	private JTextField itemTwoText = new JTextField();
	private JTextField itemThreeText = new JTextField();
	private JTextField itemFourText = new JTextField();
	private JTextField itemFiveText = new JTextField();
	private JTextField itemSixText = new JTextField();
	private JTextField itemSevenText = new JTextField();
	private JTextField itemEightText = new JTextField();
	private JTextField itemNineText = new JTextField();
	private JTextField itemTenText = new JTextField();
	
	// Item Price Text
	private JTextField itemOnePriceText = new JTextField();
	private JTextField itemTwoPriceText = new JTextField();
	private JTextField itemThreePriceText = new JTextField();
	private JTextField itemFourPriceText = new JTextField();
	private JTextField itemFivePriceText = new JTextField();
	private JTextField itemSixPriceText = new JTextField();
	private JTextField itemSevenPriceText = new JTextField();
	private JTextField itemEightPriceText = new JTextField();
	private JTextField itemNinePriceText = new JTextField();
	private JTextField itemTenPriceText = new JTextField();

	// Display counter
	public int counter = 0;
	
	public ArrayList<String> items = new ArrayList<String>();
	public ArrayList<String> priceList = new ArrayList<String>();
	public ArrayList<ItemInfo> itemsScanned = new ArrayList<ItemInfo>();
	
	RequestAssistanceGUI requestGUI;

	WrongWeightGUI wrongWeightGUI;
	
	ScanLargeItemGUI scanLargeItemGUI;
	
	RemoveItemPromptGUI removeItemPromptGUI;
	
	BagsGUI bagsGUI;
	
	PersonalBagPromptGUI personalBagGUI;
	
	PaymentGUI paymentGUI;
	
	SelfCheckoutStationSoftware scss;
	
	boolean bagPromptedAlready = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScanningItemGUI frame = new ScanningItemGUI();
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
	public ScanningItemGUI(ArrayList<ItemInfo> is, SelfCheckoutStationSoftware software) {
		if(is != null) {
			itemsScanned = is;
		}
		
		scss = software;
		/*
		items.add("2L Milk $5 1");
		items.add("2L Milk $5 2");
		items.add("2L Milk $5 3");
		items.add("2L Milk $5 4");
		items.add("2L Milk $5 5");
		items.add("2L Milk $5 6");
		items.add("2L Milk $5 7");
		items.add("2L Milk $5 8");
		items.add("2L Milk $5 9");
		items.add("2L Milk $5 10");
		//items.add("2L Milk $5 11");
		
		priceList.add("1.1");
		priceList.add("2");
		priceList.add("3");
		priceList.add("4");
		priceList.add("5");
		priceList.add("6");
		priceList.add("7");
		priceList.add("8");
		priceList.add("9");
		priceList.add("10");
		//priceList.add("11");
		*/

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1415, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		
		
		
		scanText = new JTextField();
		scanText.setFont(new Font("Tahoma", Font.BOLD, 25));
		scanText.setText("Scan Item and Place into Bagging Area");
		scanText.setBounds(861, 40, 505, 159);
		scanText.setEditable(false);
		contentPane.add(scanText);
		scanText.setColumns(10);
		

		
		scannedItemText = new JTextField();
		scannedItemText.setFont(new Font("Tahoma", Font.BOLD, 30));
		scannedItemText.setText("Scanned Items:");
		scannedItemText.setBounds(41, 40, 557, 74);
		scannedItemText.setEditable(false);
		contentPane.add(scannedItemText);
		scannedItemText.setColumns(10);
		
		
		// Display items and text
		updateItemText();
		updatePriceText();
		
		//=======================================================================================================================\\
		// START BUTTONS CODE
		
		
		
		
		JButton checkoutButton = new JButton("Checkout");
		checkoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Checkout");
				System.out.println(software.total());
				
				if(itemsScanned.size() == 0) {
			        JOptionPane.showMessageDialog(null, "You must scan an item before checking out!","No Items Scanned", JOptionPane.WARNING_MESSAGE);
				} else if(bagPromptedAlready) {
				// If customer already entered number of bags
					checkoutOn();
					dispose();
				} else {
					
					// Ask if customer used bags
					int usedBags = JOptionPane.showConfirmDialog(null, "Did you use store bags?", "Bag Prompt", JOptionPane.YES_NO_OPTION);
					if(usedBags == JOptionPane.YES_NO_OPTION) {
						System.out.println("used bags");
						bagsOn();
						
						bagPromptedAlready = true;
						
					} else {
						checkoutOn();
						dispose();
					}
				}

				
				// Close this window
			}
		});
		checkoutButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		checkoutButton.setBounds(853, 786, 505, 123);
		contentPane.add(checkoutButton);
		
		

		JButton removeButton = new JButton("Remove Item");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Remove Item");
				removeItemOn();

				
			}
		});
		removeButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		removeButton.setBounds(1115, 384, 243, 74);
		contentPane.add(removeButton);
		
		
		
		JButton requestButton = new JButton("Request Assistance");
		requestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Request Assistance");
				requestAssistanceOn();
			}
		});
		requestButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		requestButton.setBounds(853, 384, 243, 74);
		contentPane.add(requestButton);
		
		
		
		JButton scanLargeButton = new JButton("Scan Large Item");
		scanLargeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Scan Large Item");
				scanLargeItemOn();

				
			}
		});
		scanLargeButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		scanLargeButton.setBounds(1115, 507, 243, 74);
		contentPane.add(scanLargeButton);
		
		
		
		JButton lookupButton = new JButton("Lookup Product");
		lookupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Lookup Product");
			}
		});
		lookupButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lookupButton.setBounds(1115, 268, 243, 74);
		contentPane.add(lookupButton);
		
		
		
		JButton upButton = new JButton("Up");
		upButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Up");
				
				// Displaying beginning of list already
				if(counter == 0) {
			        JOptionPane.showMessageDialog(null, "Cannot go up further","Cannot go up", JOptionPane.WARNING_MESSAGE);
			        return;
				}
				
				counter--;
				
				// Update screen item and price
				updateItemText();
				updatePriceText();
			}
		});
		upButton.setBounds(644, 180, 95, 84);
		contentPane.add(upButton);
		
		
		
		JButton downButton = new JButton("Down");
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Down");
				
				System.out.println(itemsScanned.size());
				System.out.println("counter = " + counter);
				if(counter + 1 + 10 > itemsScanned.size()) {
			        JOptionPane.showMessageDialog(null, "Cannot go down further","Cannot go down", JOptionPane.WARNING_MESSAGE);
			        return;
				}
				
				counter++; 
				
				// Update screen item and price
				updateItemText();
				updatePriceText();
				
			}
		});
		downButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		downButton.setBounds(644, 673, 106, 84);
		contentPane.add(downButton);
		
		JButton personalBagButton = new JButton("Use Personal Bags");
		personalBagButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Use personal bags");
				personalBagsOn();
			}
		});
		personalBagButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		personalBagButton.setBounds(853, 507, 243, 74);
		contentPane.add(personalBagButton);
		
		
		
		
		JButton pluButton = new JButton("Enter PLU");
		pluButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Use personal bags");
				personalBagsOn();
			}
		});
		pluButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		pluButton.setBounds(853, 268, 243, 74);
		contentPane.add(pluButton);
		
		
		
		// END BUTTONS CODE
		//=======================================================================================================================\\
		
	}
	
	
	public void updateItemText() {
		ArrayList<JTextField> array = new ArrayList<JTextField>();
		array.add(itemOneText);
		array.add(itemTwoText);
		array.add(itemThreeText);
		array.add(itemFourText);
		array.add(itemFiveText);
		array.add(itemSixText);
		array.add(itemSevenText);
		array.add(itemEightText);
		array.add(itemNineText);
		array.add(itemTenText);
		
		
		//int arrayLength = items.size();
		int arrayLength = itemsScanned.size();
		int iterator = 0;
		
		for(JTextField text : array) {
			if((iterator+ counter) >= arrayLength) {
				text.setText("");
			} else {
				text.setText(itemsScanned.get(iterator+counter).description);
			}
			
			text.setFont(new Font("Tahoma", Font.PLAIN, 20));
			text.setBounds(28, 180 + (60*iterator), 436, 37);
			text.setEditable(false);
			contentPane.add(text);
			itemOneText.setColumns(10);
			iterator++;
		}

		
		
	}
	
	public void updatePriceText() {
		ArrayList<JTextField> array = new ArrayList<JTextField>();
		array.add(itemOnePriceText);
		array.add(itemTwoPriceText);
		array.add(itemThreePriceText);
		array.add(itemFourPriceText);
		array.add(itemFivePriceText);
		array.add(itemSixPriceText);
		array.add(itemSevenPriceText);
		array.add(itemEightPriceText);
		array.add(itemNinePriceText);
		array.add(itemTenPriceText);
		
		int arrayLength = itemsScanned.size();
		
		int iterator = 0;
		
		double total = 0;
		
		// For formatting double to 2 decimal 
		DecimalFormat format = new DecimalFormat("0.00");
		
		BigDecimal totalBig = new BigDecimal(0.00);
		
		for(JTextField text : array) {
			if((iterator+ counter) >= arrayLength) {
				text.setText("");
			} else {
				BigDecimal bigDecVal = itemsScanned.get(iterator+counter).price;
				double val = bigDecVal.doubleValue();
				format.format(val);
				
				text.setText("$" + format.format(val));
				//text.setText("$" + priceList.get(iterator+counter));
				total += val;
				totalBig.add(bigDecVal);
			}
			
			text.setFont(new Font("Tahoma", Font.PLAIN, 20));
			text.setBounds(502, 180 + (60*iterator), 95, 37);
			text.setEditable(false);
			contentPane.add(text);
			itemOneText.setColumns(10);
			iterator++;
		}
		
		totalPriceText.setText("Total: $" + format.format(total));
		totalPriceText.setFont(new Font("Tahoma", Font.PLAIN, 25));
		totalPriceText.setBounds(420, 811, 250, 43);
		totalPriceText.setEditable(false);
		contentPane.add(totalPriceText);
		totalPriceText.setColumns(10);
		
	}

	// Call when adding item
	// Updates GUI
	public void addItem() {
		System.out.println("added");
		
		if(itemsScanned.size() > 10) {
			counter++;
		}
		
		updateItemText();
		updatePriceText();
	}
	

	// Call when removing item
	// Updates GUI
	public void removeItem() {
		if(counter > 0) {
			counter--;
		}
		updateItemText();
		updatePriceText();
	}
	
	public void requestAssistanceOn() {
		requestGUI = new RequestAssistanceGUI();
		requestGUI.setVisible(true);

	}
	
	public void requestAssistanceOff() {
		if(requestGUI != null) {
			requestGUI.dispose();
		} else {
			System.out.println("Prompt is already off");
		}
	}
	
	public void wrongWeightOn() {
		wrongWeightGUI = new WrongWeightGUI();
		wrongWeightGUI.setVisible(true);
	}
	
	public void wrongWeightOff() {
		if(wrongWeightGUI != null) {
			wrongWeightGUI.dispose();
		} else {
			System.out.println("Prompt is already off");
		}
		
	}
	
	public void scanLargeItemOn() {
		scanLargeItemGUI = new ScanLargeItemGUI();
		scanLargeItemGUI.setVisible(true);
	}
	
	public void scanLargeItemOff() {
		if(scanLargeItemGUI != null) {
			scanLargeItemGUI.dispose();
		} else {
			System.out.println("Prompt is already off");
		}
	}
	
	public void removeItemOn() {
		removeItemPromptGUI = new RemoveItemPromptGUI();
		removeItemPromptGUI.setVisible(true);
	}
	
	public void removeItemOff() {
		if(removeItemPromptGUI != null) {
			removeItemPromptGUI.dispose();
		} else {
			System.out.println("Prompt is already off");
		}
	}
	
	public void lookupItemOn() {
		
	}
	
	public void lookupItemOff() {
		
	}
	
	public void checkoutOn() {
		paymentGUI = null;
		paymentGUI = new PaymentGUI(scss);
		//checkoutGUI = new FakeCheckoutGUI(itemsScanned);
		//checkoutGUI.setVisible(true);
		
	}
 	
	public void checkoutOff() {
		
	}
	
	public void bagsOn() {
		bagsGUI = new BagsGUI(this);
		bagsGUI.setVisible(true);
	}
 	
	public void bagsOff() {
		if(bagsGUI != null) {
			bagsGUI.dispose();
		} else {
			System.out.println("Prompt is already off");
		}
	}
	
	public void personalBagsOn() {
		personalBagGUI = new PersonalBagPromptGUI();
		personalBagGUI.setVisible(true);
	}
	
	public void personalBagsOff() {
		if(personalBagGUI != null) {
			personalBagGUI.dispose();
		} else {
			System.out.println("Prompt is already off");
		}
	}
	
}
