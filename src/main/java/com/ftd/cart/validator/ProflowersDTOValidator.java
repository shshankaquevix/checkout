package com.ftd.cart.validator;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ftd.cart.constants.AddToCartConstants;
import com.ftd.cart.constants.BeanPropertyConstants;
import com.ftd.cart.dto.PBContext;
import com.ftd.cart.dto.PBResult;
import com.ftd.cart.dto.PersonalizationElement;
import com.ftd.cart.dto.PidTree;
import com.ftd.cart.dto.ProflowersAddToCartDTO;

@Component
public class ProflowersDTOValidator implements Validator {

	
	@Autowired
	ProflowersPersonalizationValidator proflowersPersonalizationValidator;
	
	@Autowired
	ProflowersPidTreeValidator proflowersPidTreeValidator;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> aClass) {
		return ProflowersAddToCartDTO.class.equals(aClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object o, Errors errors) {
		ProflowersAddToCartDTO proflowersAddToCartDTO = (ProflowersAddToCartDTO) o;

		//validate PBContext properties
		PBContext pbContext = proflowersAddToCartDTO.getPBContext();
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PBCONTEXT_EXTERNALPAGEURL, "error.MISSING_PAGE_URL", "page url is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PBCONTEXT_PARTNERCODE, "error.MISSING_PARTNER_CODE", "partner code is empty");
		if(Objects.isNull(pbContext.getCurrentRootPid())){
			errors.rejectValue(BeanPropertyConstants.PBCONTEXT_CURRENTROOTPID, "error.MISSING_ROOT_PID", "Current root pid is empty");
		}

		//validate PBResult properties
		PBResult pbResult = proflowersAddToCartDTO.getPBResult();

		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PBRESULT_DELIVERYDATE, "error.MISSING_DELIVERY_DATE", "DeliveryDate is empty");
		ValidationUtils.rejectIfEmpty(errors, BeanPropertyConstants.PBRESULT_ZIPCODE, "error.MISSING_ZIP_CODE", "zip code is empty");
		if(Objects.isNull(pbResult.getTotalQuantity())){
			errors.rejectValue(BeanPropertyConstants.PBRESULT_TOTALQUANTITY, "error.MISSING_ROOT_TOTALQUANTITY", "totalQuantity is empty");
		}

		//validate PidTree properties
		PidTree pidTree =  pbResult.getPidTree();
		errors.pushNestedPath(BeanPropertyConstants.PIDTREE);
		ValidationUtils.invokeValidator(this.proflowersPidTreeValidator, pidTree, errors);
		errors.popNestedPath();

		//validate children
		List<PidTree> children = pidTree.getChildren();
		
		if(!CollectionUtils.isEmpty(children)){
			validateNestedCollectionProperties(errors, children);
		}
	}
    
	/**
	 * To validate nested collection properties.
	 * @param errors
	 * @param children
	 */
	private void validateNestedCollectionProperties(Errors errors, List<PidTree> children) {
		AtomicInteger outerCounter = new AtomicInteger();
		children.forEach(child -> {
			
			//format example pBResult.pidTree.children[0]
			errors.pushNestedPath(String.format(AddToCartConstants.ONE_DIMENSIONAL_ARRAY_FORMAT,
					                            BeanPropertyConstants.PBRESULT_PIDTREE_CHILDREN,
					                            outerCounter.getAndIncrement()));
			ValidationUtils.invokeValidator(this.proflowersPidTreeValidator, child, errors);
			errors.popNestedPath();
			
			List<PersonalizationElement> personalizationElements = child.getPersonalizationElements();
			AtomicInteger innerCounter = new AtomicInteger();
			if(!CollectionUtils.isEmpty(personalizationElements)){

				personalizationElements.forEach(personalizationElement -> {
					
					//format example pBResult.pidTree.children[0].personalizationElements[0]
					errors.pushNestedPath(String.format(AddToCartConstants.TWO_DIMENSIONAL_ARRAY_FORMAT, 
							                            BeanPropertyConstants.PBRESULT_PIDTREE_CHILDREN,
							                            innerCounter.getAndIncrement(),
							                            BeanPropertyConstants.PERSONALIZATIONELEMENTS, 
							                            innerCounter.getAndIncrement()));
					ValidationUtils.invokeValidator(this.proflowersPersonalizationValidator, personalizationElement, errors);
					errors.popNestedPath();
					
				});
			}
		});
	}
	
}
