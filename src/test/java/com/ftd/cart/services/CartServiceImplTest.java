/**
 * 
 */
package com.ftd.cart.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftd.cart.service.CartServiceImpl;
import com.ftd.cart.service.strategy.FTDAddToCartStrategy;
import com.ftd.cart.vo.AddToCartRequest;
import com.ftd.cart.vo.AddToCartResponse;

import rx.Observable;

/**
 * @author sanan7
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceImplTest {

	@Mock
	CartServiceImpl cartServiceImpl;

	@Mock
	FTDAddToCartStrategy fTDAddToCartStrategy;

	private AddToCartRequest addToCartRequest;
	private AddToCartResponse addToCartResponse;

	private AddToCartRequest addToCartRequestP;
	private AddToCartResponse addToCartResponseP;

	private Observable<Optional<AddToCartResponse>> response;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		addToCartRequest = new AddToCartRequest();
		addToCartRequest.setSiteId("ftd");

		addToCartResponse = new AddToCartResponse();
		addToCartResponse.setSuccessFlag(true);
		addToCartResponse.setItemId(null);
		addToCartResponse.setOrderId("123");
		addToCartResponse.setRedirectUrl("https://ordering.ftd.com/shopcart/");

		addToCartRequestP = new AddToCartRequest();
		addToCartRequestP.setSiteId("pro");

		addToCartResponseP = new AddToCartResponse();
		addToCartResponseP.setSuccessFlag(true);
		addToCartResponseP.setOrderId(
				"{\"Result\":{\"SubmissionStatus\":0,\"RedirectUrl\":\"https://cart.proflowers.com/?Ref=catalogPID&cobrand=pfc&op=new&ssid=62&trackingpgroup=ffflg&deliverydate=12%2f21%2f2017&flexShown=False&ShowGiftOptions=True&quantity=1&pid=30189425&scAddItem=true&deliveryon=True\",\"CartItem\":null},\"GeneralStatus\":\"Success\"}");
		addToCartResponseP.setRedirectUrl(
				"https://cart.proflowers.com/?Ref=catalogPID&cobrand=pfc&op=new&ssid=62&trackingpgroup=ffflg&deliverydate=12%2f21%2f2017&flexShown=False&ShowGiftOptions=True&quantity=1&pid=30189425&scAddItem=true&deliveryon=True");
	}


	/**
	 * Test method for
	 * {@link com.ftd.cart.service.CartServiceImpl#addToCart(com.ftd.cart.vo.AddToCartRequest, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddToCartFtd() throws IOException {

		Observable<AddToCartResponse> addToCartResponse1;
		response = Observable.just(Optional.of(addToCartResponse));

		cartServiceImpl = new CartServiceImpl();
		CartServiceImpl spy = Mockito.spy(cartServiceImpl);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

		Mockito.when(spy.cartResponse(addToCartRequest, httpServletRequest, httpServletResponse)).thenReturn(response);

		addToCartResponse1 = spy.addToCart(addToCartRequest, httpServletRequest, httpServletResponse);
		addToCartResponse1.subscribe(s -> {
			assertTrue(s.getRedirectUrl().equals("https://ordering.ftd.com/shopcart/"));
			assertTrue(s.getOrderId().equals("123"));
		});
	}

	@Test
	public void testAddToCartProflowers() throws IOException {

		Observable<AddToCartResponse> addToCartResponse1;
		response = Observable.just(Optional.of(addToCartResponseP));

		cartServiceImpl = new CartServiceImpl();
		CartServiceImpl spy = Mockito.spy(cartServiceImpl);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

		Mockito.when(spy.cartResponse(addToCartRequestP, httpServletRequest, httpServletResponse)).thenReturn(response);

		addToCartResponse1 = spy.addToCart(addToCartRequestP, httpServletRequest, httpServletResponse);
		addToCartResponse1.subscribe(s -> {
		    assertNotNull(s.getRedirectUrl());
			assertNotNull(s.getOrderId());
		});
	}

}
