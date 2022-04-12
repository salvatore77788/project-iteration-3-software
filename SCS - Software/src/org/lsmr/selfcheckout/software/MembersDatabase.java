package org.lsmr.selfcheckout.software;

import java.util.HashMap;

import org.lsmr.selfcheckout.Card.CardData;

public class MembersDatabase {
	
	// Let the first string be the card number (since it will be unique most likely)
	// Let the second string be the Cardholder's name.
	public HashMap<String,String> members;
	
	// Constructor.
	public MembersDatabase() {
		this.members = new HashMap<String, String>();
	}
	
	
	
	public void registerMember(CardData cardInfo) {
		
		if(!members.containsKey(cardInfo.getNumber())) {
			members.put(cardInfo.getNumber(), cardInfo.getCardholder());
		}
		// Otherwise it was already registered. 
		
	}
	
	public boolean authenticateMember(CardData cardInfo) {
		
		if(members.containsKey(cardInfo.getNumber()))
			return true;
		
		else 
			return false;
		
	}
	
	// meant for GUI only.
	public boolean authenticateByNumber(String cardNumberProvided) {
		if(members.containsKey(cardNumberProvided)) {
			return true;
		}
		
		else
			return false;
	}
	
	// In the case that the member wishes to opt out 
		// of the membership.
	public void leaveMembership(CardData cardInfo) {
		members.remove(cardInfo.getNumber(),cardInfo.getCardholder());
			
			
	}
	
	/* don't need it.
	 * public HashMap<String,String> getMembers(){ return members; }
	 */
	
	

}
