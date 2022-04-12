package org.lsmr.selfcheckout.software.gui;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.software.Data;
import org.lsmr.selfcheckout.software.LookupNoBarcode;


public class MainFrame extends javax.swing.JFrame
    {

    DefaultListModel model_plu;
    DefaultListModel model_search;
    LookupNoBarcode x;
    
    ArrayList <BarcodedProduct> barcoded_products;
    ArrayList <PLUCodedProduct> plu_products;
    Data p = Data.getInstance();

    
    public MainFrame ()
        {
        initComponents ();
        populateBarcodeDatabase();
        populatePluDatabase(); 
        //populateBarcodeDatabase();
        //populatePluDatabase();
        model_search = new DefaultListModel();
        model_plu     = new DefaultListModel();
        x = new LookupNoBarcode();
        
        //productsList.addListSelectionListener(
        //        new SharedListSelectionHandler())
        }
                         
    private void initComponents()
    {

        searchBar = new javax.swing.JTextField();
        searchBarLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        searchList = new javax.swing.JList<>();
        pluBarLabel = new javax.swing.JLabel();
        pluBar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        pluList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        searchBar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                searchBarActionPerformed(evt);
            }
        });

        searchBarLabel.setText("Search For Product..");

        jScrollPane1.setViewportView(searchList);

        pluBarLabel.setText("Enter PLU Code");

        pluBar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jTextField1ActionPerformed(evt);
            }
        });
        
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
        
        getText();
        }
        public void removeUpdate(DocumentEvent e) {
        getText();
        }
        public void insertUpdate(DocumentEvent e) {
        getText();
        }

        public void getText() {
        model_search.removeAllElements();
        
        System.out.println("inside get text");
        String noResults = "No results";
           String input = searchBar.getText();
           
           if (input.length()>0){
             //JOptionPane.showMessageDialog(null,
               // "Error: Please enter number bigger than 0", "Error Massage",
                //JOptionPane.ERROR_MESSAGE);
           
           
           barcoded_products = x.searchByNameBarcode(input); 
           if (barcoded_products.size() > 0)
               model_search.removeElement(noResults);
           else
               {
               model_search.addElement(noResults); 
               }
           
           for (int i = 0; i < barcoded_products.size(); i++)
               {
               //DefaultListModel model = (DefaultListModel) productsList.getModel();
               model_search.addElement(barcoded_products.get(i).getDescription());
               //System.out.println("Added " + products.get(i).getDescription());
               
               }
           
           plu_products = x.searchByNamePlu(input); 
           if (plu_products.size() > 0)
               model_search.removeElement(noResults);
           else
               {
               model_search.addElement(noResults); 
               }
           
           for (int i = 0; i < plu_products.size(); i++)
               {
               //DefaultListModel model = (DefaultListModel) productsList.getModel();
               model_search.addElement(plu_products.get(i).getDescription());
               //System.out.println("Added " + products.get(i).getDescription());
               
               }
           
           }

           searchList.setModel(model_search);
        }
      });
        
        
        
        pluBar.getDocument().addDocumentListener(new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
        
        getText();
        }
        public void removeUpdate(DocumentEvent e) {
        getText();
        }
        public void insertUpdate(DocumentEvent e) {
        getText();
        }

        public void getText() {
        model_plu.removeAllElements();
        
        //System.out.println("inside get text");
        String noResults = "No results";
           String input = pluBar.getText();
           
           if (input.length()>=4 && input.length() <= 5){
             //JOptionPane.showMessageDialog(null,
               // "Error: Please enter number bigger than 0", "Error Massage",
                //JOptionPane.ERROR_MESSAGE);
           
           plu_products = x.searchByPlu(input); 
           if (plu_products.size() > 0)
               model_plu.removeElement(noResults);
           else
               {
               model_plu.addElement(noResults); 
               }
           
           for (int i = 0; i < plu_products.size(); i++)
               {
               //DefaultListModel model = (DefaultListModel) productsList.getModel();
               model_plu.addElement(plu_products.get(i).getDescription());
               //System.out.println("Added " + products.get(i).getDescription());
               }
           
           }

           pluList.setModel(model_plu);
        }
      });
        
        
        searchList.addListSelectionListener(new ListSelectionListener() {

        @Override
        public void valueChanged (ListSelectionEvent e)
            {
            int selectedIndex = searchList.getSelectedIndex();
            //System.out.println("in value changed, selected index is: " + selectedIndex);
            if (!searchList.getValueIsAdjusting())
                {
                if (selectedIndex < 0)
                    return;
                // TODO Auto-generated method stub
                
                if (selectedIndex > (barcoded_products.size() - 1))
                    {
                    selectedIndex -= barcoded_products.size();
                    PLUCodedProduct selected = plu_products.get(selectedIndex);
                    ArrayList <PLUCodedProduct> scannedItems = p.getPluScanned();
                    scannedItems.add(selected);
                    
                    
                    
                     //for (int i = 0; i < scannedItems.size(); i++)
                      // System.out.println("barcoded items: " + scannedItems.get(i).getDescription());
                    }
                else
                    {
                    BarcodedProduct selected = barcoded_products.get(selectedIndex);
                    //System.out.println("selected: " + selected.getDescription());
                    //ADD TO ITEMS SCANNED
                  
                    ArrayList <BarcodedProduct> scannedItems = p.getBarcodeScanned();
                    scannedItems.add(selected);
                    
                    //for (int i = 0; i < scannedItems.size(); i++)
                        //System.out.println("PLU coded items: " + scannedItems.get(i).getDescription());
                    }
                
               // for (int i = 0; i < scannedItems.size(); i++)
                 //   System.out.println("scanned items array: " + scannedItems.get(i).getDescription());
                }
            }
        });
        
        pluList.addListSelectionListener(new ListSelectionListener() {

        @Override
        public void valueChanged (ListSelectionEvent e)
            {
            int selectedIndex = pluList.getSelectedIndex();
            //System.out.println("in value changed, selected index is: " + selectedIndex);
            if (!pluList.getValueIsAdjusting())
                {
                if (selectedIndex < 0)
                    return;
                // TODO Auto-generated method stub
                PLUCodedProduct selected = plu_products.get(selectedIndex);
                //System.out.println("selected: " + selected.getDescription());
                //ADD TO ITEMS SCANNED
                
                JLabel label = new JLabel("Added " + selected.getDescription() + " to cart");
                Dimension size = label.getPreferredSize();
                label.setBounds(400, 400, size.width, size.height);
               
                ArrayList <PLUCodedProduct> scannedPluItems = p.getPluScanned();
                scannedPluItems.add(selected);
                
                //for (int i = 0; i < scannedPluItems.size(); i++)
                    //System.out.println("scanned items array: " + scannedPluItems.get(i).getDescription());
                }
            }
        });
        
        

        jScrollPane2.setViewportView(pluList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(searchBarLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchBar, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pluBarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pluBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(53, 53, 53))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchBarLabel)
                    .addComponent(pluBarLabel))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pluBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(107, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt)                                          
    {                                              
        //System.out.println("Hi");
    }                                         

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt)                                            
    {                                                
        // TODO add your handling code here:
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main (String args[])
        {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
            {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels ())
                {
                if ("Nimbus".equals (info.getName ()))
                    {
                    javax.swing.UIManager.setLookAndFeel (info.getClassName ());
                    break;
                    }
                }
            }
        catch (ClassNotFoundException ex)
            {
            java.util.logging.Logger.getLogger (MainFrame.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
            }
        catch (InstantiationException ex)
            {
            java.util.logging.Logger.getLogger (MainFrame.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
            }
        catch (IllegalAccessException ex)
            {
            java.util.logging.Logger.getLogger (MainFrame.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
            }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
            {
            java.util.logging.Logger.getLogger (MainFrame.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
            }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater (new Runnable ()
            {
            public void run ()
                {
                new MainFrame ().setVisible (true);
                }
            });
        }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel pluBarLabel;
    private javax.swing.JList<String> pluList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField pluBar;
    private javax.swing.JList<String> searchList;
    private javax.swing.JTextField searchBar;
    private javax.swing.JLabel searchBarLabel;
    private javax.swing.JLabel addedPlu;
    private javax.swing.JLabel addedSearch;
    // End of variables declaration   
    
    public void populateBarcodeDatabase()
        {
        Numeral[] n = {Numeral.one, Numeral.two, Numeral.three, Numeral.four, Numeral.five, Numeral.six, Numeral.seven, Numeral.eight, Numeral.nine};
        for(int i = 1; i < 3; i++) {
            Numeral [] numerals = {n[i-1]};
            Barcode barcode = new Barcode (numerals);
            ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, new BarcodedProduct(barcode, ("itema"+i), new BigDecimal(i*10), i*100));
            
        }
        Numeral [] numerals = {n[4]};
        Barcode barcode = new Barcode (numerals);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, new BarcodedProduct(barcode, ("barcoded a"), new BigDecimal(10), 100));
        }
    
    public void populatePluDatabase()
        {
        Numeral[] n = {Numeral.one, Numeral.two, Numeral.three, Numeral.four, Numeral.five, Numeral.six, Numeral.seven, Numeral.eight, Numeral.nine};
            ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode ("0001"), new PLUCodedProduct(new PriceLookupCode ("0001"), "aa", new BigDecimal(10)));
            ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode ("0002"), new PLUCodedProduct(new PriceLookupCode ("0002"), "ab", new BigDecimal(10)));
            ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode ("0003"), new PLUCodedProduct(new PriceLookupCode ("0003"), "c", new BigDecimal(10)));
            ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode ("0004"), new PLUCodedProduct(new PriceLookupCode ("0004"), "d", new BigDecimal(10)));
        }  
     
    
    }
