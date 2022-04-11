package org.lsmr.selfcheckout.software.gui;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.Locale;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.software.AttendantActions;
import org.lsmr.selfcheckout.software.AvailableFunds;
import org.lsmr.selfcheckout.software.ItemInfo;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;


public class AttendantGui {
	private static final Color STATUS_GOOD_COLOR = new Color(51, 153, 0);
	private static final Color STATUS_BAD_COLOR = new Color(204, 0, 0);
	private static final Color STATUS_OFF_COLOR = Color.LIGHT_GRAY;
	
	// Test station software class
	// This should be replaced with SelfCheckoutStationSoftware when it is ready
	/*private class SCSSoftware {
		public SelfCheckoutStation station;
		
		public ArrayList<ItemInfo> itemsScanned;
		public int inkLeft;
		public int paperLeft;
		
		public Boolean isBlocked;
		public Boolean isShutdown;
		
		public SCSStatus status;
		
		public AvailableFunds funds;
		
		public SCSSoftware(SelfCheckoutStation station) {
			this.station = station;
			
			itemsScanned = new ArrayList<ItemInfo>();
			
			inkLeft = 0;
			paperLeft = 0;
			
			isBlocked = false;
			isShutdown = true;
			
			funds = new AvailableFunds(station);
			
			status = SCSStatus.OFF;
		}
		
		public int getPercentageInkLeft() {
			return toPercent(inkLeft, ReceiptPrinter.MAXIMUM_INK);
		}
		
		public int getPercentagePaperLeft() {
			return toPercent(paperLeft, ReceiptPrinter.MAXIMUM_PAPER);
		}
		
		public void startUp() {
			funds.attachAll();
			isShutdown = false;
			isBlocked = true; // Start blocked?
			status = SCSStatus.GOOD;
		}
		
		public void shutDown() {
			funds.detachAll();
			isShutdown = true;
			status = SCSStatus.OFF;
		}
	}*/
	

	// For now, just use a testing Attendant station instance
	private SupervisionStation superStation;
	private JFrame frame;
	
	private SelfCheckoutStationSoftware[] stationSoftwares;
	private SelfCheckoutStationSoftware currentSoftware;
	
	// List models
	DefaultListModel<String> shoppingCartLM;
	DefaultListModel<String> coinDispLM;
	DefaultListModel<String> bnDispLM;
	
	int[] bnDenoms = new int[] {5, 10, 20, 50, 100};
	BigDecimal[] coinDenoms = new BigDecimal[] {new BigDecimal("0.05"), new BigDecimal("0.10"), new BigDecimal("0.25"), 
			new BigDecimal("1.00"), new BigDecimal("2.00")};
	Currency currency = Currency.getInstance(Locale.CANADA);
	
    /**
     * Creates new form TestFrame
     */
    public AttendantGui() {
    	superStation = new SupervisionStation();
    	frame = superStation.screen.getFrame();
    	
        initComponents();
        
        initGUI();
        
        // Initialize 
        frame.setLocation(1500, 300);
        frame.setVisible(true);
    }

    private void initGUI() {
    	// TODO: Remove testing code
    	
    	// Custom renderer for the station list
    	jListStations.setCellRenderer(new DefaultListCellRenderer() {
    		@Override
    		public Component getListCellRendererComponent(JList list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
    			Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    			
    			SelfCheckoutStationSoftware software = stationSoftwares[index];
    			String text = (String)value;
    			
    			setText(text);
    			if(software.isShutdown)
    				setForeground(Color.LIGHT_GRAY);
    			else {
    				setForeground(Color.BLACK);
    				if(software.status == SelfCheckoutStationSoftware.SCSStatus.BAD)
        				text += "(!)";
    				}
    			
    			return comp;
    		}
    	});
    	
    	// Create some test stations
    	stationSoftwares = new SelfCheckoutStationSoftware[3];
    	for(int i = 0; i < 3; i++) {
    		SelfCheckoutStation s = new SelfCheckoutStation(currency, bnDenoms, coinDenoms, 1000, 1);
    		superStation.add(s);
    		try {
				stationSoftwares[i] = new SelfCheckoutStationSoftware(s);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	// Set up stations list
		int stationCount = superStation.supervisedStationCount();
		
		String[] stationNames = new String[stationCount];
		for(int i = 0; i < stationCount; i++)
			stationNames[i] = "Self-Checkout Station " + i;
		jListStations.setListData(stationNames);
		
		// Set up coin denomination list
		coinDispLM = new DefaultListModel<>();
		jListCoinDispensers.setModel(coinDispLM);
		
		// Set up banknote denomination list
		bnDispLM = new DefaultListModel<String>();
		jListBanknoteDispensers.setModel(bnDispLM);
		
		// Set up shopping cart list
		shoppingCartLM = new DefaultListModel<String>(); 
		jListShoppingCart.setModel(shoppingCartLM);
		
		setStation();
	}
    
    private void setStation() {
    	int idx = jListStations.getSelectedIndex();
    	
    	currentSoftware = idx == -1 ? null : stationSoftwares[idx];
    	
    	// Title
    	jLabelStationName.setText("Self-Checkout System " + (idx == -1 ? "" : idx));
    	
    	// Data here needs to be taken from the actual software instead of the test software
    	// Since none of it is actually implemented on this branch, it'll have to be done later
    	
    	// Set up buttons
    	boolean isStationNull = currentSoftware == null;
    	
    	jButtonBlockStation.setEnabled(!isStationNull && !currentSoftware.isBlocked && !currentSoftware.isShutdown);
		jButtonUnblockStation.setEnabled(!isStationNull && currentSoftware.isBlocked && !currentSoftware.isShutdown);
		jButtonShutdown.setEnabled(!isStationNull && !currentSoftware.isShutdown);
		jButtonStartUp.setEnabled(!isStationNull && currentSoftware.isShutdown);
    	jButtonRefillBanknoteDispenser.setEnabled(!isStationNull && !currentSoftware.isShutdown && currentSoftware.isBlocked);
    	jButtonRefillCoinDispenser.setEnabled(!isStationNull && !currentSoftware.isShutdown && currentSoftware.isBlocked);
    	jButtonRefillInk.setEnabled(!isStationNull && !currentSoftware.isShutdown && currentSoftware.isBlocked);
    	jButtonRefillPaper.setEnabled(!isStationNull && !currentSoftware.isShutdown && currentSoftware.isBlocked);
    	jButtonRefresh.setEnabled(!isStationNull && !currentSoftware.isShutdown);
    	jButtonUnloadBanknoteStorage.setEnabled(!isStationNull && !currentSoftware.isShutdown && currentSoftware.isBlocked);
    	jButtonUnloadCoinStorage.setEnabled(!isStationNull && !currentSoftware.isShutdown && currentSoftware.isBlocked);
    	
    	// Set up coin denomination list
    	coinDispLM.clear();
		for(int i = 0; i < coinDenoms.length; i++)
			coinDispLM.addElement("$" + coinDenoms[i].toString() + ": " + (isStationNull ? "--" : getCoinPercentage(coinDenoms[i]) + "%"));
    	
		// Set up banknote denomination list
    	bnDispLM.clear();
    	for(int i = 0; i < bnDenoms.length; i++)
    		bnDispLM.addElement("$" + bnDenoms[i] + ": " + (isStationNull ? "--" : getBanknotePercentage(bnDenoms[i]) + "%"));
    	
    	// Storage labels
    	jLabelCoinStorage.setText("Coin Storage: " + (isStationNull ? "Unknown" : (currentSoftware.funds.getIsCoinStorageFull() ? "Full" : "Not Full")));
    	jLabelBanknoteStorage.setText("Banknote Storage: " + (isStationNull ? "Unknown" : (currentSoftware.funds.getIsBanknoteStorageFull() ? "Full     " : "Not Full")));
    	
    	// Status
    	jLabelStatusCode.setText(isStationNull ? "--" : currentSoftware.status.toString());
    	jLabelStatusCode.setForeground(isStationNull ? Color.BLACK : getStatusColor(currentSoftware.status));
    	jLabelInk.setText("Ink: " + (isStationNull ? "--" : currentSoftware.getPercentageInkLeft() + "%"));
    	jLabelPaper.setText("Paper: " + (isStationNull ? "--" : currentSoftware.getPercentagePaperLeft() + "%"));
    	
    	// Shopping cart
    	shoppingCartLM.clear();
    	
    	if(!isStationNull) {
    		for(int i = 0; i < currentSoftware.itemsScanned.size(); i++) {
    			ItemInfo info = currentSoftware.itemsScanned.get(i);
    			
    			shoppingCartLM.addElement(info.toString());
    		}
    	}
    }
    
    private int getCoinPercentage(BigDecimal coinDenom) {
		int coinCount = currentSoftware.funds.getCoinCount(coinDenom);
		return toPercent(coinCount, SelfCheckoutStation.COIN_DISPENSER_CAPACITY);
	}
    
    private int getBanknotePercentage(int bnDenom) {
    	int bnCount = currentSoftware.funds.getBanknoteCount(bnDenom);
    	return toPercent(bnCount, SelfCheckoutStation.BANKNOTE_DISPENSER_CAPACITY);
    }
    
    private int toPercent(double num, double denom) {
    	return (int)(num/denom*100);
    }

	private Color getStatusColor(SelfCheckoutStationSoftware.SCSStatus status) {
    	switch(status) {
    	case GOOD:
    		return STATUS_GOOD_COLOR;
    	case BAD:
    		return STATUS_BAD_COLOR;
    	default:
    		return STATUS_OFF_COLOR;
    	}
    }

	/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPopupMenuShoppingCart = new javax.swing.JPopupMenu();
        jMenuItemSCAddItem = new javax.swing.JMenuItem();
        jMenuItemSCRemoveItem = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListStations = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jLabelStationName = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelStatus = new javax.swing.JLabel();
        jLabelStatusCode = new javax.swing.JLabel();
        jLabelInk = new javax.swing.JLabel();
        jLabelPaper = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButtonUnblockStation = new javax.swing.JButton();
        jButtonBlockStation = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListShoppingCart = new javax.swing.JList<>();
        jLabelSC = new javax.swing.JLabel();
        jButtonShutdown = new javax.swing.JButton();
        jButtonStartUp = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListCoinDispensers = new javax.swing.JList<>();
        jLabelCD = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListBanknoteDispensers = new javax.swing.JList<>();
        jLabelBD = new javax.swing.JLabel();
        jButtonRefillCoinDispenser = new javax.swing.JButton();
        jButtonRefillBanknoteDispenser = new javax.swing.JButton();
        jLabelCoinStorage = new javax.swing.JLabel();
        jLabelBanknoteStorage = new javax.swing.JLabel();
        jButtonUnloadCoinStorage = new javax.swing.JButton();
        jButtonUnloadBanknoteStorage = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jButtonRefresh = new javax.swing.JButton();
        jButtonRefillInk = new javax.swing.JButton();
        jButtonRefillPaper = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItemMBStartUpAll = new javax.swing.JMenuItem();
        jMenuItemMBShutDownAll = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItemMBLogout = new javax.swing.JMenuItem();
        jMenuItemMBQuit = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        jMenuItemSCAddItem.setText("Add Item");
        jMenuItemSCAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSCAddItemActionPerformed(evt);
            }
        });
        jPopupMenuShoppingCart.add(jMenuItemSCAddItem);

        jMenuItemSCRemoveItem.setText("Remove Item");
        jMenuItemSCRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSCRemoveItemActionPerformed(evt);
            }
        });
        jPopupMenuShoppingCart.add(jMenuItemSCRemoveItem);

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jListStations.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListStations.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListStations.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListStationsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListStations);

        jLabelStationName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelStationName.setText("Self-Checkout Station");

        jLabelStatus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelStatus.setText("Status:");

        jLabelStatusCode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelStatusCode.setForeground(new java.awt.Color(51, 153, 0));
        jLabelStatusCode.setText("Good");

        jLabelInk.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelInk.setText("Ink: 100%");

        jLabelPaper.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPaper.setText("Paper: 100%");

        jButtonUnblockStation.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonUnblockStation.setText("UNBLOCK");
        jButtonUnblockStation.setEnabled(false);
        jButtonUnblockStation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnblockStationActionPerformed(evt);
            }
        });

        jButtonBlockStation.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonBlockStation.setText("BLOCK");
        jButtonBlockStation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBlockStationActionPerformed(evt);
            }
        });

        jListShoppingCart.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListShoppingCart.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListShoppingCart.setComponentPopupMenu(jPopupMenuShoppingCart);
        jScrollPane2.setViewportView(jListShoppingCart);

        jLabelSC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelSC.setText("Shopping Cart");

        jButtonShutdown.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonShutdown.setText("Shutdown");
        jButtonShutdown.setEnabled(false);
        jButtonShutdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShutdownActionPerformed(evt);
            }
        });

        jButtonStartUp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonStartUp.setText("Start Up");
        jButtonStartUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartUpActionPerformed(evt);
            }
        });

        jListCoinDispensers.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListCoinDispensers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(jListCoinDispensers);

        jLabelCD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCD.setText("Coin Dispensers");

        jListBanknoteDispensers.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListBanknoteDispensers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(jListBanknoteDispensers);

        jLabelBD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelBD.setText("Banknote Dispensers");

        jButtonRefillCoinDispenser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonRefillCoinDispenser.setText("Refill");
        jButtonRefillCoinDispenser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefillCoinDispenserActionPerformed(evt);
            }
        });

        jButtonRefillBanknoteDispenser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonRefillBanknoteDispenser.setText("Refill");
        jButtonRefillBanknoteDispenser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefillBanknoteDispenserActionPerformed(evt);
            }
        });

        jLabelCoinStorage.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelCoinStorage.setText("Coin Storage: Not Full");

        jLabelBanknoteStorage.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelBanknoteStorage.setText("Banknote Storage: Not Full");

        jButtonUnloadCoinStorage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonUnloadCoinStorage.setText("Unload");
        jButtonUnloadCoinStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnloadCoinStorageActionPerformed(evt);
            }
        });

        jButtonUnloadBanknoteStorage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonUnloadBanknoteStorage.setText("Unload");
        jButtonUnloadBanknoteStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnloadBanknoteStorageActionPerformed(evt);
            }
        });

        jButtonRefresh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonRefresh.setText("Refresh");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        jButtonRefillInk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonRefillInk.setText("Refill");
        jButtonRefillInk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefillInkActionPerformed(evt);
            }
        });

        jButtonRefillPaper.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonRefillPaper.setText("Refill");
        jButtonRefillPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefillPaperActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabelStationName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonBlockStation)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonUnblockStation))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelCD)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButtonStartUp)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonShutdown))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabelStatus)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelStatusCode)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabelInk)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonRefillInk))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabelPaper)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonRefillPaper)))
                                .addGap(218, 218, 218))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(jButtonRefillCoinDispenser, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(jButtonRefillBanknoteDispenser, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelBD)
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButtonRefresh))
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabelCoinStorage)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addComponent(jLabelBanknoteStorage)
                                                        .addGap(64, 64, 64)
                                                        .addComponent(jButtonUnloadBanknoteStorage))
                                                    .addComponent(jButtonUnloadCoinStorage))
                                                .addGap(12, 12, 12)))))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabelSC)
                                .addGap(59, 59, 59)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStationName)
                    .addComponent(jButtonUnblockStation)
                    .addComponent(jButtonBlockStation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStatus)
                    .addComponent(jLabelStatusCode)
                    .addComponent(jLabelSC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelInk)
                            .addComponent(jButtonRefillInk))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPaper)
                            .addComponent(jButtonRefillPaper))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCD)
                            .addComponent(jLabelBD))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4)
                            .addComponent(jScrollPane3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonRefillCoinDispenser)
                            .addComponent(jButtonRefillBanknoteDispenser))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCoinStorage)
                            .addComponent(jButtonUnloadCoinStorage))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelBanknoteStorage)
                            .addComponent(jButtonUnloadBanknoteStorage))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonShutdown)
                            .addComponent(jButtonStartUp)
                            .addComponent(jButtonRefresh)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu3.setText("File");

        jMenuItemMBStartUpAll.setText("Start Up All");
        jMenuItemMBStartUpAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMBStartUpAllActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemMBStartUpAll);

        jMenuItemMBShutDownAll.setText("Shut Down All");
        jMenuItemMBShutDownAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMBShutDownAllActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemMBShutDownAll);
        jMenu3.add(jSeparator4);

        jMenuItemMBLogout.setText("Logout");
        jMenuItemMBLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMBLogoutActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemMBLogout);
        
        jMenuItemMBQuit.setText("Quit");
        jMenuItemMBQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMBQuitActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemMBQuit);

        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        frame.setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        frame.pack();
    }// </editor-fold>                        

    private void jListStationsValueChanged(javax.swing.event.ListSelectionEvent evt) {                                           
        setStation();
    }                                          

    private void jButtonBlockStationActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // Block the current station
    	currentSoftware.isBlocked = true;
    	AttendantActions.attendantBlockStation(currentSoftware.scs);
    	setStation();
    }                                                   

    private void jButtonUnblockStationActionPerformed(java.awt.event.ActionEvent evt) {                                                      
    	// Unblock the current station
    	currentSoftware.isBlocked = false;
    	AttendantActions.attendantUnBlockStation(currentSoftware.scs);
    	setStation();
    }                                                     

    private void jButtonRefillCoinDispenserActionPerformed(java.awt.event.ActionEvent evt) {                                                           
        int idx = jListCoinDispensers.getSelectedIndex();
        
        if(idx >= 0) {
        	BigDecimal denom = coinDenoms[idx];
        	
        	int refillCount = SelfCheckoutStation.COIN_DISPENSER_CAPACITY - currentSoftware.funds.getCoinCount(denom);
        	AttendantActions.fillCoinDispenser(currentSoftware.scs, denom, Collections.nCopies(refillCount, new Coin(currency, denom)).toArray(new Coin[refillCount]));
        }
        
        setStation();
    }                                                          

    private void jButtonRefillBanknoteDispenserActionPerformed(java.awt.event.ActionEvent evt) {                                                               
        int idx = jListBanknoteDispensers.getSelectedIndex();
        
        if(idx >= 0) {
        	int denom = bnDenoms[idx];
        	
        	int refillCount = SelfCheckoutStation.BANKNOTE_DISPENSER_CAPACITY - currentSoftware.funds.getBanknoteCount(denom);
        	AttendantActions.fillBanknoteDispenser(currentSoftware.scs, denom, Collections.nCopies(refillCount, new Banknote(currency, denom)).toArray(new Banknote[refillCount]));
        }
        
        setStation();
    }                                                              

    private void jButtonUnloadBanknoteStorageActionPerformed(java.awt.event.ActionEvent evt) {                                                             
        AttendantActions.emptyBanknoteStorageUnit(currentSoftware.scs);
        setStation();
    }                                                            

    private void jButtonUnloadCoinStorageActionPerformed(java.awt.event.ActionEvent evt) {                                                         
    	AttendantActions.emptyCoinStorageUnit(currentSoftware.scs);
    	setStation();
    }                                                        

    private void jButtonStartUpActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // Start up the station and software
    	currentSoftware.startUp();
    	jListStations.updateUI();
    	setStation();
    }                                              

    private void jButtonShutdownActionPerformed(java.awt.event.ActionEvent evt) {                                                
    	// Shut down the station and software
    	currentSoftware.shutDown();
    	jListStations.updateUI();
    	setStation();
    	
    }                                               

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {                                               
        setStation();
    }                                              

    private void jButtonRefillInkActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        try {
			AttendantActions.fillInk(currentSoftware.rp, ReceiptPrinter.MAXIMUM_INK-currentSoftware.rp.getInkAmount());
			setStation();
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                

    private void jButtonRefillPaperActionPerformed(java.awt.event.ActionEvent evt) {                                                   
    	try {
			AttendantActions.fillInk(currentSoftware.rp, ReceiptPrinter.MAXIMUM_PAPER-currentSoftware.rp.getPaperAmount());
			setStation();
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }                                                  

    private void jMenuItemMBStartUpAllActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        for(int i = 0; i < stationSoftwares.length; i++) {
        	SelfCheckoutStationSoftware sw = stationSoftwares[i];
        	if(sw.isShutdown)
        		sw.startUp();
        }
        jListStations.updateUI();
        setStation();
    }                                                     

    private void jMenuItemMBShutDownAllActionPerformed(java.awt.event.ActionEvent evt) {                                                       
    	for(int i = 0; i < stationSoftwares.length; i++) {
    		SelfCheckoutStationSoftware sw = stationSoftwares[i];
        	if(!sw.isShutdown)
        		sw.shutDown();
        }
    	jListStations.updateUI();
    	setStation();
    }                                                      

    private void jMenuItemMBLogoutActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }
    
    private void jMenuItemMBQuitActionPerformed(java.awt.event.ActionEvent evt) {
    	// Completely end the application -- For demo use only really
    	frame.setVisible(false);
    	frame.dispose();
    	System.exit(0);
    }

    private void jMenuItemSCRemoveItemActionPerformed(java.awt.event.ActionEvent evt) {  
    	int idx = jListShoppingCart.getSelectedIndex();
    	if(idx != -1) {
			AttendantActions.removeItem(currentSoftware, idx);
			setStation();
    	}
    }
    
    private void jMenuItemSCAddItemActionPerformed(java.awt.event.ActionEvent evt) {
    	// TODO add your handling code here:
    	// Possibly use a modal/pop up window for searching by PLU/name and linking to the PLU catalog
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        new AttendantGui();
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButtonBlockStation;
    private javax.swing.JButton jButtonRefillBanknoteDispenser;
    private javax.swing.JButton jButtonRefillCoinDispenser;
    private javax.swing.JButton jButtonRefillInk;
    private javax.swing.JButton jButtonRefillPaper;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonShutdown;
    private javax.swing.JButton jButtonStartUp;
    private javax.swing.JButton jButtonUnblockStation;
    private javax.swing.JButton jButtonUnloadBanknoteStorage;
    private javax.swing.JButton jButtonUnloadCoinStorage;
    private javax.swing.JLabel jLabelBD;
    private javax.swing.JLabel jLabelBanknoteStorage;
    private javax.swing.JLabel jLabelCD;
    private javax.swing.JLabel jLabelCoinStorage;
    private javax.swing.JLabel jLabelInk;
    private javax.swing.JLabel jLabelPaper;
    private javax.swing.JLabel jLabelSC;
    private javax.swing.JLabel jLabelStationName;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelStatusCode;
    private javax.swing.JList<String> jListBanknoteDispensers;
    private javax.swing.JList<String> jListCoinDispensers;
    private javax.swing.JList<String> jListShoppingCart;
    private javax.swing.JList<String> jListStations;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItemMBQuit;
    private javax.swing.JMenuItem jMenuItemMBLogout;
    private javax.swing.JMenuItem jMenuItemMBShutDownAll;
    private javax.swing.JMenuItem jMenuItemMBStartUpAll;
    private javax.swing.JMenuItem jMenuItemSCAddItem;
    private javax.swing.JMenuItem jMenuItemSCRemoveItem;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenuShoppingCart;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    // End of variables declaration                   
}
