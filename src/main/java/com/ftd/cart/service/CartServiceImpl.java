package com.ftd.cart.service;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ftd.cart.service.factory.AddToCartFactory;
import com.ftd.cart.vo.AddToCartRequest;
import com.ftd.cart.vo.AddToCartResponse;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * The Class CartServiceImpl.
 *
 * @author Vikrant Shirbhate
 */

@Service("cartService")
public class CartServiceImpl implements CartService {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

	/** The executor. */
	private final ExecutorService executor = Executors.newCachedThreadPool();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ftd.cart.service.CartService#addToCart()
	 */
	@Override
	public Observable<AddToCartResponse> addToCart(AddToCartRequest addToCartRequest,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {

		String methodName = "addToCart";
		log.debug(methodName + "-start");

		Observable<Optional<AddToCartResponse>> addToCartResponse = cartResponse(addToCartRequest, httpServletRequest, httpServletResponse);
		
		log.debug(methodName + "-end");
		return Observable.zip(addToCartResponse, addToCartResponse, (s1, s2) -> {
			AddToCartResponse response = new AddToCartResponse();
			if (s1.isPresent()) {
				response.setOrderId(s1.get().getOrderId());
				response.setCookies(s1.get().getCookies());
				response.setRedirectUrl(s1.get().getRedirectUrl());
				response.setSuccessFlag(s1.get().isSuccessFlag());
			}
			return response;
		});

	}

	public Observable<Optional<AddToCartResponse>> cartResponse(AddToCartRequest addToCartRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return Observable
				.<Optional<AddToCartResponse>>create(subscriber -> subscriber.onNext((AddToCartFactory.getService(addToCartRequest.getSiteId())).addToCart(addToCartRequest,httpServletRequest,httpServletResponse)))
				.subscribeOn(Schedulers.from(executor));
	}
	

}
