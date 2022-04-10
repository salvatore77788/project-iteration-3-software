package org.lsmr.selfcheckout.software;
import java.math.BigDecimal;

public class ItemInfo {
	public BigDecimal price;
	public double weight;
	public String description;
	
	public ItemInfo(BigDecimal price, double weight, String description) {
		this.price = price;
		this.weight = weight;
		this.description = description;
	}
}
