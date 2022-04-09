package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class AttendantActions {
	
	private boolean isLoggedIn = false;
	private SupervisorStation superStation;
	private Hashmap<SelfCheckoutStation, ArrayList<T>> stationObservers = new Hashmap<SelfCheckoutStation, ArrayList<T>>(); 

    // Blocks the self checkout station by disabling its crucial components
    // Data class being the same instance for all stations should be disgussed in the meating
    public void attendantBlockStation(SelfCheckoutStation station) {
        Data data = Data.getInstance();
        station.baggingArea.disable();
        station.scanningArea.disable();
        station.handheldScanner.disable();
        station.mainScanner.disable();
        station.cardReader.disable();
        data.setIsDisabled(true);
    }

    // Unblocks the self checkout station by enabling its crucial components
    // Could be used to approve a weight discrepancy
    public void attendantUnBlockStation(SelfCheckoutStation station) {
        Data data = Data.getInstance();
        station.baggingArea.enable();
        station.scanningArea.enable();
        station.handheldScanner.enable();
        station.mainScanner.enable();
        station.cardReader.enable();
        data.setIsDisabled(false);
    }
    
    // add a station to stationObservers
    public void addStation(T station, ArrayList<T> potentialObservers) {
		if (!isLoggedIn) return;
		stationObservers.add(station, potentialObservers);
	}
	
    // remove station from stationObservers
	public void removeStation(T station) {
		if (!isLoggedIn) return;
		if (!stationObservers.containsKey(station)) return;
		stationObservers.remove(station);
	}
	
	// start supervisor station up by attaching its potential observers
	public void startup(SupervisorStation ss) {
		for (T potentialObserver: stationObservers.get(ss)) {
			ss.attach(potentialObserver);
		}
	}

	// start self-checkout station up by attaching its potential observers and unblocking it
	public void startup(SelfCheckoutStation scs) {
		if (!isLoggedIn) return;
		if (!stationObservers.containsKey(scs)) return;
		
		for (T potentialObserver: stationObservers.get(scs)) {
			scs.attach(potentialObserver);
		}
		attendantUnBlockStation(scs);
	}
	
	// shut supervisor station down by detaching its observers
	public void shutdown(SupervisorStation ss) {
		if (isLoggedIn) return;
		if (!stationObservers.containsKey(ss)) return;
		
		ss.detachAll();
		
	}
	
	// shut self-checkout station down by detaching its observers and blocking it
	public void shutdown(SelfCheckoutStation scs) {
		if (!isLoggedIn) return;
		if (!stationObservers.containsKey(scs)) return;
		
		scs.detachAll();
		attendantBlockStation(scs); 
		
	}
	
	
	//id and password inputted for a given superVisionStation and database
	public void login(int id, String password, SupervisionStation superStation, PasswordDatabase database) {
		
		//login if password matches id
		if(database.getPassword(id) == password && isLoggedIn == false)
		{
			    superStation.screen.enable();
			    superStation.keyboard.enable();
			    isLoggedIn = true;
		}
		
	}
	
	//logout and disable screen and keyboard
	public void logout(SupervisionStation superStation) {
		if(isLoggedIn == true)
		{
			 superStation.screen.disable();
			 superStation.keyboard.disable();
			 isLoggedIn = false;
		}
	}
	
	// Getter for the log in status
	  public Boolean getLoginStatus() {
	    return isLoggedIn;
	  }

}