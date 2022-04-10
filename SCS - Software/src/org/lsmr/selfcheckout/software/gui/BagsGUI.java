package org.lsmr.selfcheckout.software.gui;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.software.ItemInfo;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;

public class BagsGUI extends JFrame {

	private JPanel contentPane;
	private JTextField numOfBagsText;
	public ArrayList<String> numOfBags = new ArrayList<String>();
	public String finalNumOfBags = "";
	private JTextField textField;


	/**
	 * Create the frame.
	 */
	public BagsGUI(ScanningItemGUI siGUI) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 964, 699);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		numOfBagsText = new JTextField();
		numOfBagsText.setFont(new Font("Tahoma", Font.BOLD, 63));
		numOfBagsText.setBounds(101, 75, 670, 94);
		contentPane.add(numOfBagsText);
		numOfBagsText.setColumns(10);
		
		JTextArea enterBagText = new JTextArea();
		enterBagText.setEditable(false);
		enterBagText.setFont(new Font("Monospaced", Font.BOLD, 40));
		enterBagText.setText("Enter Number of Bags");
		enterBagText.setBounds(101, 10, 670, 55);
		contentPane.add(enterBagText);
		
		JButton oneButton = new JButton("1");
		oneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfBags.add("1");
				System.out.println(numOfBags);
				
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
				
			}
		});
		oneButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		oneButton.setBounds(69, 205, 141, 50);
		contentPane.add(oneButton);
		
		JButton twoButton = new JButton("2");
		twoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfBags.add("2");
				System.out.println(numOfBags);
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
			}
		});
		twoButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		twoButton.setBounds(334, 205, 141, 50);
		contentPane.add(twoButton);
		
		JButton threeButton = new JButton("3");
		threeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfBags.add("3");
				System.out.println(numOfBags);
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
			}
		});
		threeButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		threeButton.setBounds(606, 205, 141, 50);
		contentPane.add(threeButton);
		
		JButton fourButton = new JButton("4");
		fourButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfBags.add("4");
				System.out.println(numOfBags);
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
			}
		});
		fourButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		fourButton.setBounds(69, 331, 141, 50);
		contentPane.add(fourButton);
		
		JButton fiveButton = new JButton("5");
		fiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfBags.add("5");
				System.out.println(numOfBags);
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
			}
		});
		fiveButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		fiveButton.setBounds(334, 331, 141, 50);
		contentPane.add(fiveButton);
		
		JButton sixButton = new JButton("6");
		sixButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfBags.add("6");
				System.out.println(numOfBags);
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
			}
		});
		sixButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		sixButton.setBounds(606, 331, 141, 50);
		contentPane.add(sixButton);
		
		JButton sevenButton = new JButton("7");
		sevenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfBags.add("7");
				System.out.println(numOfBags);
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
			}
		});
		sevenButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		sevenButton.setBounds(69, 449, 141, 50);
		contentPane.add(sevenButton);
		
		JButton eightButton = new JButton("8");
		eightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfBags.add("8");
				System.out.println(numOfBags);
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
			}
		});
		eightButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		eightButton.setBounds(334, 449, 141, 50);
		contentPane.add(eightButton);
		
		JButton nineButton = new JButton("9");
		nineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfBags.add("9");
				System.out.println(numOfBags);
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
			}
		});
		nineButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		nineButton.setBounds(606, 449, 141, 50);
		contentPane.add(nineButton);
		
		JButton zeroButton = new JButton("0");
		zeroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfBags.add("0");
				System.out.println(numOfBags);
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
			}
		});
		zeroButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		zeroButton.setBounds(334, 566, 141, 50);
		contentPane.add(zeroButton);
		
		JButton btnErase = new JButton("Erase");
		btnErase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int arrayLength = numOfBags.size();
				if(arrayLength > 0) {
					numOfBags.remove(arrayLength-1);
				}
				System.out.println(numOfBags);
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				numOfBagsText.setText(total);
			}
		});
		btnErase.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnErase.setBounds(69, 566, 141, 50);
		contentPane.add(btnErase);
		
		JButton enterButton = new JButton("Enter");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Finished");
				System.out.println(numOfBags);
				
				// If nothing was entered 
				if(numOfBags.size() == 0) {
					System.out.println("Please enter valid number of bags");
					
			        JOptionPane.showMessageDialog(null, "Please enter valid number of bags","Invalid Number of Bags", JOptionPane.WARNING_MESSAGE);

					
					return;
				} 
				
				// Get number of bags entered
				String total = "";
				for(String s: numOfBags) {
					total += s;
				}
				
				// If amount entered was less then 1
				if(Integer.parseInt(total) < 1) {
					System.out.println("Please enter valid number of bags");
			        JOptionPane.showMessageDialog(null, "Please enter valid number of bags","Invalid Number of Bags", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				// Set final number of bags
				finalNumOfBags += total;
				
				
				
				siGUI.items.add(finalNumOfBags + " Bags");
				
				double totalPrice = Double.parseDouble(finalNumOfBags) * 0.05;
				siGUI.priceList.add(String.valueOf(totalPrice));
				
				String description = "";
				description += finalNumOfBags + " Bags";
				
				siGUI.itemsScanned.add(new ItemInfo(new BigDecimal(String.valueOf(totalPrice)), 0.01, description));
				
				siGUI.addItem();
				
				System.out.println("Final bag count =" + finalNumOfBags);
				dispose();
					
				
				

				
			}
		});
		enterButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		enterButton.setBounds(606, 566, 141, 50);
		contentPane.add(enterButton);
		

	}
	


}
