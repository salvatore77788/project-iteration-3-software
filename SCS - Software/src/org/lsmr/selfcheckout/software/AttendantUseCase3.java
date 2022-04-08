package org.lsmr.selfcheckout.software;

import java.util.List;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;

public class AttendantUseCase3 {
	//potentially implement TouchScreenObserver
	
	private boolean isLoggedIn = false;
	private SupervisorStation superStation;
	private Hashmap<SelfCheckoutStation, ArrayList<T>> stationObservers = new Hashmap<SelfCheckoutStation, ArrayList<T>>(); 
	
	public void addStation(T station, ArrayList<T> potentialObservers) {
		if (!isLoggedIn) return;
		stationObservers.add(station, potentialObservers);
	}
	
	public void removeStation(T station) {
		if (!isLoggedIn) return;
		if (!stationObservers.containsKey(station)) return;
		stationObservers.remove(station);
	}
	
	public void startup(SupervisorStation ss) {
		for (T potentialObserver: stationObservers.get(ss)) {
			ss.attach(potentialObserver);
		}
	}

	public void startup(SelfCheckoutStation scs) {
		if (!isLoggedIn) return;
		if (!stationObservers.containsKey(scs)) return;
		
		for (T potentialObserver: stationObservers.get(scs)) {
			scs.attach(potentialObserver);
		}
		attendantUnBlockStation(scs); //method in AttendantActions --> potentially combine the two classes
	}
	
	public void shutdown(SupervisorStation ss) {
		if (isLoggedIn) return;
		if (!stationObservers.containsKey(ss)) return;
		
		ss.detachAll();
		
	}
	
	public void shutdown(SelfCheckoutStation scs) {
		if (!isLoggedIn) return;
		if (!stationObservers.containsKey(scs)) return;
		
		scs.detachAll();
		attendantBlockStation(scs); //method in AttendantActions --> potentially combine the two classes
		
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
