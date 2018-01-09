package com.ftd.cart.service.strategy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftd.cart.constants.AddToCartConstants;
import com.ftd.cart.dto.FTDAddToCartDTO;
import com.ftd.cart.service.ProductDetailsService;
import com.ftd.cart.validator.FTDAddToCartDTOValidator;
import com.ftd.cart.vo.AddToCartRequest;
import com.ftd.cart.vo.AddToCartResponse;
import com.ftd.commons.misc.constants.CommonConstants;
import com.ftd.commons.misc.exception.InputValidationException;
import com.ftd.commons.misc.util.CommonUtils;

/**
 * The Class FTDAddToCartStrategy.
 * 
 * @author Vikrant Shirbhate
 * 
 */
@Component
public class FTDAddToCartStrategy implements AddToCartStrategy {

	@Value("${config.FTDAddToCartStrategy.site}")
	String site;

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(FTDAddToCartStrategy.class);

	@Value("${config.FTDAddToCartStrategy.postURL}")
	String postURL;

	@Autowired
	FTDAddToCartDTOValidator ftdAddToCartDTOValidator;

	@Autowired
	ProductDetailsService productDetailsService;

	@Value("${config.FTDAddToCartStrategy.domain}")
	String domain;

	@Autowired
	private ApplicationContext applicationContext;

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
		final String methodName = "addToCartFTD";
		if (log.isDebugEnabled()) {
			log.debug(methodName + "-start------\n\n\n");
		}
		AddToCartResponse addToCartResponse = new AddToCartResponse();

		String responseBody = null;
		Header[] locationHeaders = null;

		try {
			HttpClientContext context = new HttpClientContext();
			CloseableHttpClient httpclient = (CloseableHttpClient) getApplicationContext().getBean("httpClient");

			JsonNode productNode = new ObjectMapper().readTree(productData.get(addToCartRequest.getProductId()));
			FTDAddToCartDTO ftdAddToCartDTO = populateAddToCartDTO(addToCartRequest, productNode);
			DataBinder binder = new DataBinder(ftdAddToCartDTO);
			binder.setValidator(getFtdAddToCartDTOValidator());
			binder.validate();
			BindingResult bindingResult = binder.getBindingResult();
			if (bindingResult.hasErrors()) {
				throw new InputValidationException("BAD_REQUEST", "Invalid JSON input", bindingResult);
			}

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(buildFormFromDTO(ftdAddToCartDTO), Consts.UTF_8);

			HttpPost httpPost = new HttpPost(getPostURL() + ftdAddToCartDTO.getProductURL());

			BasicCookieStore basicCookieStore = new BasicCookieStore();

			basicCookieStore.addCookies(CommonUtils.getHttpClientCookies(httpServletRequest.getCookies(), getDomain()));

			// send cookies from request
			context.setCookieStore(basicCookieStore);

			httpPost.setEntity(entity);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				log.info("status returned =" + status);
				if (status >= 200 && status < 400) {
					HttpEntity responseEntity = response.getEntity();

					return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			responseBody = httpclient.execute(httpPost, responseHandler, context);

			setAddToCartResponseFromCookie(httpServletResponse, addToCartResponse, context);

			// get the redirect url from response
			locationHeaders = context.getResponse().getHeaders(CommonConstants.HEADER_LOCATION);
			log.debug("----------------------------------------");
			log.debug(responseBody);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		} catch (ClientProtocolException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		// get the first non null location header from the array of location headers and
		// set it to response object
		if (Objects.nonNull(locationHeaders)) {
			addToCartResponse
					.setRedirectUrl(Stream.of(locationHeaders).filter(Objects::nonNull).findFirst().get().getValue());
		}

		if (log.isDebugEnabled()) {
			log.debug(methodName + "-end");
		}
		return Optional.of(addToCartResponse);
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
		String shoppingCart = null;
		Optional<CookieStore> optionalCookieStore = Optional.ofNullable(context.getCookieStore());

		Optional<List<Cookie>> optionalCookies = optionalCookieStore.map(magicCookies -> magicCookies.getCookies());

		if (optionalCookies.isPresent()) {
			optionalCookies.get().stream()
					.forEach(cookie -> httpServletResponse.addCookie(CommonUtils.createCookieFromMagicCookie(cookie)));

			List<Cookie> optionalRequiredCookie = getRequiredCookie(optionalCookies.get());
			
			addToCartResponse.setCookies(optionalRequiredCookie);

			Optional<Cookie> optionalCookie = optionalCookies.get().stream()
					.filter(cookie -> cookie.getName().equalsIgnoreCase(AddToCartConstants.CART_ID))
					.findFirst();
			if (optionalCookie.isPresent()) {
				orderId = optionalCookie.get().getValue();
			}

			optionalCookie = optionalCookies.get().stream()
					.filter(cookie -> cookie.getName().equalsIgnoreCase(AddToCartConstants.SHOPPING_CART)).findFirst();

			if (optionalCookie.isPresent()) {
				shoppingCart = optionalCookie.get().getValue();
			}
		}
		addToCartResponse.setOrderId(orderId);

		if (StringUtils.isNotBlank(shoppingCart) && StringUtils.isNotBlank(orderId)) {
			addToCartResponse.setSuccessFlag(Boolean.TRUE);
		} else {
			addToCartResponse.setSuccessFlag(Boolean.FALSE);
		}
	}


	/**
	 * 
	 * Method to remove cookies without create date.
	 * @param optionalCookies
	 * @return List<Cookie>
	 */
	private List<Cookie> getRequiredCookie(List<Cookie> optionalCookies) {

		List<Cookie> listCookie = new ArrayList<Cookie>();
		optionalCookies.forEach(cookie -> {
			if (((BasicClientCookie) cookie).getCreationDate() != null) {
				listCookie.add(cookie);
			}
		});
		return listCookie;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ftd.cart.service.strategy.AddToCartStrategy#getSite()
	 */
	@Override
	public String getSite() {
		return this.site;
	}

	public String getPostURL() {
		return this.postURL;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @return the ftdAddToCartDTOValidator
	 */
	public FTDAddToCartDTOValidator getFtdAddToCartDTOValidator() {
		return ftdAddToCartDTOValidator;
	}

	/**
	 * This method is used to build the form from request for making post call
	 * 
	 * @return
	 */

	public List<NameValuePair> buildFormFromDTO(FTDAddToCartDTO ftdAddToCartDTO) {
		return Form.form()
				.add(AddToCartConstants.PRODUCT_TYPE, ftdAddToCartDTO.getProductType())
				.add(AddToCartConstants.PPR_2015, "1")
				.add(AddToCartConstants.PRODUCT_ID, ftdAddToCartDTO.getProductId())
				.add(AddToCartConstants.SUBCODE, ftdAddToCartDTO.getSubCode())
				.add(AddToCartConstants.MARKCODE, ftdAddToCartDTO.getMarkCode())
				.add(AddToCartConstants.PRODUCT_PRICE, ftdAddToCartDTO.getProductPrice())
				.add(AddToCartConstants.WEST_PRODUCT, ftdAddToCartDTO.getWestProduct())
				.add(AddToCartConstants.MASTER, ftdAddToCartDTO.getMaster())
				.add(AddToCartConstants.AID, ftdAddToCartDTO.getAid())
				.add(AddToCartConstants.ACTION, ftdAddToCartDTO.getAction())
				.add(AddToCartConstants.SELECTED_PRODUCT, ftdAddToCartDTO.getSelectedProduct())
				.add(AddToCartConstants.USE_NEW_PURCHASE_PATH, "1")
				.add(AddToCartConstants.DELIVERY_DATE, ftdAddToCartDTO.getDeliveryDate())
				.add(AddToCartConstants.PAS_ONLY_SHIPPING_METHOD, ftdAddToCartDTO.getPasOnlyShippingMethod())
				.add(AddToCartConstants.STZIP, ftdAddToCartDTO.getZip())
				.add(AddToCartConstants.PP_DEL_LOC, ftdAddToCartDTO.getPpDelLoc())
				.add(AddToCartConstants.VASES, "")
				.add(AddToCartConstants.SHIPPING_METHOD_DELDATE_PRODUCT, "")
				.add(AddToCartConstants.COUNTRY_ID, ftdAddToCartDTO.getCountryId())
				.add(AddToCartConstants.PRODUCT_COUNTRY, ftdAddToCartDTO.getProductCountry())
				.add(AddToCartConstants.SAMEDAY, ftdAddToCartDTO.getSameday())
				.add(AddToCartConstants.PRODUCT_SUB_TYPE, ftdAddToCartDTO.getProductSubType())
				.add(AddToCartConstants.WEBSITE_ID, "352")
				.add(AddToCartConstants.GBB, ftdAddToCartDTO.getGbb())
				.add(AddToCartConstants.MASTER_PROD_SKU_COUNT, "")
				.add(AddToCartConstants.PRODUCT_PRICE_WITHDISCOUNT, ftdAddToCartDTO.getProductSubType())
				.add(AddToCartConstants.ITEM1, ftdAddToCartDTO.getItem1())
				.add(AddToCartConstants.CATALOG, ftdAddToCartDTO.getCatalog())
				.add(AddToCartConstants.VH, "")
				.add(AddToCartConstants.SUBCODE_LIST, "")
				.add(AddToCartConstants.SUBCODE_PRICES, "")
				.add(AddToCartConstants.DELIVERY_OPTION, "")
				.add(AddToCartConstants.ENTRY_FROM, "")
				.add(AddToCartConstants.PRODUCT_NAME, ftdAddToCartDTO.getProductName())
				.add(AddToCartConstants.DISPLAY_DELIVDATE_PRODUCT, "")
				.add(AddToCartConstants.UPDATE_CART, "")
				.add(AddToCartConstants.UPDATE_CART_ENTRY_ID, "")
				.add(AddToCartConstants.ADDONS, "")
				.build();

	}

	/**
	 * transform and populate FTDAddTocartDTO
	 * @param addToCartRequest
	 * @return FTDAddToCartDTO
	 * @throws UnsupportedEncodingException
	 */
	public FTDAddToCartDTO populateAddToCartDTO(AddToCartRequest addToCartRequest, JsonNode productRootNode)
			throws UnsupportedEncodingException {

		FTDAddToCartDTO ftdAddToCartDTO = new FTDAddToCartDTO();

		ftdAddToCartDTO.setProductId(addToCartRequest.getProductId());

		ftdAddToCartDTO.setItem1(addToCartRequest.getProductId());

		ftdAddToCartDTO.setProductType(Optional.ofNullable(productRootNode.path(AddToCartConstants.PRODUCT_TYPE))
				.map(node -> node.asText()).orElse(null));

		ftdAddToCartDTO.setCatalog(Optional.ofNullable(productRootNode.path(AddToCartConstants.CATALOG))
				.map(node -> node.asText()).orElse(null));

		ftdAddToCartDTO.setCountryId(Optional.ofNullable(productRootNode.path(AddToCartConstants.COUNTRY))
				.map(node -> node.asText()).orElse(null));

		ftdAddToCartDTO.setProductCountry(Optional.ofNullable(productRootNode.path(AddToCartConstants.COUNTRY))
				.map(node -> node.asText()).orElse(null));

		ftdAddToCartDTO.setAid(AddToCartConstants.SHOPCART);
		ftdAddToCartDTO.setAction(AddToCartConstants.ADDTOCART);

		ftdAddToCartDTO.setDeliveryDate(addToCartRequest.getDeliveryDate());

		ftdAddToCartDTO.setGbb(Optional.ofNullable(productRootNode.path(AddToCartConstants.GBB))
				.map(node -> convertBooleanToNumber(node)).orElse(null));

		ftdAddToCartDTO.setMarkCode(Optional.ofNullable(productRootNode.path(AddToCartConstants.MARKCODE))
				.map(node -> node.asText()).orElse(null));

		ftdAddToCartDTO.setMaster(Optional.ofNullable(productRootNode.path(AddToCartConstants.MASTER))
				.map(node -> convertBooleanToNumber(node)).orElse(null));

		ftdAddToCartDTO.setWestProduct(Optional.ofNullable(productRootNode.path(AddToCartConstants.WEST_PRODUCT))
				.map(node -> convertBooleanToNumber(node)).orElse(null));

		ftdAddToCartDTO.setZip(addToCartRequest.getZipCode());

		ftdAddToCartDTO.setProductSubType(Optional.ofNullable(productRootNode.path(AddToCartConstants.PRODUCT_SUBTYPES))
				.map(node -> getProductSubTypesAsString(node.elements())).orElse(null));

		ftdAddToCartDTO.setMaster(Optional.ofNullable(productRootNode.path(AddToCartConstants.MASTER))
				.map(node -> convertBooleanToNumber(node)).orElse(null));

		ftdAddToCartDTO.setPasOnlyShippingMethod(
				Optional.ofNullable(productRootNode.path(AddToCartConstants.PAS_ONLY_SHIPPING_METHOD))
						.map(node -> node.asText()).orElse(null));

		ftdAddToCartDTO.setPpDelLoc(addToCartRequest.getDeliverylocationType());

		ftdAddToCartDTO.setSubCode(Optional.ofNullable(productRootNode.path(AddToCartConstants.SUBCODE))
				.map(node -> node.asText()).orElse(null));

		ftdAddToCartDTO.setProductPrice(Optional.ofNullable(productRootNode.path(AddToCartConstants.PRODUCT_PRICE))
				.map(node -> node.asText()).orElse(null));

		ftdAddToCartDTO
				.setProductName(encodeUrl(Optional.ofNullable(productRootNode.path(AddToCartConstants.PRODUCT_NAME))
						.map(node -> node.asText()).orElse("")));

		ftdAddToCartDTO.setSameday(Optional.ofNullable(productRootNode.path(AddToCartConstants.IS_SAME_DAY))
				.map(node -> convertBooleanToNumber(node)).orElse(null));

		ftdAddToCartDTO.setProductURL(Optional.ofNullable(productRootNode.path(AddToCartConstants.PRODUCT_URL))
				.map(node -> node.asText()).orElse(null));

		ftdAddToCartDTO.setSelectedProduct(addToCartRequest.getSkuId());

		return ftdAddToCartDTO;

	}

	/**
	 * to encode the URL string
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String encodeUrl(String url) throws UnsupportedEncodingException {
		return URLEncoder.encode(url, "UTF-8");
	}

	/**
	 * covert the boolean true/false to 1/0 respectively
	 * 
	 * @param node
	 * @return
	 */
	private String convertBooleanToNumber(JsonNode node) {
		return Integer.toString(Boolean.compare(node.asBoolean(), false));
	}

	/**
	 * get formatted product sub type shown/Delux/Premium::price::discountprice
	 * 
	 * @param jsonNodesItr
	 * @return
	 */
	private String getProductSubTypesAsString(Iterator<JsonNode> jsonNodesItr) {
		while (jsonNodesItr.hasNext()) {
			JsonNode productSubtype = jsonNodesItr.next();
			if (productSubtype != null && productSubtype.get("name").asText().equals("Shown")) {
				return new StringBuffer().append(productSubtype.get("name").asText()).append("::")
						.append(productSubtype.get("price").asText()).append("::")
						.append(productSubtype.get("discountedPrice").asText()).toString();
			}
		}
		return null;
	}

	private static Map<String, String> productData = new HashMap<String, String>();

	static {
		productData.put("FALL1",
				"{\"siteId\":\"FTD\",\"product_type\":\"freshcut\",\"country\":\"US\",\"subcode\":\"FK639\",\"product_id\":\"FALL1\",\"product_price\":44.99,\"west_product\":true,\"isMaster\":true,\"pas_only_shipping_method\":\"Next-Day\",\"isSameDay\":false,\"markcode\":\"352\",\"product_url\":\"best-sellers-pcg/count-your-blessings-fall-bouquet/product-bestsellers/fall1/\",\"productSubTypes\":[{\"name\":\"Shown\",\"price\":\"49.99\",\"discountedPrice\":\"39.99\"},{\"name\":\"Delux\",\"price\":\"49.99\",\"discountedPrice\":\"39.99\"}],\"addOns\":[{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/C11_99x99.jpg\",\"addon_text\":\"Add a Box of Chocolates and we will sweeten their day with a 4-piece box of sumptuous Godiva Chocolates to accompany your bouquet.\",\"max_quantity\":1,\"desc\":\"Godiva 4 Piece Box of Chocolates\",\"order\":1,\"id\":\"C11\",\"price\":\"$8.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Chocolate\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/PB171_99x99.jpg\",\"addon_text\":\"Make your gift even more special with this Happy Birthday bear.\",\"max_quantity\":1,\"desc\":\"Happy Birthday Plush Bear\",\"order\":2,\"id\":\"PB171\",\"price\":\"$15.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Bear\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/K64_99x99.jpg\",\"addon_text\":\"Yellow Happy Birthday Pick\",\"max_quantity\":1,\"desc\":\"Yellow Happy Birthday Pick\",\"order\":3,\"id\":\"K64\",\"price\":\"$4.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Keepsake\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/J289X_99x99.jpg\",\"addon_text\":\"Complete your gift by adding a .05 ct Diamond Heart Sterling Sliver Pendant.\",\"max_quantity\":1,\"desc\":\"Diamond Heart Pendant\",\"order\":4,\"id\":\"J289X\",\"price\":\"$29.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Other\"}],\"catalog\":\"FTDDirect\",\"gbb\":true,\"product_name\":\"Count Your Blessings Fall Bouquet\"}");
		productData.put("FFDL2",
				"{\"siteId\":\"FTD\",\"product_type\":\"freshcut\",\"country\":\"US\",\"subcode\":\"FK1036\",\"product_id\":\"FFDL2\",\"product_price\":59.99,\"west_product\":true,\"isMaster\":true,\"pas_only_shipping_method\":\"Next-Day\",\"isSameDay\":false,\"markcode\":\"514\",\"product_url\":\"mixed-bouquets-pcg/you-had-me-at-pink-bouquet/product-flowers-mixedbouquets/ffdl2/\",\"productSubTypes\":[{\"name\":\"Shown\",\"price\":\"59.99\",\"discountedPrice\":\"59.99\"}],\"vases\":[{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/VA1367T_99x99.jpg\",\"addon_text\":\"Vase as shown\",\"max_quantity\":1,\"desc\":\"Vase as shown\",\"order\":1,\"id\":\"VA1367T\",\"price\":\"$0.00\",\"selected\":1,\"available\":1,\"type_desc\":\"Vase\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/AV5_99x99.jpg\",\"addon_text\":\"Add on a pink Glass Vase\",\"max_quantity\":1,\"desc\":\"Dreamy Pink Vase\",\"order\":2,\"id\":\"AV5\",\"price\":\"$8.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Vase\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/AV6_99x99.jpg\",\"addon_text\":\"Luminous Silver Finish Vase\",\"max_quantity\":1,\"desc\":\"Radiant Silver Vase\",\"order\":3,\"id\":\"AV6\",\"price\":\"$9.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Vase\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/AV9_99x99.jpg\",\"addon_text\":\"Upgrade your bouquet with this colored vase.\",\"max_quantity\":1,\"desc\":\"Gold Touch Vase\",\"order\":4,\"id\":\"AV9\",\"price\":\"$9.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Vase\"}],\"addOns\":[{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/C11_99x99.jpg\",\"addon_text\":\"Add a Box of Chocolates and we will sweeten their day with a 4-piece box of sumptuous Godiva Chocolates to accompany your bouquet.\",\"max_quantity\":1,\"desc\":\"Godiva 4 Piece Box of Chocolates\",\"order\":1,\"id\":\"C11\",\"price\":\"$8.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Chocolate\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/PB111_99x99.jpg\",\"addon_text\":\"Add a bear to your bouquet and we will select a cute & cuddly brown bear that will send the perfect sentiment.\",\"max_quantity\":1,\"desc\":\"Adorable Plush Tan Bear\",\"order\":2,\"id\":\"PB111\",\"price\":\"$9.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Plush\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/P21_99x99.jpg\",\"addon_text\":\"Capture memories together with this 3 x 4-inch silver metal photo frame.  Fits a 2 x 3-inch photo.\",\"max_quantity\":1,\"desc\":\"Silver Metal Photo Frame\",\"order\":3,\"id\":\"P21\",\"price\":\"$4.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Photo\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/J525X_99x99.jpg\",\"addon_text\":\"Featuring 25 round-cut Swarovski Crystals set in stunning sterling silver.  Heart-shaped pendant.  Total of 25 crystals.  18-inch sterling silver box chain with spring ring closure.  13/16 inch including bale.  Imported.\",\"max_quantity\":1,\"desc\":\"Swarovski Crystal Elements Sterling Silver Pendant\",\"order\":4,\"id\":\"J525X\",\"price\":\"$29.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Other\"}],\"catalog\":\"FTDDirect\",\"gbb\":true,\"product_name\":\"You Had Me at Pink Bouquet\"}");
	}

}