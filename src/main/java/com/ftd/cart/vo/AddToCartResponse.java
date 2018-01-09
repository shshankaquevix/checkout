package com.ftd.cart.vo;

import java.util.List;

import org.apache.http.cookie.Cookie;

/**
 * The Class AddToCartRequest.
 * @author Vikrant Shirbhate
 * 
 */
public class AddToCartResponse  {

	/** The cookies. */
	private List<Cookie> cookies;
	
	/** The success flag. */
	private boolean successFlag;
	
	/** The order id. */
	private String orderId;

	/** The item id. */
	private String itemId;
	
	/** The redirectUrl for cart page */
	private String redirectUrl; 

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	/**
	 * Checks if is success flag.
	 *
	 * @return true, if is success flag
	 */
	public boolean isSuccessFlag() {
		return successFlag;
	}

	/**
	 * Sets the success flag.
	 *
	 * @param successFlag
	 *            the new success flag
	 */
	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * Sets the order id.
	 *
	 * @param orderId
	 *            the new order id
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * Sets the item id.
	 *
	 * @param itemId
	 *            the new item id
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the redirectUrl
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * @param redirectUrl the redirectUrl to set
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}