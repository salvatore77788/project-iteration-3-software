package org.lsmr.selfcheckout.software;

import java.util.ArrayList;

import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class Data
    {
    private static  Data p = new Data();
    private ArrayList <BarcodedProduct> barcoded_items = new ArrayList<BarcodedProduct>();
    private ArrayList <PLUCodedProduct> plu_items = new ArrayList<PLUCodedProduct>();
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
    
    }
