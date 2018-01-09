package com.ftd.cart.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ftd.cart.constants.BeanPropertyConstants;
import com.ftd.cart.dto.PersonalizationElement;

@Component
public class ProflowersPersonalizationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PersonalizationElement.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PERSONALIZATIONTYPE, "error.MISSING_PERSONALIZATION_TYPE", "personalization Type is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PERSONALIZATIONNAME, "error.MISSING_PERSONALIZATION_NAME", "personalization Name is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PERSONALIZATIONVALUE, "error.MISSING_PERSONALIZATION_VALUE", "personalization Value is empty");
		
	}
}
