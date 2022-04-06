package org.lsmr.selfcheckout.software;

import java.util.HashMap;

public class PasswordDatabase {
	private HashMap<int, String> database = new HashMap<int, String>();
	
	public PasswordDatabase(HashMap<int, String> map) {
		this.database = map;
	}
	
	public BarcodedProductDatabase(){
	}
	
	public void AddLoginDetails(int ID, String password){
		database.put(ID, password);
	}
	
	public Password getPassword(int ID) {
		return database.get(ID);
	}
}
