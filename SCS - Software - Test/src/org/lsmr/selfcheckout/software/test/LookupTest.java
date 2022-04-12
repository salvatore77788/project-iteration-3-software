package org.lsmr.selfcheckout.software.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import javax.swing.JFrame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.TouchScreen;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.software.LookupNoBarcode;

public class LookupTest
    {
    
    private LookupNoBarcode x;

    @Before
    public void setup() throws Exception {
        populateBarcodeDatabase();
        populatePluDatabase();
        x = new LookupNoBarcode();  
    }
    
   @Test
    public void test1searchbarcoded_inarray ()
        {
        ArrayList <BarcodedProduct> products = x.searchByNameBarcode("item1");
        Assert.assertEquals(1, products.size());
        }
   
   @Test
   public void testsearchplu_inarray ()
       {
       ArrayList <PLUCodedProduct> products = x.searchByNamePlu("item1");
       Assert.assertEquals(1, products.size());
       }
   
   @Test
    public void test1searchbarcoded_not_inarray ()
        {
        ArrayList <BarcodedProduct> products = x.searchByNameBarcode("item12");
        Assert.assertEquals(0, products.size());
        }
   
   @Test
   public void test1searchplu_not_inarray ()
       {
       ArrayList <PLUCodedProduct> products = x.searchByNamePlu("item12");
       Assert.assertEquals(0, products.size());
       }
   
   @Test
    public void plu_inarray ()
        {
        ArrayList <PLUCodedProduct> products = x.searchByPlu("0001");
        Assert.assertEquals(1, products.size());
        }
   @Test
    public void plu_notinarray ()
        {
        ArrayList <PLUCodedProduct> products = x.searchByPlu("0000");
        Assert.assertEquals(0, products.size());
        }
   
   
    
    
    public void populateBarcodeDatabase()
        {
        Numeral[] n = {Numeral.one, Numeral.two, Numeral.three, Numeral.four, Numeral.five, Numeral.six, Numeral.seven, Numeral.eight, Numeral.nine};
        for(int i = 1; i < 10; i++) {
            Numeral [] numerals = {n[i-1]};
            Barcode barcode = new Barcode (numerals);
            ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, new BarcodedProduct(barcode, ("item"+i), new BigDecimal(i*10), i*100));
        }
        }
    
    public void populatePluDatabase()
        {
        Numeral[] n = {Numeral.one, Numeral.two, Numeral.three, Numeral.four, Numeral.five, Numeral.six, Numeral.seven, Numeral.eight, Numeral.nine};
            ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode ("0001"), new PLUCodedProduct(new PriceLookupCode ("0001"), "item1", new BigDecimal(10)));
            ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode ("0002"), new PLUCodedProduct(new PriceLookupCode ("0002"), "item2", new BigDecimal(10)));
            ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode ("0003"), new PLUCodedProduct(new PriceLookupCode ("0003"), "item3", new BigDecimal(10)));
            ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode ("0004"), new PLUCodedProduct(new PriceLookupCode ("0004"), "item4", new BigDecimal(10)));
        }  
    
    
    }
