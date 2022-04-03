package org.lsmr.software;

import java.math.BigDecimal;
import java.util.Currency;
import org.lsmr.selfcheckout.Card.CardData;

// Need To Add Pay With Card Here Refer To Other Payment Methods
// Rewrite Test Cases For Checkout Method As We Substituted It

import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.observers.*;

public class Checkout implements CoinValidatorObserver, BanknoteValidatorObserver, CardReaderObserver {
	private SelfCheckoutStation station;
	private double amountDue;
	private double amountPaid;
	private boolean isCheckingOut;
	//boolean checks for card payments
	private CardData cardData;
	private boolean cardisSwiped = false;
	private boolean cardisTapped = false;
	private boolean cardisInserted = false;
	private boolean success = false;
	private ShoppingCartReceiptPrinter printReceipt;
	

	public Checkout(SelfCheckoutStation checkout) {
		station = checkout;
		station.coinValidator.attach(this);
		station.banknoteValidator.attach(this);
		station.cardReader.attach(this);
		
		isCheckingOut = false;

		station.banknoteValidator.disable();
		station.coinValidator.disable();
		station.cardReader.disable();

		// Will Throw Exception If Not Enough Materials Are Left
		station.printer.addPaper(500);
		station.printer.addInk(500);
		printReceipt = new ShoppingCartReceiptPrinter(station);
	}

	public void wouldLikeToCheckOut(double totalPrice) {
		amountDue = totalPrice;
		amountPaid = 0;
		isCheckingOut = true;
		
		station.baggingArea.enable();
        station.scanningArea.enable();
        station.mainScanner.enable();
        station.handheldScanner.enable();
        
		station.banknoteValidator.enable();
		station.coinValidator.enable();
		station.cardReader.enable();
		System.out.println("Please proceed to checking out.");
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
	}

	@Override
	// Called whenever a new coin is inserted and accepted in the machine
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		amountPaid += value.doubleValue();
		if(amountPaid >= amountDue) {
			checkoutFinished();
		}
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {}

	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		amountPaid += (double) value;
		if(amountPaid >= amountDue) {
			checkoutFinished();
		}
	}


	@Override
	public void invalidBanknoteDetected(BanknoteValidator validator) {}

	public void returnToScanning() {
		// This Method In Conjunction With The Checkout Finished Method, Solves Both The Paid In Full And
		// Partial Payment Usecases And Their Derivatives

		isCheckingOut = false;

		// Functionalities That Were Disabled Amidst The Initialization Of Payment
		station.baggingArea.enable();
        station.scanningArea.enable();
        station.mainScanner.enable();
        station.handheldScanner.enable();

		// Functionalities That Are To Be Disabled While Not Paying
		station.coinValidator.disable();
		station.banknoteValidator.disable();
	}
	@Override
	public void cardInserted(CardReader reader) {
		cardisInserted = true;
		
	}

	@Override
	public void cardRemoved(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardTapped(CardReader reader) {
		cardisTapped = true;
		
		
	}

	@Override
	public void cardSwiped(CardReader reader) {
		cardisSwiped = true;
		
	}

	@Override
	public void cardDataRead(CardReader reader, CardData data) {
		//I have never seen a self checkout (and I have worked on one for 2 years) that accepts partial credit card payment
		//therefore, I chose to implement card payment as the total amount due
		//It is easier to make a single card function, as debit and credit these days possess the same functionality.
		//If card swiped isn't debit or credit, simulation will be thrown
		cardData = data;
		//Observer will trigger whatever state the customer indicates
		if (cardisSwiped && cardData.getCardholder()!=null && cardData.getNumber()!=null) {
			if (cardData.getType()=="credit"||cardData.getType()=="debit") {
				//boolean expression will indicate whether the trial was success or not
				success=true;
				//card data will then be read
				CustomerBank bank = new CustomerBank(cardData, "Swipe");
				payWithCard(bank);
			}	
			//if card is tapped
		} else if (cardisTapped && cardData.getCardholder()!=null && cardData.getNumber()!=null) {
			if (cardData.getType()=="credit"||cardData.getType()=="debit") {
				success=true;
				CustomerBank bank = new CustomerBank(cardData, "Tap");
				payWithCard(bank);
			}
			//if card is inserted
		} else if (cardisInserted && cardData.getCardholder()!=null && cardData.getNumber()!=null) {
			if (cardData.getType()=="credit"||cardData.getType()=="debit") {
				success=true;
				CustomerBank bank = new CustomerBank(cardData, "Insert");
				payWithCard(bank);
			}
			//card isn't valid or doesn't meet above criteria
		} else {
			System.out.println("Invalid Card Type");
		}
		
	}
	//pay with card is called when reader observes a valid card
	public void payWithCard (CustomerBank bank) {
		double amount=getAmountDue();
		//temporarily created a data base that populates a mock customers bank account
		double customerBalance = bank.funds;
		if ((amount > customerBalance) && success) {
			System.out.println("Insufficient funds");
		}
		else if ((amount <= customerBalance) && success){ 
			bank.setFunds(customerBalance-amount);
			amountPaid = amount;
			checkoutFinished();
		}
	}
	public void checkoutFinished() {

		// Receipt For The Transation Of The Customer Checking Out
		printReceipt.printReceipt();

		// After A Customer Is Done The Station Needs To Be Reset For The Next Customer

		// Empty Items From Cart
		ShoppingCart.getInstance().Empty();

		// Reset Amounts
		amountDue = 0;
		amountPaid = 0;

		// States Need To Be Updated To Accomodate The Functionalities Needed By The Next Customer
		station.baggingArea.enable();
        station.scanningArea.enable();
        station.mainScanner.enable();
        station.handheldScanner.enable();
		station.coinValidator.disable();
		station.banknoteValidator.disable();
		station.cardReader.disable();
		isCheckingOut = false;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public double getAmountDue() {
		return amountDue;
	}



}

