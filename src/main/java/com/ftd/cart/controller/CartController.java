package com.ftd.cart.controller;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.ftd.cart.service.CartService;
import com.ftd.cart.validator.CartRequestValidator;
import com.ftd.cart.vo.AddToCartRequest;
import com.ftd.cart.vo.AddToCartResponse;
import com.ftd.commons.misc.exception.BusinessException;
import com.ftd.commons.misc.exception.InputValidationException;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * The Class CartController.
 * 
 * @author Vikrant Shirbhate
 */
@RestController
public class CartController {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(CartController.class);
	/** The service. */
	@Autowired
	private CartService service;

	/** The validator. */

	@Autowired
	private CartRequestValidator validator;

	/** The item executor. */
	private final ExecutorService itemExecutor = Executors.newCachedThreadPool();

	/**
	 * Inits the binder.
	 *
	 * @param addToCartRequest
	 *            the add to cart request
	 * @param bindingResult
	 *            the binding result
	 * @return the deferred result
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */

	@InitBinder("addToCartRequest")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = { "/cart/addtocart" }, method = { RequestMethod.POST }, produces = "application/json")
	@ExceptionMetered
	@Timed
	public DeferredResult<ResponseEntity<AddToCartResponse>> addtocart(
			 @Validated AddToCartRequest addToCartRequest, BindingResult bindingResult,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

		final String methodName = "addToCart";
		log.debug(methodName + "-start");

		if (bindingResult.hasErrors()) {

			throw new InputValidationException("BAD_REQUEST", "Invalid JSON input", bindingResult);
		}

		if (null != addToCartRequest && !(StringUtils.equalsIgnoreCase(addToCartRequest.getSiteId(), "pro")
				|| StringUtils.equalsIgnoreCase(addToCartRequest.getSiteId(), "ftd"))) {
			throw new BusinessException("100", "Invalid SiteId! Please choose correct Site ID");
		}

		DeferredResult<ResponseEntity<AddToCartResponse>> response = new DeferredResult<>();
		
		Observable<AddToCartResponse> cartResponseObservable = service
				.addToCart(addToCartRequest, httpServletRequest, httpServletResponse)
				.subscribeOn(Schedulers.from(itemExecutor));
		cartResponseObservable.subscribe(result -> {

			HttpHeaders header = new HttpHeaders();
			result.getCookies().forEach(s -> {

				header.add("Set-Cookie", s.getName() + ":" + s.getValue());
			});
			ResponseEntity<AddToCartResponse> responseEntity = new ResponseEntity<>(result, header, HttpStatus.OK);
			response.setResult(responseEntity);
		});
		log.debug(methodName + "-end");
		return response;
	}
}