package com.ftd.cart.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ftd.cart.vo.AddToCartRequest;
import com.ftd.cart.vo.AddToCartResponse;

import rx.Observable;

/**
 * Cart service interface
 * @author Vikrant Shirbhate
 */

public interface CartService {

	public Observable<AddToCartResponse> addToCart(AddToCartRequest addToCartRequest,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException;

}
