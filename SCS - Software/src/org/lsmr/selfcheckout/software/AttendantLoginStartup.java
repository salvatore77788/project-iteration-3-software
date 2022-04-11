package org.lsmr.selfcheckout.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.lsmr.selfcheckout.devices.observers.KeyboardObserver;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;
import org.lsmr.selfcheckout.devices.observers.TouchScreenObserver;
import org.lsmr.selfcheckout.software.gui.FailedLogin;
import org.lsmr.selfcheckout.software.gui.Login;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteSlotObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;
import org.lsmr.selfcheckout.devices.observers.CoinDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.CoinSlotObserver;
import org.lsmr.selfcheckout.devices.observers.CoinTrayObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.CoinStorageUnitObserver;

public class AttendantLoginStartup {
	private boolean isLoggedIn = false;
	private SupervisionStation superStation;
	
	public ArrayList<SupervisionStation> allSuperStations = new ArrayList<SupervisionStation>();
	
	private Map<SupervisionStation, ArrayList<TouchScreenObserver>> allSuperTouchScreenObservers = 
			new HashMap<SupervisionStation, ArrayList<TouchScreenObserver>>(); 
	
	private Map<SupervisionStation, ArrayList<KeyboardObserver>> allSuperKeyboardObservers = 
			new HashMap<SupervisionStation, ArrayList<KeyboardObserver>>(); 
	
	
	
	private ArrayList<SelfCheckoutStation> allSelfStations = new ArrayList<SelfCheckoutStation>();
	
	private Map<SelfCheckoutStation, ArrayList<ElectronicScaleObserver>> allSelfBaggingAreaObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<ElectronicScaleObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<ElectronicScaleObserver>> allSelfScanningAreaObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<ElectronicScaleObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<TouchScreenObserver>> allSelfScreenObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<TouchScreenObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<ReceiptPrinterObserver>> allSelfPrinterObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<ReceiptPrinterObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<CardReaderObserver>> allSelfCardReaderObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<CardReaderObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<BarcodeScannerObserver>> allSelfMainScannerObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<BarcodeScannerObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<BarcodeScannerObserver>> allSelfHandheldScannerObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<BarcodeScannerObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<BanknoteSlotObserver>> allSelfBanknoteInputObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<BanknoteSlotObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<BanknoteSlotObserver>> allSelfBanknoteOutputObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<BanknoteSlotObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<BanknoteValidatorObserver>> allSelfBanknoteValidatorObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<BanknoteValidatorObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<BanknoteStorageUnitObserver>> allSelfBanknoteStorageObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<BanknoteStorageUnitObserver>>();
	
	private Map<SelfCheckoutStation, HashMap<Integer, ArrayList<BanknoteDispenserObserver>>> allSelfBanknoteDispenserObservers =
			new HashMap<SelfCheckoutStation, HashMap<Integer, ArrayList<BanknoteDispenserObserver>>>();
	
	private Map<SelfCheckoutStation, ArrayList<CoinSlotObserver>> allSelfCoinSlotObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<CoinSlotObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<CoinValidatorObserver>> allSelfCoinValidatorObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<CoinValidatorObserver>>();
	
	private Map<SelfCheckoutStation, ArrayList<CoinStorageUnitObserver>> allSelfCoinStorageObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<CoinStorageUnitObserver>>();
	
	private Map<SelfCheckoutStation, HashMap<BigDecimal, ArrayList<CoinDispenserObserver>>> allSelfCoinDispenserObservers = 
			new HashMap<SelfCheckoutStation, HashMap<BigDecimal, ArrayList<CoinDispenserObserver>>>();
	
	private Map<SelfCheckoutStation, ArrayList<CoinTrayObserver>> allSelfCoinTrayObservers = 
			new HashMap<SelfCheckoutStation, ArrayList<CoinTrayObserver>>();
	
	
	 public Login loginFrame = new Login();
    
    // add a supervision station to allSuperStationObservers
    public void addStation(SupervisionStation ss, 
    		ArrayList<TouchScreenObserver> superTouchScreenObservers, 
    		ArrayList<KeyboardObserver> superKeyboardObservers) {
    	
		if (!isLoggedIn) return;
		if (allSuperStations.contains(ss)) return;
		
		allSuperStations.add(ss);
		allSuperTouchScreenObservers.put(ss, superTouchScreenObservers);
		allSuperKeyboardObservers.put(ss, superKeyboardObservers);
	}
    
    public void addStation(SelfCheckoutStation scs,
    		ArrayList<ElectronicScaleObserver> selfBaggingAreaObservers,
    		ArrayList<ElectronicScaleObserver> selfScanningAreaObservers,
    		ArrayList<TouchScreenObserver> selfScreenObservers,
    		ArrayList<ReceiptPrinterObserver> selfPrinterObservers,
    		ArrayList<CardReaderObserver> selfCardReaderObservers,
    		ArrayList<BarcodeScannerObserver> selfMainScannerObservers,
    		ArrayList<BarcodeScannerObserver> selfHandheldScannerObservers,
    		ArrayList<BanknoteSlotObserver> selfBanknoteInputObservers,
    		ArrayList<BanknoteSlotObserver> selfBanknoteOutputObservers,
    		ArrayList<BanknoteValidatorObserver> selfBanknoteValidatorObservers,
    		ArrayList<BanknoteStorageUnitObserver> selfBanknoteStorageObservers,
    		HashMap<Integer, ArrayList<BanknoteDispenserObserver>> selfBanknoteDispenserObservers,
    		ArrayList<CoinSlotObserver> selfCoinSlotObservers,
    		ArrayList<CoinValidatorObserver> selfCoinValidatorObservers,
    		ArrayList<CoinStorageUnitObserver> selfCoinStorageObservers,
    		HashMap<BigDecimal, ArrayList<CoinDispenserObserver>> selfCoinDispenserObservers,
    		ArrayList<CoinTrayObserver> selfCoinTrayObservers) {
    	
    	if (!isLoggedIn) return;
    	if (allSelfStations.contains(scs)) return;
    	
    	allSelfStations.add(scs);
    	allSelfBaggingAreaObservers.put(scs, selfBaggingAreaObservers);
    	allSelfScanningAreaObservers.put(scs, selfScanningAreaObservers);
    	allSelfScreenObservers.put(scs, selfScreenObservers);
    	allSelfPrinterObservers.put(scs, selfPrinterObservers);
    	allSelfCardReaderObservers.put(scs, selfCardReaderObservers);
    	allSelfMainScannerObservers.put(scs, selfMainScannerObservers);
    	allSelfHandheldScannerObservers.put(scs, selfHandheldScannerObservers);
    	allSelfBanknoteInputObservers.put(scs, selfBanknoteInputObservers);
    	allSelfBanknoteOutputObservers.put(scs, selfBanknoteOutputObservers);
    	allSelfBanknoteValidatorObservers.put(scs, selfBanknoteValidatorObservers);
    	allSelfBanknoteStorageObservers.put(scs, selfBanknoteStorageObservers);
    	allSelfBanknoteDispenserObservers.put(scs, selfBanknoteDispenserObservers);
    	allSelfCoinSlotObservers.put(scs, selfCoinSlotObservers);
    	allSelfCoinValidatorObservers.put(scs, selfCoinValidatorObservers);
    	allSelfCoinStorageObservers.put(scs, selfCoinStorageObservers);
    	allSelfCoinDispenserObservers.put(scs, selfCoinDispenserObservers);
    	allSelfCoinTrayObservers.put(scs, selfCoinTrayObservers);
    }
	
    // remove supervison station from allSuperStationObservers
	public void removeStation(SupervisionStation ss) {
		if (!isLoggedIn) return;
		if (!allSuperStations.contains(ss)) return;
		
		allSuperStations.remove(ss);
		allSuperTouchScreenObservers.remove(ss);
		allSuperKeyboardObservers.remove(ss);
	}
	
	// remove self-checkout station from stationObservers
	public void removeStation(SelfCheckoutStation scs) {
		if (!isLoggedIn) return;
		if (!allSelfStations.contains(scs)) return;
		
		allSelfStations.remove(scs);
		allSelfBaggingAreaObservers.remove(scs);
		allSelfScanningAreaObservers.remove(scs);
		allSelfScreenObservers.remove(scs);
		allSelfPrinterObservers.remove(scs);
		allSelfCardReaderObservers.remove(scs);
		allSelfMainScannerObservers.remove(scs);
		allSelfHandheldScannerObservers.remove(scs);
		allSelfBanknoteInputObservers.remove(scs);
		allSelfBanknoteOutputObservers.remove(scs);
		allSelfBanknoteValidatorObservers.remove(scs);
		allSelfBanknoteStorageObservers.remove(scs);
		allSelfBanknoteDispenserObservers.remove(scs);
		allSelfCoinSlotObservers.remove(scs);
		allSelfCoinValidatorObservers.remove(scs);
		allSelfCoinStorageObservers.remove(scs);
		allSelfCoinDispenserObservers.remove(scs);
		allSelfCoinTrayObservers.remove(scs);
	}
	
	// start supervisor station up by attaching its potential observers
	public void startup(SupervisionStation ss) {
		if (!isLoggedIn) return;
		if (!allSuperStations.contains(ss)) return;
		
		ArrayList<TouchScreenObserver> screenObservers = allSuperTouchScreenObservers.get(ss);
		for (TouchScreenObserver observer: screenObservers) {
			ss.screen.attach(observer);
		}
		
		ArrayList<KeyboardObserver> keyboardObservers = allSuperKeyboardObservers.get(ss);
		for (KeyboardObserver observer: keyboardObservers) {
			ss.keyboard.attach(observer);
		}
	}

	// start self-checkout station up by attaching its potential observers and unblocking it
	public void startup(SelfCheckoutStation scs) {
		if (!isLoggedIn) return;
		if (!allSelfStations.contains(scs)) return;
		
		AttendantActions attendantActions = new AttendantActions();
		attendantActions.attendantUnBlockStation(scs);
		
		ArrayList<ElectronicScaleObserver> baggingAreaObservers = allSelfBaggingAreaObservers.get(scs);
		for (ElectronicScaleObserver observer: baggingAreaObservers)
			scs.baggingArea.attach(observer);
		
		ArrayList<ElectronicScaleObserver> scanningAreaObservers = allSelfScanningAreaObservers.get(scs);
		for (ElectronicScaleObserver observer: scanningAreaObservers)
			scs.scanningArea.attach(observer);
		
		ArrayList<TouchScreenObserver> screenObservers = allSelfScreenObservers.get(scs);
		for (TouchScreenObserver observer: screenObservers)
			scs.screen.attach(observer);
		
		ArrayList<ReceiptPrinterObserver> printerObservers = allSelfPrinterObservers.get(scs);
		for (ReceiptPrinterObserver observer: printerObservers)
			scs.printer.attach(observer);
		
		ArrayList<CardReaderObserver> cardReaderObservers = allSelfCardReaderObservers.get(scs);
		for (CardReaderObserver observer: cardReaderObservers)
			scs.cardReader.attach(observer);
		
		ArrayList<BarcodeScannerObserver> mainScannerObservers = allSelfMainScannerObservers.get(scs);
		for (BarcodeScannerObserver observer: mainScannerObservers)
			scs.mainScanner.attach(observer);
		
		ArrayList<BarcodeScannerObserver> handheldScannerObservers = allSelfHandheldScannerObservers.get(scs);
		for (BarcodeScannerObserver observer: handheldScannerObservers)
			scs.handheldScanner.attach(observer);
		
		ArrayList<BanknoteSlotObserver> banknoteInputObservers = allSelfBanknoteInputObservers.get(scs);
		for (BanknoteSlotObserver observer: banknoteInputObservers)
			scs.banknoteInput.attach(observer);
		
		ArrayList<BanknoteSlotObserver> banknoteOutputObservers = allSelfBanknoteOutputObservers.get(scs);
		for (BanknoteSlotObserver observer: banknoteOutputObservers)
			scs.banknoteOutput.attach(observer);
		
		ArrayList<BanknoteValidatorObserver> banknoteValidatorObservers = allSelfBanknoteValidatorObservers.get(scs);
		for (BanknoteValidatorObserver observer: banknoteValidatorObservers)
			scs.banknoteValidator.attach(observer);
		
		ArrayList<BanknoteStorageUnitObserver> banknoteStorageObservers = allSelfBanknoteStorageObservers.get(scs);
		for (BanknoteStorageUnitObserver observer: banknoteStorageObservers)
			scs.banknoteStorage.attach(observer);
		
		Map<Integer, ArrayList<BanknoteDispenserObserver>> banknoteDispenserObservers = allSelfBanknoteDispenserObservers.get(scs);
		for (Integer banknoteDenomination: scs.banknoteDenominations) {
			for (BanknoteDispenserObserver observer: banknoteDispenserObservers.get(banknoteDenomination))
				scs.banknoteDispensers.get(banknoteDenomination).attach(observer);
		}
			
		ArrayList<CoinSlotObserver> coinSlotObservers = allSelfCoinSlotObservers.get(scs);
		for (CoinSlotObserver observer: coinSlotObservers)
			scs.coinSlot.attach(observer);
		
		ArrayList<CoinValidatorObserver> coinValidatorObservers = allSelfCoinValidatorObservers.get(scs);
		for (CoinValidatorObserver observer: coinValidatorObservers)
			scs.coinValidator.attach(observer);
		
		ArrayList<CoinStorageUnitObserver> coinStorageObservers = allSelfCoinStorageObservers.get(scs);
		for (CoinStorageUnitObserver observer: coinStorageObservers)
			scs.coinStorage.attach(observer);
		
		Map<BigDecimal, ArrayList<CoinDispenserObserver>> coinDispenserObservers = allSelfCoinDispenserObservers.get(scs);
		for (BigDecimal coinDenomination: scs.coinDenominations) {
			for (CoinDispenserObserver observer: coinDispenserObservers.get(coinDenomination))
				scs.coinDispensers.get(coinDenomination).attach(observer);
		}
		
		ArrayList<CoinTrayObserver> coinTrayObservers = allSelfCoinTrayObservers.get(scs);
		for (CoinTrayObserver observer: coinTrayObservers)
			scs.coinTray.attach(observer);
	}
	
	// shut supervisor station down by detaching its observers
	public void shutdown(SupervisionStation ss) {
		if (isLoggedIn) return;
		if (!allSuperStations.contains(ss)) return;
		
		ss.screen.detachAll();
		ss.keyboard.detachAll();
	}
	
	// shut self-checkout station down by detaching its observers and blocking it
	public void shutdown(SelfCheckoutStation scs) {
		if (!isLoggedIn) return;
		if (!allSelfStations.contains(scs)) return;
		
		AttendantActions attendantActions = new AttendantActions();
		attendantActions.attendantBlockStation(scs); 
		
		scs.baggingArea.detachAll();
		scs.scanningArea.detachAll();
		scs.screen.detachAll();
		scs.printer.detachAll();
		scs.cardReader.detachAll();
		scs.mainScanner.detachAll();
		scs.handheldScanner.detachAll();
		scs.banknoteInput.detachAll();
		scs.banknoteOutput.detachAll();
		scs.banknoteValidator.detachAll();
		scs.banknoteStorage.detachAll();
		for (Integer banknoteDenomination: scs.banknoteDenominations)
			scs.banknoteDispensers.get(banknoteDenomination).detachAll();
		scs.coinSlot.detachAll();
		scs.coinValidator.detachAll();
		scs.coinStorage.detachAll();
		for (BigDecimal coinDenomination: scs.coinDenominations)
			scs.coinDispensers.get(coinDenomination).detachAll();
		scs.coinTray.detachAll();
		
	}
	
	
	public void login(SupervisionStation superStation, PasswordDatabase database) {
		if (isLoggedIn) return;
        database.AddLoginDetails("admin","admin");
        loginFrame.unPressed();
        while (loginFrame.isPressed()== false)
        loginFrame.setVisible(true);
      
        String id = loginFrame.getTextUserID();
        String password = loginFrame.getPasswordEntered();
        
        if(database.getPassword(id).equals(password))
        {
            this.superStation = superStation;
            superStation.screen.enable();
            superStation.keyboard.enable();
            isLoggedIn = true;
        }
        else {
            loginFrame.dispose();
            FailedLogin failFrame = new FailedLogin();
            failFrame.setVisible(true);
        }
    }
	
	//logout and disable screen and keyboard
	public void logout(SupervisionStation superStation) {
		if(isLoggedIn == true && superStation.equals(this.superStation))
		{
			this.superStation = null;
			superStation.screen.disable();
			superStation.keyboard.disable();
			isLoggedIn = false;
		}
	}
	
	 // Getter for the log in status
	  public Boolean getLoginStatus() {
	    return isLoggedIn;
	  }
	  
	  public ArrayList<SupervisionStation> getAllSuperStations() {
		  return allSuperStations;
	  }
	  
	  public ArrayList<TouchScreenObserver> getSuperTouchScreenObservers(SupervisionStation ss) {
		  return allSuperTouchScreenObservers.get(ss);
	  }
	  
	  public ArrayList<KeyboardObserver> getSuperKeyboardObservers(SupervisionStation ss) {
		  return allSuperKeyboardObservers.get(ss);
	  }
	  
	  
	  public ArrayList<SelfCheckoutStation> getAllSelfStations() {
		  return allSelfStations;
	  }
	  
	  public ArrayList<ElectronicScaleObserver> getSelfBaggingAreaObservers(SelfCheckoutStation scs) {
		  return allSelfBaggingAreaObservers.get(scs);
	  }
	  
	  public ArrayList<ElectronicScaleObserver> getSelfScanningAreaObservers(SelfCheckoutStation scs) {
		  return allSelfScanningAreaObservers.get(scs);
	  }
	  
	  public ArrayList<TouchScreenObserver> getSelfScreenObservers(SelfCheckoutStation scs) {
		  return allSelfScreenObservers.get(scs);
	  }
	  
	  public ArrayList<ReceiptPrinterObserver> getSelfPrinterObservers(SelfCheckoutStation scs) {
		  return allSelfPrinterObservers.get(scs);
	  }
	  
	  public ArrayList<CardReaderObserver> getSelfCardReaderObservers(SelfCheckoutStation scs) {
		  return allSelfCardReaderObservers.get(scs);
	  }
	  
	  public ArrayList<BarcodeScannerObserver> getSelfMainScannerObservers(SelfCheckoutStation scs) {
		  return allSelfMainScannerObservers.get(scs);
	  }
	  
	  public ArrayList<BarcodeScannerObserver> getSelfHandheldScannerObservers(SelfCheckoutStation scs) {
		  return allSelfHandheldScannerObservers.get(scs);
	  }
	  
	  public ArrayList<BanknoteSlotObserver> getSelfBanknoteInputObservers(SelfCheckoutStation scs) {
		  return allSelfBanknoteInputObservers.get(scs);
	  }
	  
	  public ArrayList<BanknoteSlotObserver> getSelfBanknoteOutputObservers(SelfCheckoutStation scs) {
		  return allSelfBanknoteOutputObservers.get(scs);
	  }
	  
	  public ArrayList<BanknoteValidatorObserver> getSelfBanknoteValidatorObservers(SelfCheckoutStation scs) {
		  return allSelfBanknoteValidatorObservers.get(scs);
	  }
	  
	  public ArrayList<BanknoteStorageUnitObserver> getSelfBanknoteStorageObservers(SelfCheckoutStation scs) {
		  return allSelfBanknoteStorageObservers.get(scs);
	  }
	  
	  public Map<Integer, ArrayList<BanknoteDispenserObserver>> getSelfBanknoteDispenserObservers(SelfCheckoutStation scs) {
		  return allSelfBanknoteDispenserObservers.get(scs);
	  }
	  
	  public ArrayList<CoinSlotObserver> getSelfCoinSlotObservers(SelfCheckoutStation scs) {
		  return allSelfCoinSlotObservers.get(scs);
	  }
	  
	  public ArrayList<CoinValidatorObserver> getSelfCoinValidatorObservers(SelfCheckoutStation scs) {
		  return allSelfCoinValidatorObservers.get(scs);
	  }
	  
	  public ArrayList<CoinStorageUnitObserver> getSelfCoinStorageObservers(SelfCheckoutStation scs) {
		  return allSelfCoinStorageObservers.get(scs);
	  }
	  
	  public Map<BigDecimal, ArrayList<CoinDispenserObserver>> getSelfCoinDispenserObservers(SelfCheckoutStation scs) {
		  return allSelfCoinDispenserObservers.get(scs);
	  }
	  
	  public ArrayList<CoinTrayObserver> getSelfCoinTrayObservers(SelfCheckoutStation scs) {
		  return allSelfCoinTrayObservers.get(scs);
	  }
	  
	  
}
