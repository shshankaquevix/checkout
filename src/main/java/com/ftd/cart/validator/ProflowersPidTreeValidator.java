package com.ftd.cart.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ftd.cart.constants.BeanPropertyConstants;
import com.ftd.cart.dto.PidTree;

@Component
public class ProflowersPidTreeValidator implements Validator {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.validation.Validator#supports(java.lang.Class)
		 */
		@Override
		public boolean supports(Class<?> aClass) {
			return PidTree.class.equals(aClass);
		}

		@Override
		public void validate(Object target, Errors errors) {
			
			PidTree pidTree =  (PidTree)target;
			
			if(Objects.isNull(pidTree.getPid())){
				errors.rejectValue(BeanPropertyConstants.PID, "error.MISSING_CHILD_PID", "pid is empty");
			}
			if(Objects.isNull(pidTree.getOriginalSalePrice())){
				errors.rejectValue(BeanPropertyConstants.ORIGINALSALEPRICE, "error.MISSING_CHILD_ORIGINALSALEPRICE", "originalSalePrice is empty");
			}
		}


}
