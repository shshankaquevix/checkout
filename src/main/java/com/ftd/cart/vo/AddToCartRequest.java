package com.ftd.cart.vo;

import java.util.List;

/**
 * The Class AddToCartRequest.
 * 
 * @author Vikrant Shirbhate
 */
public class AddToCartRequest {
	
	/** The cart id. */
	String cartId;

	String productId;
	
	String productType;
	
	/** The sku id. */
	String skuId;

	/** The delivery date. */
	String deliveryDate;

	/** The quantity. */
	String quantity;

	/** The zip code. */
	String zipCode;

	/** The deliverylocation type. */
	String deliverylocationType;

	/** The site id. */
	String siteId;
	
	/** delivery method based on the selected date **/
	String productDeliveryMethod;
	
	/** personalization elements**/
	private List<Personalization> personalizations;
	
	/** optional products**/
	private List<OptionalProduct> optionalProducts;

	/**
	 * Gets the cart id.
	 *
	 * @return the cart id
	 */
	public String getCartId() {
		return cartId;
	}

	/**
	 * Sets the cart id.
	 *
	 * @param cartId
	 *            the new cart id
	 */
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	/**
	 * Gets the site id.
	 *
	 * @return the site id
	 */
	public String getSiteId() {
		return siteId;
	}

	/**
	 * Sets the site id.
	 *
	 * @param siteId
	 *            the new site id
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
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
	 * @param quantity
	 *            the new quantity
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

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
	 * @param skuId
	 *            the new sku id
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	/**
	 * Gets the delivery date.
	 *
	 * @return the delivery date
	 */
	public String getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * Sets the delivery date.
	 *
	 * @param deliveryDate
	 *            the new delivery date
	 */
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * Gets the zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode
	 *            the new zip code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Gets the deliverylocation type.
	 *
	 * @return the deliverylocation type
	 */
	public String getDeliverylocationType() {
		return deliverylocationType;
	}

	/**
	 * Sets the deliverylocation type.
	 *
	 * @param deliverylocationType
	 *            the new deliverylocation type
	 */
	public void setDeliverylocationType(String deliverylocationType) {
		this.deliverylocationType = deliverylocationType;
	}



	/**
	 * @return the productDeliveryMethod
	 */
	public String getProductDeliveryMethod() {
		return productDeliveryMethod;
	}

	/**
	 * @param productDeliveryMethod the productDeliveryMethod to set
	 */
	public void setProductDeliveryMethod(String productDeliveryMethod) {
		this.productDeliveryMethod = productDeliveryMethod;
	}

	/**
	 * @return the personalizationElements
	 */
	public List<Personalization> getPersonalizations() {
		return personalizations;
	}

	/**
	 * @param personalizationElements the personalizationElements to set
	 */
	public void setPersonalizationElements(List<Personalization> personalizations) {
		this.personalizations = personalizations;
	}

	/**
	 * @return the optionalProducts
	 */
	public List<OptionalProduct> getOptionalProducts() {
		return optionalProducts;
	}

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
	 * @param optionalProducts the optionalProducts to set
	 */
	public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
		this.optionalProducts = optionalProducts;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddToCartRequest [cartId=" + cartId + ", skuId=" + skuId + ", deliveryDate=" + deliveryDate
				+ ", quantity=" + quantity + ", zipCode=" + zipCode + ", deliverylocationType=" + deliverylocationType
				+ ", siteId=" + siteId + ", productDeliveryMethod=" + productDeliveryMethod
				+ ", personalizationElements=" + personalizations + ", optionalProducts=" + optionalProducts
				+ "]";
	}
}