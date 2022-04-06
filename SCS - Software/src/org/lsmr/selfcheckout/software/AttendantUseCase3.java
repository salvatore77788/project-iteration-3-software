package org.lsmr.selfcheckout.software;

import java.util.List;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;

public class AttendantUseCase3 implements TouchScreenObserver {
	
	private boolean isLoggedIn = false;
	private SupervisorStation superStation;
	private Hashmap<SelfCheckoutStation, ArrayList<T>> stationObservers = new Hashmap<SelfCheckoutStation, ArrayList<T>>(); 
	
	public AttendantUseCase3() {
		
	}
	
	public void addStation(SelfCheckoutStation scs, ArrayList<T> potentialObservers) {
		if (!isLoggedIn) return;
	}
	
	public void removeStation(SelfCheckoutStation scs) {
		if (!isLoggedIn) return;
		if (!stationObservers.contains(scs)) return;
	}

	public void startup() {
		if (!isLoggedIn) return;
		
		//attach observers
		
	}
	
	public void shutdown() {
		if (!isLoggedIn) return;
		
		//detach observers
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
