package com.ftd.cart.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * Implementation class for Product Details Service
 * @author msi104
 *
 */
@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public RestTemplate restTemplate;

	@Value("${config.productService.dns}")
	private String productServicesDns;

	@Value("${config.productService.uri}")
	private String productDetailsServiceURI;

	/**
	 * <p>
	 * This method retrieves product details based on productId
	 * </p>
	 * 
	 * @param productId
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "handleInvokeProductService", commandKey = "Products-Retrieve-Failure", groupKey = "AddToCart-Service", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "600000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "5"), })
	@Override
	public JsonNode invokeProductService(String productId) {
		log.info("[Inside ProductServiceImpl.invokeProductService()] :: Product Id Passed is", productId);
		String productDetailsAPIUrl = productServicesDns + productDetailsServiceURI.replace("ids", productId);
		return restTemplate.getForObject(productDetailsAPIUrl, JsonNode.class);

	}

	/**
	 * <p>
	 * Fall back method for Hystrix which handles the failure of the services
	 * without breaking the functionality.
	 * </p>
	 * 
	 * @param productId
	 * @return ProductDetails
	 */
	public JsonNode handleInvokeProductService(String productId) {
		log.info("[Inside ProductDetailsServiceImpl.handleInvokeProductService()]");
		return null;
	}

}
