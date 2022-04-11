package org.lsmr.selfcheckout.software.gui;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

/**
 * Presents the customer with a catalog of items they can look up.
 */
public class ProductCatalogGUI extends javax.swing.JFrame {
	// TODO Move these static methods out?
	private static void populateDatabases() {
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.PLU_PRODUCT_DATABASE.clear();
		
		addBarcodedProduct(createBarcode("0001"), "Aluminum chips 1 lb bag", new BigDecimal("3.99"), 1.5);
		addBarcodedProduct(createBarcode("0002"), "Dairy Land 2% Milk", new BigDecimal("1.58"), 1.5);
		addBarcodedProduct(createBarcode("0003"), "50Pc Disposable Face Mask", new BigDecimal("26.96"), 1.5);
		addBarcodedProduct(createBarcode("0004"), "Industrial Air Dryer", new BigDecimal("326.99"), 1.5);
		addBarcodedProduct(createBarcode("0005"), "Magnesium 200mg 50Pc", new BigDecimal("11.50"), 1.5);
		addBarcodedProduct(createBarcode("0006"), "Soup with Soup", new BigDecimal("5.99"), 1.5);
		addBarcodedProduct(createBarcode("0007"), "Crystal Pepsi 1 litre", new BigDecimal("4.99"), 1.5);
		addBarcodedProduct(createBarcode("0008"), "24-karat Gold Bar", new BigDecimal("0.99"), 1.5);
		addBarcodedProduct(createBarcode("0009"), "500Pc Cat and Dog Puzzle", new BigDecimal("30.99"), 1.5);
		addBarcodedProduct(createBarcode("0010"), "Recursive Snowglobe", new BigDecimal("8.95"), 1.5);

		addPLUProduct("1010", "Granny Smith Apple", new BigDecimal("3.49"));
		addPLUProduct("1234", "Jumble of Bananas", new BigDecimal("7.89"));
		addPLUProduct("4321", "PLU Sticker Set", new BigDecimal("33941.99"));
		addPLUProduct("7681", "Orange Orange", new BigDecimal("4.99"));
		addPLUProduct("29984", "Nefarious Collection of Pizza Slices", new BigDecimal("2.99"));
		addPLUProduct("8473", "Eurasian Golden Oriole", new BigDecimal("119.99"));
		addPLUProduct("1794", "Broccoli", new BigDecimal("24.55"));
		addPLUProduct("99383", "Solitary Banana", new BigDecimal("1.99"));
		addPLUProduct("0382", "Watermelon 80lb", new BigDecimal("4.99"));
		addPLUProduct("10093", "Subdivided Strawberries", new BigDecimal("9.99"));
	}

	private static void addPLUProduct(String code, String desc, BigDecimal price) {
		PriceLookupCode plu = new PriceLookupCode(code);
		PLUCodedProduct p = new PLUCodedProduct(plu, desc, price);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu, p);
	}

	private static void addBarcodedProduct(Barcode bc, String desc, BigDecimal price, double weight) {
		BarcodedProduct p = new BarcodedProduct(bc, desc, price, weight);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(bc, p);
	}

	public ArrayList<JButton> productButtons;
	
	public Product selectedProduct = null;

	private static Barcode createBarcode(String bc) {
		Numeral[] bcNums = new Numeral[bc.length()];

		for (int i = 0; i < bc.length(); i++)
			bcNums[i] = Numeral.valueOf(Byte.parseByte(bc.substring(i, i + 1)));

		return new Barcode(bcNums);
	}

	/**
	 * Creates new form ProductCatalogGUI
	 */
	public ProductCatalogGUI() { // TODO Probably will need to link up the software
		populateDatabases();

		initComponents();

		productButtons = new ArrayList<>();

		for (BarcodedProduct prod : ProductDatabases.BARCODED_PRODUCT_DATABASE.values())
			createItemButton(prod, prod.getDescription(), prod.getPrice());

		for (PLUCodedProduct prod : ProductDatabases.PLU_PRODUCT_DATABASE.values())
			createItemButton(prod, prod.getDescription(), prod.getPrice());
	}

	// Creates a JButton for a given product that will be used to select this
	// product.
	private void createItemButton(Product prod, String desc, BigDecimal price) {
		// Can't believe you need to use HMTL+CSS for this
		JButton button = new JButton("<html><div text-align:center>" + desc + "<br/>$" + price + "</div></html>");

		// Make somewhat thicker buttons
		button.setFont(new java.awt.Font("Tahoma", 0, 18));
		button.setPreferredSize(new Dimension(32, 100));

		// Attach the product to this button, so when the button is pressed we select
		// it.
		button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectProduct(prod);
			}
		});

		productButtons.add(button);
		jPanelProducts.add(button);
	}

	/**
	 * Selects the given product.
	 * 
	 * @param prod Product the customer selects.
	 */
	private void selectProduct(Product prod) {
		System.out.println("called");
		selectedProduct = prod;
		// TODO: Go back to scanning GUI
		// TODO: Need to let the software know what needs to be put into the bagging
		// area.
	}

	private void jButtonGoBackActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO Go back to scanning GUI
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jPanelProducts = new javax.swing.JPanel();
		jButtonGoBack = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);

		jPanelProducts.setLayout(new java.awt.GridLayout(0, 4, 32, 32));
		jScrollPane1.setViewportView(jPanelProducts);

		jButtonGoBack.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jButtonGoBack.setText("Go Back");
		jButtonGoBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonGoBackActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap(53, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 797,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonGoBack))
						.addContainerGap(43, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 537,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jButtonGoBack)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ProductCatalogGUI().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify
	private javax.swing.JButton jButtonGoBack;
	private javax.swing.JPanel jPanelProducts;
	private javax.swing.JScrollPane jScrollPane1;
	// End of variables declaration
}
