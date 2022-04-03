package org.lsmr.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.lsmr.selfcheckout.products.Product;

/**
 * Represents a virtual shopping cart that contains items. These items are determined by a
 * product instance and a weight in grams.
 */
public class ShoppingCart {

    private boolean itemAdded = false;
    private boolean isDisabled = false;
    
	/**
	 * Represents a single entry into the virtual shopping cart.
	 */
	public class ShoppingCartEntry {
		private Product product;
		private BigDecimal weightInGrams;
		
		/**
		 * Create a new shopping cart entry instance.
		 * 
		 * @param product
		 * 			The product instance associated with an item to add.
		 * @param weightInGrams
		 * 			The weight of the item.
		 */
		public ShoppingCartEntry(Product product, double weightInGrams) {
			this.product = product;
			this.weightInGrams = new BigDecimal(weightInGrams);
		}
		
		/**
		 * Gets the price of the entry
		 * 
		 * @return The price of the entry, determined by the product type and the weight.
		 */
		public BigDecimal getPrice() {
			return product.isPerUnit() ? product.getPrice() : product.getPrice().multiply(weightInGrams);
		}
		
		/**
		 * Gets the product.
		 * 
		 * @return The product in this entry.
		 */
		public Product getProduct() {
			return product;
		}
		
		/**
		 * Gets the weight of the entry.
		 * 
		 * @return The weight of the entry in grams as a BigDecimal.
		 */
		public BigDecimal getWeight() {
			return weightInGrams;
		}
	}
	
	public static ShoppingCart Instance = new ShoppingCart();
	
	/**
	 * Returns the singleton instance of shopping cart
	 * @return The shopping cart singleton.
	 */
	public static ShoppingCart getInstance() {
		return Instance;
	}
	
	private ArrayList<ShoppingCartEntry> cart;
	
	ShoppingCart() {
		cart = new ArrayList<ShoppingCartEntry>();
	}
	
	/**
	 * Adds a new item into the shopping cart.
	 * 
	 * @param product The product instance.
	 * @param d The weight of the entry in grams.
	 */
	public void Add(Product product, double d) {
		cart.add(new ShoppingCartEntry(product, d));
	}
	
	/**
	 * Retrieves the total price of the shopping cart.
	 * 
	 * @return The total price of all items in the shopping cart.
	 */
	public BigDecimal getTotalPrice() {
		BigDecimal totalPrice = BigDecimal.ZERO;
		for(ShoppingCartEntry entry : cart) {
			totalPrice = totalPrice.add(entry.getPrice());
		}
		
		return totalPrice;
	}
	
	/**
	 * Empties the shopping cart of all items.
	 */
	public void Empty() {
		cart.clear();
	}
	
	public ArrayList<ShoppingCartEntry> getEntries() {
		return cart;
	}
	
}
