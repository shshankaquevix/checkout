package com.ftd.cart.vo;

/**
 * The Class SkuRequest.
 * @author Vikrant Shirbhate
 */
public class SkuRequest {
	
	/** The sku id. */
	String 	skuId;
	
	/** The quantity. */
	String 	quantity;
	
	/**
	 * Gets the sku id.
	 *
	 * @return the sku id
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * Sets the sku id.
	 *
	 * @param skuId the new sku id
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SkuRequest [skuId=" + skuId + ", quantity=" + quantity + "]";
	}
	
}