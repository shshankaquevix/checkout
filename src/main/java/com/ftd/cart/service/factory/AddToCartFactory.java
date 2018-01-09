package com.ftd.cart.service.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftd.cart.service.strategy.AddToCartStrategy;
import com.ftd.commons.misc.constants.CommonConstants;

/**
 * A factory for creating AddToCart objects.
 * 
 * @author Vikrant Shirbhate
 */
@Component
public class AddToCartFactory {

	/** The services. */
	@Autowired
	private List<AddToCartStrategy> services;

	/** The Constant addToCartStrategyCache. */
	private static final Map<String, AddToCartStrategy> addToCartStrategyCache = new HashMap<>();

	/**
	 * Inits the add to cart service cache.
	 */
	@PostConstruct
	public void initAddToCartServiceCache() {
		for (AddToCartStrategy service : services) {
			if (org.apache.commons.lang.StringUtils.contains(service.getSite(), CommonConstants.DELIMETER_COMMA)) {
				String[] siteArray = StringUtils.split(service.getSite(), CommonConstants.DELIMETER_COMMA);
				for (String element : siteArray) {
					String siteId = StringUtils.trim(element);
					if (!addToCartStrategyCache.containsKey(siteId)) {
						addToCartStrategyCache.put(siteId, service);
					}
				}
			} else {
				String siteId = StringUtils.trim(service.getSite());
				if (!addToCartStrategyCache.containsKey(siteId)) {
					addToCartStrategyCache.put(siteId, service);
				}
			}
		}
	}

	/**
	 * Gets the service.
	 *
	 * @param siteId
	 *            the site id
	 * @return the service
	 */
	public static AddToCartStrategy getService(String siteId) {
		
		AddToCartStrategy service = addToCartStrategyCache.get(siteId);
		if (service == null)
			throw new RuntimeException("Unknown service siteId: " + siteId);
		return service;
	}
}