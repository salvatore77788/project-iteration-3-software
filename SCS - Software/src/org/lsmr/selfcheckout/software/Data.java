package org.lsmr.selfcheckout.software;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class Data
    {
    private static  Data p = new Data();
    private ArrayList <BarcodedProduct> barcoded_items = new ArrayList<BarcodedProduct>();
    private ArrayList <PLUCodedProduct> plu_items      = new ArrayList<PLUCodedProduct>();
    private ArrayList <BigDecimal>      barcoded_price = new ArrayList<BigDecimal>();
    private ArrayList <BigDecimal>      plu_price = new ArrayList<BigDecimal>();
    
    boolean plu_product_scanned = false;
    
    
    private Data()
    {
    
    }
    
    public static Data getInstance() {
    return p;
    }
    
    
    public ArrayList <BarcodedProduct> getBarcodeScanned()
        {
        return barcoded_items;
        }
    
    public ArrayList <PLUCodedProduct> getPluScanned()
        {
        return plu_items;
        }
    
    public ArrayList <BigDecimal> getBarcodedPrice()
        {
        return barcoded_price;
        }
    
    public ArrayList <BigDecimal> getPluPrice()
        {
        return plu_price;
        }
    
    public boolean get_plu_product_scanned()
        {
        return plu_product_scanned;
        }
    
    public void set_plu_product_scanned(boolean x)
        {
        plu_product_scanned = x;
        }
    
    }
