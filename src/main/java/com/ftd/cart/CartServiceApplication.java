package com.ftd.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * The Class CartServiceApplication.
 * @author Vikrant Shirbhate
 */
@SpringBootApplication
(scanBasePackages={
"com.ftd.commons.misc", "com.ftd.cart"})
@EnableCircuitBreaker
public class CartServiceApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CartServiceApplication.class, args);
	}
	
	/**
	 * A rest template bean initialized at the boot start up .
	 * 
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
