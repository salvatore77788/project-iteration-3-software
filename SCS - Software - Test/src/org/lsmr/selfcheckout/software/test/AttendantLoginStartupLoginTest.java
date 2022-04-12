package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.software.AttendantLoginStartup;
import org.lsmr.selfcheckout.software.PasswordDatabase;
import org.lsmr.selfcheckout.software.gui.Login;

public class AttendantLoginStartupLoginTest {
	
	private SelfCheckoutStation station;
    private AttendantLoginStartup loginStartup;
    private SupervisionStation superStation;
    private PasswordDatabase pdata;
    private Login loginFrame;

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
       pdata = new PasswordDatabase(new HashMap<String, String>());
       superStation = new SupervisionStation();
    }

    @Test
    public void loginTest() {    	
        loginStartup.login(superStation, pdata); 
        Assert.assertEquals(true, loginStartup.getLoginStatus());
    }
    
    
    @Test
    public void logoutTest() {
        loginStartup.logout(superStation);
        
        Assert.assertTrue(loginStartup.getLoginStatus() == false);
  
    }
    
}