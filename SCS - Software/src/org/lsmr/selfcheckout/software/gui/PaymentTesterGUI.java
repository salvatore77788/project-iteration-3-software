package org.lsmr.selfcheckout.software.gui;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.external.CardIssuer;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

/**
 * Utility/demo GUI for testing all payment options: credit, debit, gift, coins, banknotes, as well as swiping
 * a membership card.
 */
public class PaymentTesterGUI extends javax.swing.JFrame {

	private SelfCheckoutStation station;
	private CardIssuer cardIssuer;
	private Card creditCard;
	private Card debitCard;
	private Card giftCard;
	private Card membershipCard;
	private Coin[] coins;
	private Banknote[] banknotes;
	private SelfCheckoutStationSoftware scss;
	
    /**
     * Creates new form PaymentTesterGUI
     */
    public PaymentTesterGUI(SelfCheckoutStationSoftware software) {
        initComponents();
        scss = software;
        
        station = software.scs;
        cardIssuer = software.cardSoftware.cardIssuer;
        
        Currency c = Currency.getInstance(Locale.CANADA);
        
        // Create each card type
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.YEAR, 3);
        creditCard = createCard("credit", "123456789", "John Doe", "011", "1234", true, true, expiry, new BigDecimal("1000.00"));
        debitCard = createCard("debit", "987654321", "Doe John", "110", "4321", true, true, expiry, new BigDecimal("1000.00"));
        giftCard = createCard("gift", "1010101010", "Gift Man", "000", "0000", false, false, expiry, new BigDecimal("1000.00"));
        membershipCard = createCard("member", "192837465", "Member", "000", "0000", false, false, expiry, new BigDecimal("1000.00"));
        software.memberCardObserver.memberDatabase.members.put("192837465", "Member"); // Register the test membership card
        
        coins = new Coin[] {
        		new Coin(c, new BigDecimal("0.05")),
        		new Coin(c, new BigDecimal("0.10")),
        		new Coin(c, new BigDecimal("0.25")),
        		new Coin(c, new BigDecimal("1.00")),
        		new Coin(c, new BigDecimal("2.00")),
        };
        
        banknotes = new Banknote[] {
        	new Banknote(c, 5),	
        	new Banknote(c, 10),	
        	new Banknote(c, 20),	
        	new Banknote(c, 50),	
        	new Banknote(c, 100),	
        };
    }
    
    private Card createCard(String type, String number, String name, String ccv, String pin, boolean tap, boolean chip, Calendar expiry, BigDecimal amount) {
    	cardIssuer.addCardData(number, name, expiry, ccv, amount);
    	return new Card(type, number, name, ccv, pin, tap, chip);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jButtonSwipeCreditCard = new javax.swing.JButton();
        jButtonSwipeDebitCard = new javax.swing.JButton();
        jButtonInsertNickel = new javax.swing.JButton();
        jButtonTapCreditCard = new javax.swing.JButton();
        jButtonTapDebitCard = new javax.swing.JButton();
        jButtonInsertDime = new javax.swing.JButton();
        jButtonInsertCreditCard = new javax.swing.JButton();
        jButtonInsertDebitCard = new javax.swing.JButton();
        jButtonInsertQuarter = new javax.swing.JButton();
        jButtonSwipeGiftCard = new javax.swing.JButton();
        jButtonSwipeMembershipCard = new javax.swing.JButton();
        jButtonInsertLoonie = new javax.swing.JButton();
        jButtonInsert5 = new javax.swing.JButton();
        jButtonInsert20 = new javax.swing.JButton();
        jButtonInsertToonie = new javax.swing.JButton();
        jButtonInsert10 = new javax.swing.JButton();
        jButtonInsert50 = new javax.swing.JButton();
        jButtonInsert100 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(6, 0));

        jButtonSwipeCreditCard.setText("Swipe Credit Card");
        jButtonSwipeCreditCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSwipeCreditCardActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSwipeCreditCard);

        jButtonSwipeDebitCard.setText("Swipe Debit Card");
        jButtonSwipeDebitCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSwipeDebitCardActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSwipeDebitCard);

        jButtonInsertNickel.setText("Insert Nickel");
        jButtonInsertNickel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertNickelActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsertNickel);

        jButtonTapCreditCard.setText("Tap Credit Card");
        jButtonTapCreditCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTapCreditCardActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonTapCreditCard);

        jButtonTapDebitCard.setText("Tap Debit Card");
        jButtonTapDebitCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTapDebitCardActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonTapDebitCard);

        jButtonInsertDime.setText("Insert Dime");
        jButtonInsertDime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertDimeActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsertDime);

        jButtonInsertCreditCard.setText("Insert Credit Card");
        jButtonInsertCreditCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertCreditCardActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsertCreditCard);

        jButtonInsertDebitCard.setText("Insert Debit Card");
        jButtonInsertDebitCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertDebitCardActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsertDebitCard);

        jButtonInsertQuarter.setText("Insert Quarter");
        jButtonInsertQuarter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertQuarterActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsertQuarter);

        jButtonSwipeGiftCard.setText("Swipe Gift Card");
        jButtonSwipeGiftCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSwipeGiftCardActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSwipeGiftCard);

        jButtonSwipeMembershipCard.setText("Swipe Membership Card");
        jButtonSwipeMembershipCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSwipeMembershipCardActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSwipeMembershipCard);

        jButtonInsertLoonie.setText("Insert Loonie");
        jButtonInsertLoonie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertLoonieActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsertLoonie);

        jButtonInsert5.setText("Insert $5");
        jButtonInsert5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsert5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsert5);

        jButtonInsert20.setText("Insert $20");
        jButtonInsert20.setToolTipText("");
        jButtonInsert20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsert20ActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsert20);

        jButtonInsertToonie.setText("Insert Twoonie");
        jButtonInsertToonie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertToonieActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsertToonie);

        jButtonInsert10.setText("Insert $10");
        jButtonInsert10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsert10ActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsert10);

        jButtonInsert50.setText("Insert $50");
        jButtonInsert50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsert50ActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsert50);

        jButtonInsert100.setText("Insert $100");
        jButtonInsert100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsert100ActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonInsert100);

        pack();
    }// </editor-fold>                        

    private void jButtonSwipeCreditCardActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        try {
			station.cardReader.swipe(creditCard);
			scss.addAmountPaid(scss.getAmountLeftToPay());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                      

    private void jButtonTapCreditCardActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        try {
			station.cardReader.tap(creditCard);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                    

    private void jButtonInsertCreditCardActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        try {
			station.cardReader.insert(creditCard, "1234");
			scss.addAmountPaid(scss.getAmountLeftToPay());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                       

    private void jButtonSwipeGiftCardActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        try {
			station.cardReader.swipe(giftCard);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                    

    private void jButtonInsert5ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        try {
			station.banknoteInput.accept(banknotes[0]);
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                              

    private void jButtonInsert10ActionPerformed(java.awt.event.ActionEvent evt) {                                                
    	try {
			station.banknoteInput.accept(banknotes[1]);
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                               

    private void jButtonSwipeDebitCardActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        try {
			station.cardReader.swipe(debitCard);
			scss.addAmountPaid(scss.getAmountLeftToPay());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                     

    private void jButtonTapDebitCardActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        try {
			station.cardReader.tap(debitCard);
			scss.addAmountPaid(scss.getAmountLeftToPay());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                   

    private void jButtonInsertDebitCardActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        try {
			station.cardReader.insert(debitCard, "4321");
			scss.addAmountPaid(scss.getAmountLeftToPay());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                      

    private void jButtonSwipeMembershipCardActionPerformed(java.awt.event.ActionEvent evt) {                                                           
        try {
			station.cardReader.swipe(membershipCard);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                          

    private void jButtonInsert20ActionPerformed(java.awt.event.ActionEvent evt) {                                                
    	try {
			station.banknoteInput.accept(banknotes[2]);
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                               

    private void jButtonInsert50ActionPerformed(java.awt.event.ActionEvent evt) {                                                
    	try {
			station.banknoteInput.accept(banknotes[3]);
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                               

    private void jButtonInsertNickelActionPerformed(java.awt.event.ActionEvent evt) {                                                    
    	try {
			station.coinSlot.accept(coins[0]);
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                   

    private void jButtonInsertDimeActionPerformed(java.awt.event.ActionEvent evt) {                                                  
    	try {
			station.coinSlot.accept(coins[1]);
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                 

    private void jButtonInsertQuarterActionPerformed(java.awt.event.ActionEvent evt) {                                                     
    	try {
			station.coinSlot.accept(coins[2]);
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                    

    private void jButtonInsertLoonieActionPerformed(java.awt.event.ActionEvent evt) {                                                    
    	try {
			station.coinSlot.accept(coins[3]);
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                   

    private void jButtonInsertToonieActionPerformed(java.awt.event.ActionEvent evt) {                                                    
    	try {
			station.coinSlot.accept(coins[4]);
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                   

    private void jButtonInsert100ActionPerformed(java.awt.event.ActionEvent evt) {                                                 
    	try {
			station.banknoteInput.accept(banknotes[4]);
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButtonInsert10;
    private javax.swing.JButton jButtonInsert100;
    private javax.swing.JButton jButtonInsert20;
    private javax.swing.JButton jButtonInsert5;
    private javax.swing.JButton jButtonInsert50;
    private javax.swing.JButton jButtonInsertCreditCard;
    private javax.swing.JButton jButtonInsertDebitCard;
    private javax.swing.JButton jButtonInsertDime;
    private javax.swing.JButton jButtonInsertLoonie;
    private javax.swing.JButton jButtonInsertNickel;
    private javax.swing.JButton jButtonInsertQuarter;
    private javax.swing.JButton jButtonInsertToonie;
    private javax.swing.JButton jButtonSwipeCreditCard;
    private javax.swing.JButton jButtonSwipeDebitCard;
    private javax.swing.JButton jButtonSwipeGiftCard;
    private javax.swing.JButton jButtonSwipeMembershipCard;
    private javax.swing.JButton jButtonTapCreditCard;
    private javax.swing.JButton jButtonTapDebitCard;
    // End of variables declaration                   
}
