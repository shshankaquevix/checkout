package com.ftd.cart.service.strategy;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ftd.cart.vo.AddToCartRequest;
import com.ftd.cart.vo.AddToCartResponse;

/**
 * The Interface AddToCartStrategy.
 * @author Vikrant Shirbhate
 */
public interface AddToCartStrategy {

	/**
	 * Adds the to cart.
	 *
	 * @param addToCartRequest the add to cart request
	 * @param httpServletRequest the http servlet request
	 * @param httpServletResponse the http servlet response
	 * @return the optional
	 */
	Optional<AddToCartResponse> addToCart(AddToCartRequest addToCartRequest,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);
	
	/**
	 * Gets the site.
	 *
	 * @return the site
	 */
	String getSite();
}
