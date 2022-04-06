package org.lsmr.selfcheckout.software;

import java.util.List;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;

public class attendantUseCase3 {
	
	public boolean isLoggedIn = false;
	//Potentially add methods to add or remove supervised stations if no other class has done that already
	
	public void startup() {
		//enable all functions/objects related to customer use of a station, such as scanner,coin acceptor, etc etc.
		//can only be done while logged in
	}
	
	public void shutdown() {
		//reverse of startup()
		//can only be done while logged in
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
		}
	}
	
	// Getter for the log in status
	  public Boolean getLoginStatus() {
	    return isLoggedIn;
	  }

	
	
}
