package com.ftd.cart.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ftd.cart.vo.AddToCartRequest;

/**
 * Cart Request Validator
 * 
 * @author Vikrant Shirbhate
 */

@Component
public class CartRequestValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> aClass) {
		return AddToCartRequest.class.equals(aClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object o, Errors errors) {
		AddToCartRequest request = (AddToCartRequest) o;
		
		if (StringUtils.isEmpty(request.getSiteId())) {	
			errors.rejectValue("siteId", "error.MISSING_SITE_ID", "Site field is empty");			
		}
		
		if (StringUtils.isEmpty(request.getSkuId())) {			
			errors.rejectValue("skuId", "error.MISSING_SKU_ID", "Input SKU field is empty");
		}
	}
}