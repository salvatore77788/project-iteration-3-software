package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class FailBaggingAreaTest
    {
    private SelfCheckoutStation selfCheckout;
    private Data data;
    private CustomerScansItem scansItem;
    private ProductDatabase catalog;
    private ShoppingCart c;
    
    @BeforeClass
    public static void setUpBeforeClass () throws Exception
        {
        }

    @Before
    public void setUp () throws Exception
        {
        data = Data.getInstance();
        c    = ShoppingCart.getInstance();
        catalog = ProductDatabase.getInstance();
                
                
        scansItem = new CustomerScansItem(selfCheckout, c, catalog);
        
        int [] banknotes_accepted = {5,10,20,50};
        BigDecimal[] coins_accepted = {new BigDecimal (0.05), new BigDecimal (0.1), new BigDecimal (0.25), new BigDecimal (1), new BigDecimal (2)};
        Currency c = Currency.getInstance("CAD");
        
        selfCheckout = new SelfCheckoutStation (c, banknotes_accepted, coins_accepted, 1000, 10);
        }

    @Test
    public void test ()
        {
        PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(selfCheckout);
        selfCheckout.baggingArea.attach(baggingArea);
        selfCheckout.mainScanner.attach(scansItem);
        selfCheckout.mainScanner.enable();
        
        BarcodedItem testItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), 100);
        selfCheckout.mainScanner.scan(testItem);
        selfCheckout.baggingArea.add(testItem);
        
        Assert.assertEquals(true, data.getItemAdded());
        }
    
    @Test
    public void failsToPlaceInBaggingItemAdded ()
        {
        PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(selfCheckout);
        selfCheckout.baggingArea.attach(baggingArea);
        selfCheckout.mainScanner.attach(scansItem);
        selfCheckout.mainScanner.enable();
        
        BarcodedItem testItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), 100);
        selfCheckout.mainScanner.scan(testItem);
        
        try
            {
            TimeUnit.SECONDS.sleep(5);
            } 
        catch (InterruptedException e)
            {
            e.printStackTrace();
            }
        
        Assert.assertEquals(false, data.getItemAdded());
        } 
    
    
    @Test
    public void failsToPlaceInBaggingDisabled ()
        {
        PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(selfCheckout);
        selfCheckout.baggingArea.attach(baggingArea);
        selfCheckout.mainScanner.attach(scansItem);
        selfCheckout.mainScanner.enable();
        
        BarcodedItem testItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), 100);
        selfCheckout.mainScanner.scan(testItem);
        
        try
            {
            TimeUnit.SECONDS.sleep(6);
            } 
        catch (InterruptedException e)
            {
            e.printStackTrace();
            }
        
        Assert.assertEquals(true, data.getIsDisabled());
        }
    
    @Test
    public void timerTaskItemAdded ()
        {
        data.setItemAdded(false);
        
        BaggingTimerTask x = new BaggingTimerTask();
        x.run();
        
        Assert.assertEquals(false, data.getItemAdded());
        }
    
    @Test
    public void timerTaskDisabled ()
        {
        data.setItemAdded(false);
        
        BaggingTimerTask x = new BaggingTimerTask();
        x.run();
        
        Assert.assertEquals(true, data.getIsDisabled());
        }
    
    @Test
    public void weightChanged_unscannedItem()
        {
        PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(selfCheckout);
        selfCheckout.baggingArea.attach(baggingArea);
        selfCheckout.mainScanner.attach(scansItem);
        selfCheckout.mainScanner.enable();
        
        BarcodedItem testItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), 100);
        selfCheckout.baggingArea.add(testItem);
        
        Assert.assertEquals(true, data.getIsDisabled());
        }
    
    @Test
    public void weightChanged_expectedWeight()
        {
        PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(selfCheckout);
        selfCheckout.baggingArea.attach(baggingArea);
        selfCheckout.mainScanner.attach(scansItem);
        selfCheckout.mainScanner.enable();
        
        BarcodedItem testItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), 100);
        
        selfCheckout.mainScanner.scan(testItem);
        System.out.println("after called");
        selfCheckout.baggingArea.add(testItem);
        
        Assert.assertEquals((double)100, baggingArea.getExpectedWeight(), 0.001);
        }
    
    @Test
    public void weightChanged_removedItem()
        {
        PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(selfCheckout);
        selfCheckout.baggingArea.attach(baggingArea);
        selfCheckout.mainScanner.attach(scansItem);
        selfCheckout.mainScanner.enable();
        
        BarcodedItem testItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), 100);
        selfCheckout.mainScanner.scan(testItem);
        selfCheckout.baggingArea.add(testItem);
        selfCheckout.baggingArea.remove(testItem);
        
        Assert.assertEquals(true, data.getIsDisabled());
        }
    
    
    @Test
    public void overload_isDisabled_true()
        {
        PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(selfCheckout);
        selfCheckout.baggingArea.attach(baggingArea);
        selfCheckout.mainScanner.attach(scansItem);
        selfCheckout.mainScanner.enable();
        
        BarcodedItem testItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), 2000);
        
        selfCheckout.mainScanner.scan(testItem);
        
        
        selfCheckout.baggingArea.add(testItem);
        
        Assert.assertEquals(true, data.getIsDisabled());
        }
    

    @Test
    public void outOfOverload_isDisabled_false()
        {
        PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(selfCheckout);
        selfCheckout.baggingArea.attach(baggingArea);
        selfCheckout.mainScanner.attach(scansItem);
        selfCheckout.mainScanner.enable();
        
        BarcodedItem testItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), 2000);
        selfCheckout.mainScanner.scan(testItem);
        
        selfCheckout.baggingArea.add(testItem);
        selfCheckout.baggingArea.remove(testItem);
        
        Assert.assertEquals(true, data.getIsDisabled());
        }
    
    @Test
    public void bagging_enabled_isDisabled_false()
        {
        PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(selfCheckout);
        selfCheckout.baggingArea.attach(baggingArea);
        selfCheckout.baggingArea.enable();
        Assert.assertEquals(false, data.getIsDisabled());
        }
    
    @Test
    public void bagging_disabled_isDisabled_true()
        {
        PlaceItemInBaggingArea baggingArea = new PlaceItemInBaggingArea(selfCheckout);
        selfCheckout.baggingArea.attach(baggingArea);
        selfCheckout.baggingArea.disable();
        Assert.assertEquals(true, data.getIsDisabled());
        }
    
    @Test
    public void scanning_enabled_isDisabled_false()
        {
        selfCheckout.mainScanner.attach(scansItem);
        selfCheckout.mainScanner.enable();
        Assert.assertEquals(false, data.getIsDisabled());
        }

    @Test
    public void scanning_disabled_isDisabled_true()
        {
        selfCheckout.mainScanner.attach(scansItem);
        selfCheckout.mainScanner.enable();
        Assert.assertEquals(true, data.getIsDisabled());
        }
    
    }
