package org.lsmr.software;

import java.math.BigDecimal;
import java.util.List;

import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

public class ShoppingCartReceiptPrinter implements ReceiptPrinterObserver {
	private SelfCheckoutStation station;
	
	private boolean lowPaper;
	private boolean lowInk;
	public CardData membershipInfo;

	public ShoppingCartReceiptPrinter(SelfCheckoutStation station) {
		lowPaper = lowInk = true; // Assume this is created at the same time as the receipt printer
		
		this.station = station;
		station.printer.attach(this);
	}
	
	public void printReceipt() {
		final String currencySymbol = SelfCheckoutStationSetup.currency.getSymbol();
		
		List<ShoppingCart.ShoppingCartEntry> cart = ShoppingCart.getInstance().getEntries();
		
		// print a header?
		
		// Print each item line by line
		for(ShoppingCart.ShoppingCartEntry entry : cart) {
			Product prod = entry.getProduct();
			BigDecimal price = entry.getPrice();
			
			// Refactor in HW: both barcoded and PLU product classes have a description field; move up into product class?
			String prodDesc = "";
			if(prod.getClass() == BarcodedProduct.class) {
				prodDesc = ((BarcodedProduct)prod).getDescription();
			}
			else if(prod.getClass() == PLUCodedProduct.class) {
				prodDesc = ((PLUCodedProduct)prod).getDescription();
			}
			
			printString(prodDesc);
			
			// Right align price
			String priceStr = currencySymbol + price.toString();
			int empty = ReceiptPrinter.CHARACTERS_PER_LINE-prodDesc.length()-priceStr.length();
			printEmpty(empty);
			
			printString(priceStr);
			
			// New line
			printNewline();
		}
		
		// End message
		printLine();
		
		String totalStr = currencySymbol + ShoppingCart.getInstance().getTotalPrice().toString();
		printString("TOTAL");
		printEmpty(ReceiptPrinter.CHARACTERS_PER_LINE-5-totalStr.length());
		printString(totalStr);
		
		// Print the membership card number.
		if (membershipInfo != null ){
			printNewline();
			printLine();
			printString("Member Number: ");
			printString(membershipInfo.getNumber());
			printNewline();
		}
		
		//printLine();
		
		// Cut the receipt
		station.printer.cutPaper();
	}
	
	private void printString(String str) {
		for(int i = 0; i < str.length(); i++) {
			if(canPrint())
				try {
					station.printer.print(str.charAt(i));
				} catch (EmptyException | OverloadException e) {
					// TODO THIS NEEDS TO BE HANDLED (Empty = no more paper; overload = need a new line)
					e.printStackTrace();
				}
			else
				throw new IllegalStateException("Receipt printer is out of paper or ink"); // For now, just throw an exception
		}
	}
	
	private void printNewline() {
		try {
			station.printer.print('\n');
		}
		catch (OverloadException | EmptyException e) {
			// Should not happen
			e.printStackTrace();
		}
	}
	
	private void printLine() {
		printString("_".repeat(ReceiptPrinter.CHARACTERS_PER_LINE));
		printNewline();
	}
	
	private void printEmpty(int count) {
		printString(" ".repeat(count));
	}
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOfPaper(ReceiptPrinter printer) {
		lowPaper = true;
	}

	@Override
	public void outOfInk(ReceiptPrinter printer) {
		lowInk = true;
	}

	@Override
	public void paperAdded(ReceiptPrinter printer) {
		lowPaper = false;
	}

	@Override
	public void inkAdded(ReceiptPrinter printer) {
		lowInk = false;
	}
	
	private boolean canPrint() {
		return !(lowPaper || lowInk);
	}
	
	
	public void setMembership(CardData memberCardData) {
		membershipInfo = memberCardData;
	}

}
