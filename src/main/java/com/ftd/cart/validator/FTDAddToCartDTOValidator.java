package com.ftd.cart.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ftd.cart.constants.BeanPropertyConstants;
import com.ftd.cart.dto.FTDAddToCartDTO;

@Component
public class FTDAddToCartDTOValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return FTDAddToCartDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PRODUCTTYPE, "error.MISSING_PRODUCT_TYPE", "product type is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PRODUCTID, "error.MISSING_PRODUCT_ID", "product id is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PRODUCTSUBTYPE, "error.MISSING_PRODUCT_SUBTYPE", "product subtype is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.SUBCODE, "error.MISSING_SUBCODE", "subcode is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.MARKCODE, "error.MISSING_MARKCODE", "markcode is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PRODUCTPRICE, "error.MISSING_PRODUCT_PRICE", "product price url is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.WESTPRODUCT, "error.MISSING_WEST_PRODUCT", "west product is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.AID, "error.MISSING_AID", "aid is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.DELIVERYDATE, "error.MISSING_DELIVER_DATE", "delivery date is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PASONLYSHIPPINGMETHOD, "error.MISSING_PAS_ONLY_SHIPPING_METHOD", "pas only shiping method is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PPDELLOC, "error.MISSING_PP_DEL_LOC", "delivery location is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.COUNTRYID, "error.MISSING_COUNTRY_ID", "country id is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PRODUCTCOUNTRY, "error.MISSING_PRODUCT_COUNTRY", "product country is empty");
		
	}

}
