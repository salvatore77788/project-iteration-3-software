package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class AttendantActions {
	
	private boolean isLoggedIn = false;
	private SupervisorStation superStation;
	private Hashmap<SupervisionStation, Hashmap<D extends AbstractDevice, ArrayList<O>>> allSuperStationObservers = 
			new Hashmap<SupervisionStation, Hashmap<D extends AbstractDevice, ArrayList<O>>>(); 
	private Hashmap<SelfCheckoutStation, Hashmap<D extends AbstractDevice, ArrayList<O>>> allSelfStationObservers = 
			new Hashmap<SelfCheckoutStation, Hashmap<D extends AbstractDevice, ArrayList<O>>>(); 
	
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
    
    // add a supervision station to allSuperStationObservers
    public void addStation(SupervisionStation ss, Hashmap<D, ArrayList<O>> superStationObservers) {
		if (!isLoggedIn) return;
		
		allSuperStationObservers.add(ss, superStationObservers);
	}
    
    public void addStation(SelfCheckoutStation scs, Hashmap<D, ArrayList<O>> selfStationObservers) {
    	if (!isLoggedIn) return;   	
    	
    	allSelfStationObservers.add(scs, selfStationObservers)
    }
	
    // remove supervison station from allSuperStationObservers
	public void removeStation(SupervisionStation ss) {
		if (!isLoggedIn) return;
		if (!allSuperStationObservers.containsKey(ss)) return;
		
		allSuperStationObservers.remove(ss);
	}
	
	// remove self-checkout station from stationObservers
	public void removeStation(SelfCheckoutStation scs) {
		if (!isLoggedIn) return;
		if (!allSelfStationObservers.containsKey(scs)) return;
		
		allSelfStationObservers.remove(scs);
	}
	
	// start supervisor station up by attaching its potential observers
	public void startup(SupervisorStation ss) {
		Hashmap<D extends AbstractDevice, ArrayList<O>> superStationObservers = allSuperStationObservers.get(ss);
		for (O extends TouchScreenObserver observer: superstationObservers.get(ss.screen)) {
			ss.screen.attach(observer);
		}
		
		for () {
			
		}
		
		for () {
			
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
	  
	  // getter for the station observers
	  public Hashmap<S, ArrayList<O>> getStationObservers() {
		  return stationObservers;
	  }

}