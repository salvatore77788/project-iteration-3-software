package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.software.gui.*;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchProduct
    {

    private JFrame frame;
    private JPanel panel;
    private JTextField searchBar;
    private JButton search;

    /**
     * Launch the application.
     */
    public static void main (String[] args)
        {
 
       // MainFrame.main(args);
        //EventQueue.invokeLater(new Runnable()
        //     {
        //    public void run ()
        //        {
        //        try
        //            {
        //            SearchProduct window = new SearchProduct();
        //            window.frame.setVisible(true);
        //            } catch (Exception e)
        //           {
        //            e.printStackTrace();
        //            }
        //        }
        //    });
        }

    /**
     * Create the application.
     */
    public SearchProduct()
        {
        //initialize();
        }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize ()
        {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,2));
        
        search = new JButton("Search For Product");
        
        panel.add(search);
        panel.add(searchBar);
        
        frame.getContentPane().add(panel);
        }

    }
