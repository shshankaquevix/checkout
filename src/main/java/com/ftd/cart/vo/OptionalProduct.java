package com.ftd.cart.vo;

import java.util.List;

/**
 * The Class OptionalProduct.
 * @author Mohit Singh
 */
public class OptionalProduct {

	/**The type **/
	private String type;

	/**The productId **/
	private String productId;

	/**The personalization **/
	private List<Personalization> personalizations;

	/**The quantity **/
	private String quantity;

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the personalizations
	 */
	public List<Personalization> getPersonalizations() {
		return personalizations;
	}

	/**
	 * @param personalizations the personalizations to set
	 */
	public void setPersonalizations(List<Personalization> personalizations) {
		this.personalizations = personalizations;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OptionalProduct [productType=" + type + ", productId=" + productId + ", personalizationElements="
				+ personalizations + ", quantity=" + quantity + "]";
	}
}
