package com.ftd.cart.service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Interface class for product details service
 * @author Neeraja Jha
 *
 */
public interface ProductDetailsService{

	
	JsonNode invokeProductService(String productId);



}
