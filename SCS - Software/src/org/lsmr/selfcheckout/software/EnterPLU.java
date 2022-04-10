package org.lsmr.selfcheckout.software;

import java.util.ArrayList;

import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class EnterPLU {

	public ArrayList <PLUCodedProduct> SearchByPLU(String userInput)
    {
    ArrayList <PLUCodedProduct> products = new ArrayList<PLUCodedProduct>();
    
    for (PLUCodedProduct value : ProductDatabases.PLU_PRODUCT_DATABASE.values()) 
        {
        String productString = value.getDescription();
        if (productString.contains(userInput))
            {
            products.add(value);
            System.out.println(value.getDescription());
            }
        }
    return products;
    }
	
	public void AddtoCheckout(PLUCodedProduct product) {
		// Add to checkout method with product (currently not available in pt branch)
	}
	
}
