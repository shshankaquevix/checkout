package com.ftd.cart.service.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftd.cart.constants.AddToCartConstants;
import com.ftd.cart.dto.PBContext;
import com.ftd.cart.dto.PBResult;
import com.ftd.cart.dto.PersonalizationElement;
import com.ftd.cart.dto.PidTree;
import com.ftd.cart.dto.ProflowersAddToCartDTO;
import com.ftd.cart.service.ProductDetailsService;
import com.ftd.cart.validator.ProflowersDTOValidator;
import com.ftd.cart.vo.AddToCartRequest;
import com.ftd.cart.vo.AddToCartResponse;
import com.ftd.cart.vo.OptionalProduct;
import com.ftd.cart.vo.Personalization;
import com.ftd.commons.misc.exception.InputValidationException;
import com.ftd.commons.misc.util.CommonUtils;

/**
 * The Class ProflowersAddToCartStrategy.
 * 
 * @author Vikrant Shirbhate
 * 
 */
@Component
public class ProflowersAddToCartStrategy implements AddToCartStrategy {

	/** The site. */
	@Value("${config.ProflowersAddToCartStrategy.site}")
	String site;

	/** The post URL. */
	@Value("${config.ProflowersAddToCartStrategy.postURL}")
	String postURL;

	@Autowired
	ProflowersDTOValidator proflowersDTOValidator;

	@Autowired
	ProductDetailsService productDetailsService;

	/** The domain. */
	@Value("${config.ProflowersAddToCartStrategy.domain}")
	String domain;

	@Autowired
	private ApplicationContext applicationContext;

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(ProflowersAddToCartStrategy.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ftd.cart.service.strategy.AddToCartStrategy#addToCart(com.ftd.cart.vo.
	 * AddToCartRequest, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Optional<AddToCartResponse> addToCart(AddToCartRequest addToCartRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		final String methodName = "addToCartProFlowers";
		if (log.isDebugEnabled()) {
			log.debug(methodName + "-start");
		}
		AddToCartResponse addToCartResponse = new AddToCartResponse();

		List<OptionalProduct> optionalProducts = addToCartRequest.getOptionalProducts();
		Optional<OptionalProduct> optionalProduct = null;
		List<JsonNode> productNodes = new ArrayList<JsonNode>();
		String responseBody = null;
		
		try {

			productNodes.add(new ObjectMapper().readTree(productData.get(addToCartRequest.getProductId())));

			if (!CollectionUtils.isEmpty(optionalProducts)) {
				optionalProduct = optionalProducts.stream().filter(product -> product.getType().equals("vase"))
						.findFirst();
				if (optionalProduct.isPresent()) {
					productNodes
							.add(new ObjectMapper().readTree(productData.get(optionalProduct.get().getProductId())));
				}
			}

			// JsonNode productNodes =
			// productDetailsService.invokeProductService(productIds);

			ProflowersAddToCartDTO proflowersAddToCartDTO = createAddToCartDTO(addToCartRequest, productNodes);
			
			HttpClientContext context = new HttpClientContext();

			ObjectMapper addToCartDTOObjectMapper = new ObjectMapper();
			String proflowersAddToCartDTOAsString = null;

			proflowersAddToCartDTOAsString = addToCartDTOObjectMapper.writeValueAsString(proflowersAddToCartDTO);
			log.info("Inside  addToCartProFlowers(String jsonString) method ::" + proflowersAddToCartDTOAsString);

			CloseableHttpClient httpclient = (CloseableHttpClient) applicationContext.getBean("httpClient");

			StringEntity entity_str = new StringEntity(proflowersAddToCartDTOAsString);

			HttpPost httpPost = new HttpPost(getPostURL());

			BasicCookieStore basicCookieStore = new BasicCookieStore();

			basicCookieStore.addCookies(CommonUtils.getHttpClientCookies(httpServletRequest.getCookies(), getDomain()));
			// send cookies from request
			context.setCookieStore(basicCookieStore);

			httpPost.setEntity(entity_str);
			httpPost.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			// Create a custom response handler
			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				log.debug("status returned =" + status);
				if (status >= 200 && status < 400) {
					HttpEntity responseEntity = response.getEntity();
					return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			responseBody = httpclient.execute(httpPost, responseHandler, context);
			
			setAddToCartResponseFromCookie(httpServletResponse, addToCartResponse, context);

			final ObjectMapper objectMapper = new ObjectMapper();
			JsonNode json = objectMapper.readValue(responseBody, JsonNode.class);
			;
			Optional<JsonNode> jsonNode = Optional.ofNullable(json)
					.map(node -> node.get(AddToCartConstants.RESPONSE_RESULT))
					.map(node -> node.get(AddToCartConstants.RESPONSE_REDIRECTURL));
			if (jsonNode.isPresent()) {
				addToCartResponse.setRedirectUrl(jsonNode.get().asText());
			}
			log.debug("----------------------------------------");
			log.debug(responseBody);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		log.debug(methodName + "-end");
		return Optional.of(addToCartResponse);
	}


	/**
	 * @param addToCartRequest
	 * @param productNodes
	 * @param proflowersAddToCartDTO
	 * @return
	 */
	private ProflowersAddToCartDTO createAddToCartDTO(AddToCartRequest addToCartRequest,
			List<JsonNode> productNodes) {
		
		ProflowersAddToCartDTO proflowersAddToCartDTO = null;
		
		if (productNodes != null) {
			proflowersAddToCartDTO = populateAddToCartDTO(addToCartRequest, productNodes);
			DataBinder binder = new DataBinder(proflowersAddToCartDTO);
			binder.setValidator(proflowersDTOValidator);
			binder.validate();
			BindingResult bindingResult = binder.getBindingResult();
			if (bindingResult.hasErrors()) {
				throw new InputValidationException("BAD_REQUEST", "Invalid JSON input", bindingResult);
			}
		}
		return proflowersAddToCartDTO;
	}

	
	/**
	 * This method sets the addToCartResponse taking from cookie.
	 * 
	 * @param httpServletResponse
	 * @param addToCartResponse
	 * @param context
	 */
	private void setAddToCartResponseFromCookie(HttpServletResponse httpServletResponse,
			AddToCartResponse addToCartResponse, HttpClientContext context) {
		String orderId = null;
		Optional<CookieStore> optionalCookieStore = Optional.ofNullable(context.getCookieStore());

		Optional<List<Cookie>> optionalCookies = optionalCookieStore.map(magicCookies -> magicCookies.getCookies());

		if (optionalCookies.isPresent()) {
			optionalCookies.get().stream()
					.forEach(cookie -> httpServletResponse.addCookie(CommonUtils.createCookieFromMagicCookie(cookie)));

			List<Cookie> optionalRequiredCookie = getRequiredCookie(optionalCookies.get());
			
			addToCartResponse.setCookies(optionalRequiredCookie);

			Optional<Cookie> optionalCookie = optionalCookies.get().stream()
					.filter(cookie -> cookie.getName().equalsIgnoreCase(AddToCartConstants.PFC_SHOPPINGCARTCONTENTID))
					.findFirst();
			if (optionalCookie.isPresent()) {
				orderId = optionalCookie.get().getValue();
			}
			
		}
		addToCartResponse.setOrderId(orderId);

		if (StringUtils.isNotBlank(orderId)) {
			addToCartResponse.setSuccessFlag(Boolean.TRUE);
		} else {
			addToCartResponse.setSuccessFlag(Boolean.FALSE);
		}
	}

	
	/**
	 * Method to remove cookies without create date.
	 * @param optionalCookies
	 * @return List<Cookie>
	 */
	private List<Cookie> getRequiredCookie(List<Cookie> optionalCookies) {
		List<Cookie> listCookie = new ArrayList<Cookie>();
		optionalCookies.forEach(cookie->{
			if(((BasicClientCookie)cookie).getCreationDate() != null){
				
				listCookie.add(cookie);
			}
		});
		return listCookie;
	}
	
	/**
	 * Method to convert an Apache HttpClient cookie to a Java Servlet cookie.
	 * 
	 * @param magicCookie
	 *            the source apache cookie
	 * @return a java servlet cookie
	 */
	public  javax.servlet.http.Cookie createCookieFromMagicCookie(org.apache.http.cookie.Cookie magicCookie) {

		Optional<org.apache.http.cookie.Cookie> optionalCookie = Optional.ofNullable(magicCookie);
		if (magicCookie == null) {
			return null;
		}

		String name = optionalCookie.map(cookie -> cookie.getName()).orElse(null);
		String value = optionalCookie.map(cookie -> cookie.getValue()).orElse(null);

		javax.servlet.http.Cookie responseCookie = new javax.servlet.http.Cookie(name, value);
		// set the domain
		responseCookie.setDomain(optionalCookie.map(cookie -> cookie.getDomain()).orElse(null));
		// set the path
		responseCookie.setPath(optionalCookie.map(cookie -> cookie.getPath()).orElse(null));
		// set the comment
		responseCookie.setComment(optionalCookie.map(cookie -> cookie.getComment()).orElse(null));
		// set the version
		responseCookie.setVersion(magicCookie.getVersion());
		responseCookie.setVersion(optionalCookie.map(cookie -> cookie.getVersion()).orElse(null));

		Optional<Date> optionalExpiryDate = Optional
				.ofNullable(optionalCookie.map(cookie -> cookie.getExpiryDate()).orElse(null));

		if (optionalExpiryDate.isPresent()) {
			long maxAge = (optionalExpiryDate.get().getTime() - System.currentTimeMillis()) / 1000;
			// we have to lower down, no other option
			responseCookie.setMaxAge((int) maxAge);
		}
		// return the servlet cookie
		return responseCookie;
	}

	
	@Override
	public String getSite() {
		return this.site;
	}

	/**
	 * Gets the post URL.
	 *
	 * @return the post URL
	 */
	public String getPostURL() {
		return this.postURL;
	}

	/**
	 * Populates proflowers add to cart DTO
	 * @param addToCartRequest
	 * @return ProflowersAddToCartDTO
	 */
	protected ProflowersAddToCartDTO populateAddToCartDTO(AddToCartRequest addToCartRequest,
			List<JsonNode> productNodes) {

		JsonNode productNode = productNodes.get(0);

		List<Personalization> personalizations = addToCartRequest.getPersonalizations();

		List<PersonalizationElement> personalizationElements = new ArrayList<PersonalizationElement>();
		if (!CollectionUtils.isEmpty(personalizations)) {
			personalizations.forEach(personalization -> {

				PersonalizationElement personalizationElement = new PersonalizationElement();
				personalizationElement.setPersonalizationName(personalization.getPersonalizationName());
				personalizationElement.setPersonalizationValue(personalization.getPersonalizationValue());
				personalizationElement.setPersonalizationType(personalization.getPersonalizationType());
				personalizationElement.setFulfillmentSortOrder(personalization.getFulfillmentSortOrder());
				personalizationElements.add(personalizationElement);
			});
		}
		Double originalSalePrice = productNodes.get(0).get(AddToCartConstants.ORIGINALSALEPRICE).asDouble();
		PidTree child = null;
		if (productNodes.get(1) != null) {
			child = new PidTree().withPersonalizationElements(personalizationElements)
					.withPid(productNodes.get(1).get(AddToCartConstants.PRODUCTID).asInt())
					.withOriginalSalePrice(productNodes.get(1).get(AddToCartConstants.ORIGINALSALEPRICE).asDouble());
		}

		PidTree pidTree = new PidTree().withPid(Integer.parseInt(addToCartRequest.getProductId()))
				.withOriginalSalePrice(originalSalePrice);
		pidTree.getChildren().add(child);

		Integer originalRootPid = Optional.ofNullable(productNode.get(AddToCartConstants.ORIGINALROOTPID).asInt())
				.orElse(null);
		PBContext pbContext = new PBContext().withCurrentRootPid(originalRootPid).withOriginalRootPid(originalRootPid)
				.withPartnerCode(
						Optional.ofNullable(productNode.get(AddToCartConstants.PARTNERCODE).asText()).orElse(null))
				.withIsPortablePDP(Boolean.FALSE)
				.withExternalPageUrl(
						Optional.ofNullable(productNode.get(AddToCartConstants.EXTERNALPAGEURL).asText()).orElse(null))
				.withPortablePDPDiscountType(Optional
						.ofNullable(productNode.get(AddToCartConstants.PORTABLEPDPDISCOUNTTYPE).asInt()).orElse(null))
				.withPortablePDPDiscountValue(Optional
						.ofNullable(productNode.get(AddToCartConstants.PORTABLEPDPDISCOUNTVALUE).asInt()).orElse(null));

		PBResult pbResult = new PBResult().withDeliveryDate(addToCartRequest.getDeliveryDate()).withPidTree(pidTree)
				.withTotalQuantity(1).withZipCode(addToCartRequest.getZipCode()).withIsDeliveryBy(Boolean.FALSE)
				.withFlexDelivery(Boolean.FALSE);

		ProflowersAddToCartDTO proflowersAddToCartDTO = new ProflowersAddToCartDTO().withPBContext(pbContext)
				.withPBResult(pbResult).withPBContext(pbContext).withPBContext(pbContext);
		proflowersAddToCartDTO.setSkuBusinessUnitId(productNode.get(AddToCartConstants.SKUBUSSINESSUNITID).asInt());
		proflowersAddToCartDTO.setSavePidTreeInfoInTransactionTable(Boolean.FALSE);
		proflowersAddToCartDTO.setCartUniqueLineNumberForEdit(0);

		return proflowersAddToCartDTO;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	private static Map<String, String> productData = new HashMap<String, String>();

	static {
		productData.put("2204",
				"{\"SiteId\":\"Proflowers\",\"ProductType\":\"N\",\"Pid\":2204,\"FlexDelivery\":false,\"SkuBusinessUnitId\":1,\"IsDeliveryBy\":true,\"salePrice\":269.98,\"OriginalSalePrice\":269.98,\"IsProductSet\":false,\"ExternalPageUrl\":\"https://www.proflowers.com/product/3-Months-of-Flowers-2202?pagesplit=SplitA&ref=RadioHomeRadioListeners_W&code=&prid=fgvradio&PersonalityName=Radio&top=tuedj3&pid=960&q=2202&start=&spell=&trackingpgroup=pid\",\"OriginalRootPid\":2202,\"RefCode\":null,\"OriginatingCobrand\":null,\"PartnerCode\":\"PFC\",\"Ssid\":null,\"IsPortablePDP\":false,\"ApplicationUsingPortablePDP\":null,\"PortablePDPDiscountType\":0,\"PortablePDPDiscountValue\":0,\"CurrentRootPid\":2202}");

		productData.put("40237",
				"{\"ProductName\":\"Large Ginger Vase\",\"ProductDetailsName\":\"Large Ginger\",\"StrikePrice\":0,\"OriginalSalePrice\":0,\"SalePrice\":0,\"ImageUrl\":\"https://cimages.prvd.com/is/image/ProvideCommerce/ACC_11_VA0741_W1_SQ?$PFCProductImage$\",\"ProductId\":40237,\"RelationshipId\":606925,\"IsBundle\":true,\"SortOrder\":1,\"Description\":null,\"RewardPoints\":0,\"RewardUnitType\":null,\"RewardUnitLabel\":null,\"IsEligibleForRewards\":false,\"ShowPlusInCrossSellPrice\":false}");

	}

}