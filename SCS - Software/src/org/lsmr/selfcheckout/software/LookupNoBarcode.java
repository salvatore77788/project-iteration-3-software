import java.util.ArrayList;

import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class LookupNoBarcode
{
    public ArrayList <BarcodedProduct> searchByNameBarcode(String userInput)
    {
        ArrayList <BarcodedProduct> products = new ArrayList<BarcodedProduct>();

        userInput = userInput.toLowerCase();

        for (BarcodedProduct value : ProductDatabases.BARCODED_PRODUCT_DATABASE.values())
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

    public ArrayList <PLUCodedProduct> searchByPlu(String userInput)
    {
        userInput = userInput.toLowerCase();
        ArrayList <PLUCodedProduct> products = new ArrayList<PLUCodedProduct>();

        PriceLookupCode pluInput = new PriceLookupCode(userInput);

        for (PLUCodedProduct value : ProductDatabases.PLU_PRODUCT_DATABASE.values())
        {
            PriceLookupCode plu = value.getPLUCode();
            if (plu.equals(pluInput))
            {
                products.add(value);
                System.out.println(value.getDescription());
            }
        }
        return products;
    }

    public ArrayList <PLUCodedProduct> searchByNamePlu(String userInput)
    {
        userInput = userInput.toLowerCase();
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


}
