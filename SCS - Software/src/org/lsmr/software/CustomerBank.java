package org.lsmr.software;

import java.util.Random;


import org.lsmr.selfcheckout.Card.CardData;

//Since there is no direct method to verify funds in a customers card, we have decided to create a simulator for their bank account
//in the interest of time and simplicity, this class will generate a fake account, and populate the funds.
public class CustomerBank {
	public String type;
	public String number;
	public String cardholder;
	public String cvv;
	public String pin;
	public double funds;
	
	public CustomerBank(CardData data, String type) {
		// do something
		if (type.equals("Swipe")) {
			type = data.getType();
			number = data.getNumber();
			cardholder = data.getCardholder();
			funds = 1000;
			
		} else if (type.equals("Tap")) {
			type = data.getType();
			number = data.getNumber();
			cardholder = data.getCardholder();
			cvv = data.getCVV();
			funds = 1000;
			
		}else if (type.endsWith("Insert")) {

			type = data.getType();
			number = data.getNumber();
			cardholder = data.getCardholder();
			cvv = data.getCVV();
			funds = 1000;
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCardholder() {
		return cardholder;
	}
	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public double getFunds() {
		return funds;
	}
	public void setFunds(double funds) {
		this.funds = funds;
	}
	
}