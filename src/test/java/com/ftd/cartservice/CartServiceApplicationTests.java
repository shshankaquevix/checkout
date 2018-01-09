package com.ftd.cartservice;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.ftd.cart.CartServiceApplication;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartServiceApplication.class)
public class CartServiceApplicationTests {

	
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(CartServiceApplicationTests.class);

	@Test
	public void contextLoads() {
		
		String methodName = "contextLoads";
		log.debug(methodName + "-start");
	}

}
