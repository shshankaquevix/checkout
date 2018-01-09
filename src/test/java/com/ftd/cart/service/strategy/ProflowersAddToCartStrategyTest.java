/**
 * 
 */
package com.ftd.cart.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.cookie.Cookie;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.ftd.cart.vo.AddToCartRequest;
import com.ftd.cart.vo.AddToCartResponse;

/**
 * @author sanan7
 *
 */
//@RunWith(SpringRunner.class)
@SpringBootTest  
public class ProflowersAddToCartStrategyTest {
	
	@Mock
	ProflowersAddToCartStrategy proflowersAddToCartStrategy;
	
	private AddToCartRequest addToCartRequest;
	private Cookie cookie;
	
	
	@Before
	public void setUp(){
		addToCartRequest = new AddToCartRequest();
		addToCartRequest.setSiteId("pro");
	}

	/**
	 * Test method for {@link com.ftd.cart.service.strategy.ProflowersAddToCartStrategy#addToCart(com.ftd.cart.vo.AddToCartRequest, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	//@Test
	public void testAddToCart() {

		proflowersAddToCartStrategy = new ProflowersAddToCartStrategy();
		ProflowersAddToCartStrategy spy = Mockito.spy(proflowersAddToCartStrategy);
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		Mockito.when(spy.getPostURL()).thenReturn("https://www.proflowers.com/product/webapi/StepFlowAPI/ValidateAndSubmitOrder");
		Optional<AddToCartResponse> addToCartResponse = spy.addToCart(addToCartRequest, mockedRequest,
				httpServletResponse);
		if(addToCartResponse.isPresent()) {
			assertNotNull(addToCartResponse.get().getOrderId(), addToCartResponse);
			assertTrue(addToCartResponse.get().isSuccessFlag());
		}
		
	}
	
	//@Test
	public void testAddtoCartResponseCookie() {

		proflowersAddToCartStrategy = new ProflowersAddToCartStrategy();
		ProflowersAddToCartStrategy spy = Mockito.spy(proflowersAddToCartStrategy);
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		Mockito.when(spy.getPostURL())
				.thenReturn("https://www.proflowers.com/product/webapi/StepFlowAPI/ValidateAndSubmitOrder");
		Optional<AddToCartResponse> addToCartResponse = spy.addToCart(addToCartRequest, mockedRequest,
				httpServletResponse);
		if (addToCartResponse.isPresent()) {
			for (Cookie cookie1 : addToCartResponse.get().getCookies()) {
				if (cookie1.getName().equals("PRVD")) {
					cookie = cookie1;
					break;
				}
			}
		}
		assertEquals(cookie.getDomain(), "proflowers.com");
		assertEquals(cookie.getPath(), "/");
	}

}
