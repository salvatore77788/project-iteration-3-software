package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.software.AttendantLoginStartup;
import org.lsmr.selfcheckout.software.PasswordDatabase;

public class AttendantLoginStartupTest {
	
	private SelfCheckoutStation station;
    private AttendantLoginStartup loginStartup;
    private SupervisionStation superStation;
    private PasswordDatabase pdata;

    @Before
    public void setup() {

        Currency currency = Currency.getInstance("CAD");
        int[] banknoteDenominations = { 5, 10, 20, 50 };
        BigDecimal[] coinDenominations = { new BigDecimal(0.05), new BigDecimal(0.1), new BigDecimal(0.25),
        new BigDecimal(1.00), new BigDecimal(2.00) };
        int weightLimitInGrams = 100;
        int sensitivity = 1;
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.MONTH, 11);
        expiry.add(Calendar.YEAR, 2022);
        station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, weightLimitInGrams,
                sensitivity);
       loginStartup = new AttendantLoginStartup();
    }

    @Test
    public void addStationTest() {
    	loginStartup.addStation(superStation, null, null);

    }
    
    @Test
    public void removeStationTest() {
    	loginStartup.removeStation(superStation);

    }
	
    @Test
    public void startUpTest() {
    	loginStartup.startup(superStation);

    }
    
    @Test
    public void shutDownTest() {
    	loginStartup.shutdown(superStation);

    }
    
    @Test
    public void loginTest() {
    	pdata.AddLoginDetails("0", "0");
        loginStartup.login(superStation, pdata);
        
        //not sure how to test gui input so that it puts in correct login information
 
        Assert.assertTrue(loginStartup.getLoginStatus() == true);
  
    }
    
    
    @Test
    public void logoutTest() {
        loginStartup.logout(superStation);
        
        Assert.assertTrue(loginStartup.getLoginStatus() == false);
  
    }
    
}
