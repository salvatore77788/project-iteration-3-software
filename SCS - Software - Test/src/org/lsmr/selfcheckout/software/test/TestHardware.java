package org.lsmr.selfcheckout.software.test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;

import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;




public class TestHardware {
	
	public Currency currency;
    public SelfCheckoutStation scs;
    public int[] denominations;
    public BigDecimal[] coinDenominations;
    public int scaleMaximumWeight;
    public int scaleSensitivity;
    
    public static Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.CANADA);
    
    public BarcodeScanner scanner;
    public Barcode barcode_for_item;
    public BarcodedItem item;
    public BarcodedProduct product;
    

	
	//new Numeral[] {Numeral.one, Numeral.two}
	public TestHardware(){
        this.currency = DEFAULT_CURRENCY;
        this.denominations = new int[]{5, 10, 20, 50, 100}; 
        this.coinDenominations = new BigDecimal[]{
        		new BigDecimal("0.05"), 
        		new BigDecimal("0.10"),
        		new BigDecimal("0.25"),
        		new BigDecimal("1.00"),
        		new BigDecimal("2.00"),
        		};
        
        this.scaleMaximumWeight = 5000;
        this.scaleSensitivity = 2;

        this.scs = new SelfCheckoutStation(currency, denominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
        
        this.scanner = new BarcodeScanner();
        


        System.out.println("Test Hardware Created");
    }
	

}
