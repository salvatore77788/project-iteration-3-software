package org.lsmr.selfcheckout.software.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.software.EnterPLU;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JList;

public class EnterPLUGUI {

	private JFrame frmEnterPlu;
	private JTextField txtEnterPluHere;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterPLUGUI window = new EnterPLUGUI();
					window.frmEnterPlu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EnterPLUGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		EnterPLU EnterPLU = new EnterPLU();
		frmEnterPlu = new JFrame();
		frmEnterPlu.setTitle("Enter PLU");
		frmEnterPlu.setBounds(100, 100, 450, 300);
		frmEnterPlu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEnterPlu.getContentPane().setLayout(null);
		
		txtEnterPluHere = new JTextField();
		txtEnterPluHere.setBounds(148, 38, 102, 20);
		frmEnterPlu.getContentPane().add(txtEnterPluHere);
		txtEnterPluHere.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Enter PLU Here");
		lblNewLabel.setBounds(148, 11, 102, 14);
		frmEnterPlu.getContentPane().add(lblNewLabel);
		
		JList list = new JList();
		list.setBounds(21, 113, 251, 124);
		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount() == 1) {
					JList itemList = (JList)e.getSource();
					int index = itemList.locationToIndex(e.getPoint());
					if(index >=0) {
						EnterPLU.AddtoCheckout((PLUCodedProduct) itemList.getModel().getElementAt(index));
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		frmEnterPlu.getContentPane().add(list);
		
		JLabel lblNewLabel_1 = new JLabel("Select Product");
		lblNewLabel_1.setBounds(91, 88, 102, 14);
		frmEnterPlu.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(322, 214, 89, 23);
		frmEnterPlu.getContentPane().add(btnNewButton);
		txtEnterPluHere.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				ArrayList<PLUCodedProduct> productList = EnterPLU.SearchByPLU(txtEnterPluHere.getText());
				list.setListData(productList.toArray());;
			}
			  });
	}
}
