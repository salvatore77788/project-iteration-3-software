package org.lsmr.selfcheckout.software;

public class attendantUseCase3 {
	
	//Potentially add methods to add or remove supervised stations if no other class has done that already
	
	public void startup() {
		//enable all functions/objects related to customer use of a station, such as scanner,coin acceptor, etc etc.
		//can only be done while logged in
	}
	
	public void shutdown() {
		//reverse of startup()
		//can only be done while logged in
	}
	
	public void login() {
		//Have attendant enter an employee ID and password, and test if the entries occur in a password database of some sort
		//On successful login, enable functions/objects related to supervision stations. 
		//Can only be run properly if current station is not already logged into. 
	}
	
	public void logout() {
		//reverse of login, doesn't require password, just have the attendant press a logout button, and disable corresponding functions.
		//can only be done if already logged in.
	}
}
