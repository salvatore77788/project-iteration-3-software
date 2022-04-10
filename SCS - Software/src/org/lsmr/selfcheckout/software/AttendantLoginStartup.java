package org.lsmr.selfcheckout.software;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.devices.observers.KeyboardObserver;
import org.lsmr.selfcheckout.devices.observers.TouchScreenObserver;

public class AttendantLoginStartup {
	private boolean isLoggedIn = false;
	private SupervisionStation superStation;
	
	private ArrayList<SupervisionStation> allSuperStations = new ArrayList<SupervisionStation>();
	
	private Map<SupervisionStation, ArrayList<TouchScreenObserver>> allSuperTouchScreenObservers = 
			new HashMap<SupervisionStation, ArrayList<TouchScreenObserver>>(); 
	
	private Map<SupervisionStation, ArrayList<KeyboardObserver>> allSuperKeyboardObservers = 
			new HashMap<SupervisionStation, ArrayList<KeyboardObserver>>(); 
	
	
	
	private ArrayList<SelfCheckoutStation> allSelfStations = new ArrayList<SelfCheckoutStation>();
	
	private Map<SelfCheckoutStation, ArrayList<T>> allSelfObservers = new HashMap<SelfCheckoutStation, ArrayList<T>>();
	
    
    // add a supervision station to allSuperStationObservers
    public void addStation(SupervisionStation ss, 
    		ArrayList<TouchScreenObserver> superTouchScreenObservers, 
    		ArrayList<KeyboardObserver> superKeyboardObservers) {
    	
		if (!isLoggedIn) return;
		
		allSuperStations.add(ss);
		allSuperTouchScreenObservers.put(ss, superTouchScreenObservers);
		allSuperKeyboardObservers.put(ss, superKeyboardObservers);
	}
    
    public void addStation(SelfCheckoutStation scs) {
    	if (!isLoggedIn) return;   	
    	
    	allSelfStations.add(scs);
    	allSelfStationObservers.put(scs, selfStationObservers);
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
		
		allSelfStationObservers.remove(scs);
	}
	
	// start supervisor station up by attaching its potential observers
	public <T> void startup(SupervisionStation ss) {
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
		if (!stationObservers.containsKey(scs)) return;
		
		attendantUnBlockStation(scs);
		
		for (T potentialObserver: stationObservers.get(scs))
			scs.attach(potentialObserver);
		
		for ()
			
			
		for ()
		
	}
	
	// shut supervisor station down by detaching its observers
	public void shutdown(SupervisorStation ss) {
		if (isLoggedIn) return;
		if (!stationObservers.containsKey(ss)) return;
		
		detachAll();
		detachAll();
		detachAll();
		detachAll();
	}
	
	// shut self-checkout station down by detaching its observers and blocking it
	public void shutdown(SelfCheckoutStation scs) {
		if (!isLoggedIn) return;
		if (!stationObservers.containsKey(scs)) return;
		
		attendantBlockStation(scs); 
		
		detachAll();
		detachAll();
		detachAll();
		detachAll();
		
	}
	
	
	//id and password inputted for a given superVisionStation and database
	public void login(int id, String password, SupervisionStation superStation, PasswordDatabase database) {
		
		//login if password matches id
		if(database.getPassword(id) == password && isLoggedIn == false)
		{
			this.superStation = superStation
			superStation.screen.enable();
			superStation.keyboard.enable();
			isLoggedIn = true;
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
	  
	  // getter for the super station observers
	  public HashMap<SupervisionStation, ArrayList<?>> getSuperStationObservers() {
		  //fill hashmap
		  
		  return allStationObservers;
	  }
	  
	  // getter for the self station observers
	  public HashMap<SelfCheckoutStation, ArrayList<?>> getSelfStationObservers() {
		  //fill hashmap
		  
		  return allStationObservers;
	  }
}
