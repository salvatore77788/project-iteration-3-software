package org.lsmr.selfcheckout.software.gui;

import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.observers.*;
import org.lsmr.selfcheckout.products.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class StationItemGUI extends JFrame {

	private JPanel contentPane;
	private JTextField text;
	
	private JTextField itemOne = new JTextField();
	private JTextField itemTwo = new JTextField();
	private JTextField itemThree = new JTextField();
	private JTextField itemFour = new JTextField();
	private JTextField itemFive = new JTextField();
	private JTextField itemSix = new JTextField();
	private JTextField itemSeven = new JTextField();
	private JTextField itemEight = new JTextField();
	private JTextField itemNine = new JTextField();
	private JTextField itemTen = new JTextField();
	
	private JButton buttonOne = new JButton("Remove");
	private JButton buttonTwo = new JButton("Remove");
	private JButton buttonThree = new JButton("Remove");
	private JButton buttonFour = new JButton("Remove");
	private JButton buttonFive = new JButton("Remove");
	private JButton buttonSix = new JButton("Remove");
	private JButton buttonSeven = new JButton("Remove");
	private JButton buttonEight = new JButton("Remove");
	private JButton buttonNine = new JButton("Remove");
	private JButton buttonTen = new JButton("Remove");

	
	private JScrollPane scrollPane;
	private JButton button;
	public int counter = 1;
	
	public int itemToRemove = 0;
	private JTextField pageNumber = new JTextField();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the frame.
	 */
	public StationItemGUI(ArrayList<ItemInfo> items, int stationNum) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1054, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		displayItem(items);
		

	
		
		
		// Add Remove Buttons
		// ============================================================================== //
		
		buttonOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(itemOne.getText().isEmpty()) {
					System.out.println("Invalid item selection");
			        JOptionPane.showMessageDialog(null, "Item does not exist. Please select a valid one.","Invalid Item", JOptionPane.WARNING_MESSAGE);
					return;
				}
				itemToRemove = 0 + ((counter-1)*10);
				System.out.println("Remove item# = " + itemToRemove);
				items.remove(itemToRemove);
				counter = 1;
				displayItem(items);
		        JOptionPane.showMessageDialog(null, "Item successfully removed","Item Removed", JOptionPane.INFORMATION_MESSAGE);
		        System.out.println("stat num" + stationNum);
		        
		        AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItem();
		        System.out.println("here");

			}
		});
		buttonOne.setBounds(639, 21 + (0 * 40), 124, 37);
		contentPane.add(buttonOne);
		
		
		buttonTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(itemTwo.getText().isEmpty()) {
					System.out.println("Invalid item selection");
			        JOptionPane.showMessageDialog(null, "Item does not exist. Please select a valid one.","Invalid Item", JOptionPane.WARNING_MESSAGE);
					return;
				}
				itemToRemove = 1 + ((counter-1)*10);
				System.out.println("Remove item# = " + itemToRemove);	
				items.remove(itemToRemove);
				counter = 1;
				displayItem(items);
		        JOptionPane.showMessageDialog(null, "Item successfully removed","Item Removed", JOptionPane.INFORMATION_MESSAGE);
		        AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItem();

			}
		});
		buttonTwo.setBounds(639, 21 + (1 * 40), 124, 37);
		contentPane.add(buttonTwo);
		
		buttonThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(itemThree.getText().isEmpty()) {
					System.out.println("Invalid item selection");
			        JOptionPane.showMessageDialog(null, "Item does not exist. Please select a valid one.","Invalid Item", JOptionPane.WARNING_MESSAGE);
					return;
				}
				itemToRemove = 2 + ((counter-1)*10);
				System.out.println("Remove item# = " + itemToRemove);	
				items.remove(itemToRemove);
				counter = 1;
				displayItem(items);
		        JOptionPane.showMessageDialog(null, "Item successfully removed","Item Removed", JOptionPane.INFORMATION_MESSAGE);
		        AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItem();

			}
		});
		buttonThree.setBounds(639, 21 + (2 * 40), 124, 37);
		contentPane.add(buttonThree);
		
		buttonFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(itemFour.getText().isEmpty()) {
					System.out.println("Invalid item selection");
			        JOptionPane.showMessageDialog(null, "Item does not exist. Please select a valid one.","Invalid Item", JOptionPane.WARNING_MESSAGE);
					return;
				}
				itemToRemove = 3 + ((counter-1) * 10);
				System.out.println("Remove item# = " + itemToRemove);	
				items.remove(itemToRemove);
				counter = 1;
				displayItem(items);
		        JOptionPane.showMessageDialog(null, "Item successfully removed","Item Removed", JOptionPane.INFORMATION_MESSAGE);
		        AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItem();

			}
		});
		buttonFour.setBounds(639, 21 + (3 * 40), 124, 37);
		contentPane.add(buttonFour);
		
		buttonFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(itemFive.getText().isEmpty()) {
					System.out.println("Invalid item selection");
			        JOptionPane.showMessageDialog(null, "Item does not exist. Please select a valid one.","Invalid Item", JOptionPane.WARNING_MESSAGE);
					return;
				}
				itemToRemove = 4 + ((counter-1)*10);
				System.out.println("Remove item# = " + itemToRemove);	
				items.remove(itemToRemove);
				counter = 1;
				displayItem(items);
		        JOptionPane.showMessageDialog(null, "Item successfully removed","Item Removed", JOptionPane.INFORMATION_MESSAGE);
		        AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItem();

			}
		});
		buttonFive.setBounds(639, 21 + (4 * 40), 124, 37);
		contentPane.add(buttonFive);
		
		buttonSix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(itemSix.getText().isEmpty()) {
					System.out.println("Invalid item selection");
			        JOptionPane.showMessageDialog(null, "Item does not exist. Please select a valid one.","Invalid Item", JOptionPane.WARNING_MESSAGE);
					return;
				}
				itemToRemove = 5 + ((counter-1)*10);
				System.out.println("Remove item# = " + itemToRemove);	
				items.remove(itemToRemove);
				counter = 1;
				displayItem(items);
		        JOptionPane.showMessageDialog(null, "Item successfully removed","Item Removed", JOptionPane.INFORMATION_MESSAGE);
		        AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItem();

			}
		});
		buttonSix.setBounds(639, 21 + (5 * 40), 124, 37);
		contentPane.add(buttonSix);
		
		
		buttonSeven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(itemSeven.getText().isEmpty()) {
					System.out.println("Invalid item selection");
			        JOptionPane.showMessageDialog(null, "Item does not exist. Please select a valid one.","Invalid Item", JOptionPane.WARNING_MESSAGE);
					return;
				}
				itemToRemove = 6 + ((counter-1)*10);
				System.out.println("Remove item# = " + itemToRemove);	
				items.remove(itemToRemove);
				counter = 1;
				displayItem(items);
		        JOptionPane.showMessageDialog(null, "Item successfully removed","Item Removed", JOptionPane.INFORMATION_MESSAGE);
		        AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItem();

			}
		});
		buttonSeven.setBounds(639, 21 + (6 * 40), 124, 37);
		contentPane.add(buttonSeven);
		
		
		buttonEight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(itemEight.getText().isEmpty()) {
					System.out.println("Invalid item selection");
			        JOptionPane.showMessageDialog(null, "Item does not exist. Please select a valid one.","Invalid Item", JOptionPane.WARNING_MESSAGE);
					return;
				}
				itemToRemove = 7 + ((counter-1)*10);
				System.out.println("Remove item# = " + itemToRemove);	
				items.remove(itemToRemove);
				counter = 1;
				displayItem(items);
		        JOptionPane.showMessageDialog(null, "Item successfully removed","Item Removed", JOptionPane.INFORMATION_MESSAGE);
		        AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItem();

			}
		});
		buttonEight.setBounds(639, 21 + (7 * 40), 124, 37);
		contentPane.add(buttonEight);
		
		
		buttonNine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(itemNine.getText().isEmpty()) {
					System.out.println("Invalid item selection");
			        JOptionPane.showMessageDialog(null, "Item does not exist. Please select a valid one.","Invalid Item", JOptionPane.WARNING_MESSAGE);
					return;
				}
				itemToRemove = 8 + ((counter-1)*10);
				System.out.println("Remove item# = " + itemToRemove);
				items.remove(itemToRemove);
				counter = 1;
				displayItem(items);
		        JOptionPane.showMessageDialog(null, "Item successfully removed","Item Removed", JOptionPane.INFORMATION_MESSAGE);
		        AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItem();

			}
		});
		buttonNine.setBounds(639, 21 + (8 * 40), 124, 37);
		contentPane.add(buttonNine);
		
		
		buttonTen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String test = itemTen.getText();
				if(itemTen.getText().isEmpty()) {
					System.out.println("Invalid item selection");
			        JOptionPane.showMessageDialog(null, "Item does not exist. Please select a valid one.","Invalid Item", JOptionPane.WARNING_MESSAGE);
					return;
				}
				itemToRemove = 9 + ((counter-1)*10);
				System.out.println("Remove item# = " + itemToRemove);	
				items.remove(itemToRemove);
				counter = 1;
				displayItem(items);
		        JOptionPane.showMessageDialog(null, "Item successfully removed","Item Removed", JOptionPane.INFORMATION_MESSAGE);
		        AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItem();

			}
		});
		buttonTen.setBounds(639, 21 + (9 * 40), 124, 37);
		contentPane.add(buttonTen);
		
		
		// ================================================================================ //
		// End of Adding Remove Buttons
		
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Back");
				if(counter == 1) {
					System.out.println("Counter cannot go lower");
			        JOptionPane.showMessageDialog(null, "Cannot go back anymore","Invalid Action", JOptionPane.WARNING_MESSAGE);
					return;
				}
				counter--;
				displayItem(items);
				System.out.println("Counter == " + counter);

			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		backButton.setBounds(100, 477, 202, 60);
		contentPane.add(backButton);
		
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Next");
				if(counter*10 >= items.size() ) {
					System.out.println("No more items to view");
			        JOptionPane.showMessageDialog(null, "No more items to display","Invalid Action", JOptionPane.WARNING_MESSAGE);
					return;
					
				}
				counter++;
				displayItem(items);
				System.out.println("Counter == " + counter);
			}
		});
		
		nextButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		nextButton.setBounds(400, 477, 202, 60);
		contentPane.add(nextButton);
		
		JButton cancelButton = new JButton("Done");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AttendantStation.scanAndBagConnected.get(stationNum).scanGUI.removeItemOff();
				dispose();
			}
		});
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		cancelButton.setBounds(800, 477, 202, 60);
		contentPane.add(cancelButton);
		
		
	}
	
	
	public void displayItem(ArrayList<ItemInfo> itemsScanned) {
		int arrayLength = itemsScanned.size();
		System.out.println("array len" + arrayLength);
		
		ArrayList<JTextField> textArray = new ArrayList<JTextField>();
		textArray.add(itemOne);
		textArray.add(itemTwo);
		textArray.add(itemThree);
		textArray.add(itemFour);
		textArray.add(itemFive);
		textArray.add(itemSix);
		textArray.add(itemSeven);
		textArray.add(itemEight);
		textArray.add(itemNine);
		textArray.add(itemTen);

		int num = 0;
		int placementCounter = 0;
		
		for(JTextField text : textArray) {
			if ((counter-1)*10 + num >= arrayLength)  {
				text.setText("");
			} else {
				text.setText(itemsScanned.get((counter-1) * 10 + num).description);
			}
			text.setFont(new Font("Tahoma", Font.PLAIN, 20));
			text.setEditable(false);
			contentPane.add(text);
			text.setColumns(10);
			text.setBounds(34, (21 + (placementCounter * 40)), 570, 37);
			
			placementCounter++;
			num++;
		}
		
		pageNumber.setFont(new Font("Tahoma", Font.BOLD, 45));
		pageNumber.setText("Page " + String.valueOf(counter));
		pageNumber.setBounds(789, 34, 241, 78);
		pageNumber.setEditable(false);
		pageNumber.setColumns(10);
		contentPane.add(pageNumber);
		
		/*
		
		for(int i = 0; i < items.size(); i++) {
			text = new JTextField();
			text.setText(items.get(i));
			text.setFont(new Font("Tahoma", Font.PLAIN, 20));
			text.setBounds(34, (21 + (i * 40)), 570, 37);
			text.setEditable(false);
			contentPane.add(text);
			text.setColumns(10);
			
			button = new JButton("Remove");
			button.setBounds(639, 21 + (i * 40), 124, 37);
			contentPane.add(button);
		}
		*/
	}

}
