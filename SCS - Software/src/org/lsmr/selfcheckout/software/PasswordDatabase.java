package org.lsmr.selfcheckout.software;

import java.util.HashMap;

public class PasswordDatabase {
	private HashMap<String, String> database = new HashMap<String, String>();
	
	public PasswordDatabase(HashMap<String, String> map) {
		this.database = map;
	}
	
	public void AddLoginDetails(String ID, String password){
		database.put(ID, password);
	}
	
	public String getPassword(String ID) {
		return database.get(ID);
	}
}
