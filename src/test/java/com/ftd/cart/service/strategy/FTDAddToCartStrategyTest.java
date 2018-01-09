package com.ftd.cart.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.fluent.Form;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftd.cart.constants.AddToCartConstants;
import com.ftd.cart.dto.FTDAddToCartDTO;
import com.ftd.cart.ssl.connection.SSLConnection;
import com.ftd.cart.validator.FTDAddToCartDTOValidator;
import com.ftd.cart.vo.AddToCartRequest;
import com.ftd.cart.vo.AddToCartResponse;
import com.ftd.commons.misc.util.CommonUtils;

/**
 * @author sanan7
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FTDAddToCartStrategyTest {

	private static final String PRODUCT_ID = "FALL1";

	private static final String HTTPS_ORDERING_FTD_COM = "https://ordering.ftd.com/";

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(FTDAddToCartStrategy.class);

	@Mock
	FTDAddToCartStrategy fTDAddToCartStrategy;

	@Mock
	ApplicationContext applicationContext;

	@Mock
	CommonUtils commonUtils;

	@Autowired
	SSLConnection connection;

	@Autowired
	FTDAddToCartDTOValidator ftdAddToCartDTOValidator;

	private org.apache.http.cookie.Cookie cookie;
	private AddToCartRequest addToCartRequest;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp(){

		addToCartRequest = new AddToCartRequest();
		addToCartRequest.setSiteId("ftd");
		addToCartRequest.setProductId(PRODUCT_ID);
		addToCartRequest.setDeliveryDate("2018/01/21");
		addToCartRequest.setZipCode("10001");
		addToCartRequest.setQuantity("1");
		addToCartRequest.setDeliverylocationType("R");

	}

	/**
	 * Test method for
	 * {@link com.ftd.cart.service.strategy.FTDAddToCartStrategy#addToCart(com.ftd.cart.vo.AddToCartRequest, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */

	@Test
	public void testAddToCart() {

		fTDAddToCartStrategy = new FTDAddToCartStrategy();
		FTDAddToCartStrategy spy = Mockito.spy(fTDAddToCartStrategy);
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		String domain = "ordering.ftd.com";

		Mockito.when(spy.getApplicationContext()).thenReturn(applicationContext);

		CloseableHttpClient httpclient = connection.getSSLConnectionSocketFactoryLive();

		Cookie[] cookies = new Cookie[3];

		Cookie cartIdCookie = new Cookie(AddToCartConstants.CART_ID, "1524115001");
		Cookie shoppingCartCookie = new Cookie("shoppingcart",
				"%7B%22num_items%22%3A5%2C%22cart_id%22%3A1524115001%2C%22vs_counter%22%3A1724255645%7D");
		Cookie domainCookie = new Cookie("domain", ".ftd.com");

		cookies[0] = cartIdCookie;
		cookies[1] = shoppingCartCookie;
		cookies[2] = domainCookie;

		Mockito.when(spy.getDomain()).thenReturn(domain);

		Mockito.when(mockedRequest.getCookies()).thenReturn(cookies);

		when((CloseableHttpClient) applicationContext.getBean(AddToCartConstants.HTTP_CLIENT)).thenReturn(httpclient);
		when(spy.getFtdAddToCartDTOValidator()).thenReturn(ftdAddToCartDTOValidator);

		Mockito.when(spy.getPostURL()).thenReturn(HTTPS_ORDERING_FTD_COM);

		Optional<AddToCartResponse> addToCartResponse = spy.addToCart(addToCartRequest, mockedRequest,
				httpServletResponse);
		if (addToCartResponse.isPresent()) {
			assertNotNull(addToCartResponse.get().getOrderId(), addToCartResponse);
			assertTrue(addToCartResponse.get().isSuccessFlag());
		}

	}

	@Test
	public void testAddtoCartResponseCookie() {

		fTDAddToCartStrategy = new FTDAddToCartStrategy();
		FTDAddToCartStrategy spy = Mockito.spy(fTDAddToCartStrategy);
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		Mockito.when(spy.getPostURL()).thenReturn(HTTPS_ORDERING_FTD_COM);

		String domain = "ordering.ftd.com";

		Mockito.when(spy.getApplicationContext()).thenReturn(applicationContext);

		CloseableHttpClient httpclient = connection.getSSLConnectionSocketFactoryLive();

		Cookie[] cookies = new Cookie[3];

		Cookie cartIdCookie = new Cookie(AddToCartConstants.CART_ID, "1524115001");
		Cookie shoppingCartCookie = new Cookie("shoppingcart",
				"%7B%22num_items%22%3A5%2C%22cart_id%22%3A1524115001%2C%22vs_counter%22%3A1724255645%7D");
		Cookie domainCookie = new Cookie("domain", ".ftd.com");

		cookies[0] = cartIdCookie;
		cookies[1] = shoppingCartCookie;
		cookies[2] = domainCookie;

		Mockito.when(spy.getDomain()).thenReturn(domain);

		Mockito.when(mockedRequest.getCookies()).thenReturn(cookies);

		when((CloseableHttpClient) applicationContext.getBean(AddToCartConstants.HTTP_CLIENT)).thenReturn(httpclient);
		when(spy.getFtdAddToCartDTOValidator()).thenReturn(ftdAddToCartDTOValidator);
		Mockito.when(spy.getPostURL()).thenReturn(HTTPS_ORDERING_FTD_COM);

		Optional<AddToCartResponse> addToCartResponse = spy.addToCart(addToCartRequest, mockedRequest,
				httpServletResponse);
		if (addToCartResponse.isPresent()) {
			for (org.apache.http.cookie.Cookie cookie1 : addToCartResponse.get().getCookies()) {
				if (cookie1.getName().equals(AddToCartConstants.CART_ID)) {
					cookie = cookie1;
					break;
				}
			}
		}
		assertEquals(cookie.getDomain(), "ftd.com");
		assertEquals(cookie.getPath(), "/");
	}

	@Test
	public void testException() {
		fTDAddToCartStrategy = new FTDAddToCartStrategy();
		FTDAddToCartStrategy spy = Mockito.spy(fTDAddToCartStrategy);

		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		Mockito.when(spy.getPostURL()).thenReturn(HTTPS_ORDERING_FTD_COM);

		CloseableHttpClient httpclient = connection.getSSLConnectionSocketFactoryLive();
		when(spy.getApplicationContext()).thenReturn(applicationContext);
		when((CloseableHttpClient) applicationContext.getBean(AddToCartConstants.HTTP_CLIENT)).thenReturn(httpclient);
		when(spy.getFtdAddToCartDTOValidator()).thenReturn(ftdAddToCartDTOValidator);

		FTDAddToCartDTO ftdAddToCartDTO;
		try {
			JsonNode productNode = new ObjectMapper().readTree(productData.get(addToCartRequest.getProductId()));
			ftdAddToCartDTO = spy.populateAddToCartDTO(addToCartRequest, productNode);

			Mockito.when(spy.buildFormFromDTO(ftdAddToCartDTO))
					.thenReturn(Form.form().add(AddToCartConstants.PRODUCT_TYPE, "freshcut")
							.add(AddToCartConstants.SELECTED_PRODUCT, PRODUCT_ID).build());
			when(spy.addToCart(addToCartRequest, mockedRequest, httpServletResponse))
					.thenThrow(new IllegalStateException());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private static Map<String, String> productData = new HashMap<String, String>();

	static {
		productData.put(PRODUCT_ID,
				"{\"siteId\":\"FTD\",\"product_type\":\"freshcut\",\"country\":\"US\",\"subcode\":\"FK639\",\"product_id\":\"FALL1\",\"product_price\":44.99,\"west_product\":true,\"isMaster\":true,\"pas_only_shipping_method\":\"Next-Day\",\"isSameDay\":false,\"markcode\":\"352\",\"product_url\":\"best-sellers-pcg/count-your-blessings-fall-bouquet/product-bestsellers/fall1/\",\"productSubTypes\":[{\"name\":\"Shown\",\"price\":\"49.99\",\"discountedPrice\":\"39.99\"},{\"name\":\"Delux\",\"price\":\"49.99\",\"discountedPrice\":\"39.99\"}],\"addOns\":[{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/C11_99x99.jpg\",\"addon_text\":\"Add a Box of Chocolates and we will sweeten their day with a 4-piece box of sumptuous Godiva Chocolates to accompany your bouquet.\",\"max_quantity\":1,\"desc\":\"Godiva 4 Piece Box of Chocolates\",\"order\":1,\"id\":\"C11\",\"price\":\"$8.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Chocolate\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/PB171_99x99.jpg\",\"addon_text\":\"Make your gift even more special with this Happy Birthday bear.\",\"max_quantity\":1,\"desc\":\"Happy Birthday Plush Bear\",\"order\":2,\"id\":\"PB171\",\"price\":\"$15.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Bear\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/K64_99x99.jpg\",\"addon_text\":\"Yellow Happy Birthday Pick\",\"max_quantity\":1,\"desc\":\"Yellow Happy Birthday Pick\",\"order\":3,\"id\":\"K64\",\"price\":\"$4.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Keepsake\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/J289X_99x99.jpg\",\"addon_text\":\"Complete your gift by adding a .05 ct Diamond Heart Sterling Sliver Pendant.\",\"max_quantity\":1,\"desc\":\"Diamond Heart Pendant\",\"order\":4,\"id\":\"J289X\",\"price\":\"$29.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Other\"}],\"catalog\":\"FTDDirect\",\"gbb\":true,\"product_name\":\"Count Your Blessings Fall Bouquet\"}");
		productData.put("FFDL2",
				"{\"siteId\":\"FTD\",\"product_type\":\"freshcut\",\"country\":\"US\",\"subcode\":\"FK1036\",\"product_id\":\"FFDL2\",\"product_price\":59.99,\"west_product\":true,\"isMaster\":true,\"pas_only_shipping_method\":\"Next-Day\",\"isSameDay\":false,\"markcode\":\"514\",\"product_url\":\"mixed-bouquets-pcg/you-had-me-at-pink-bouquet/product-flowers-mixedbouquets/ffdl2/\",\"productSubTypes\":[{\"name\":\"Shown\",\"price\":\"59.99\",\"discountedPrice\":\"59.99\"}],\"vases\":[{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/VA1367T_99x99.jpg\",\"addon_text\":\"Vase as shown\",\"max_quantity\":1,\"desc\":\"Vase as shown\",\"order\":1,\"id\":\"VA1367T\",\"price\":\"$0.00\",\"selected\":1,\"available\":1,\"type_desc\":\"Vase\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/AV5_99x99.jpg\",\"addon_text\":\"Add on a pink Glass Vase\",\"max_quantity\":1,\"desc\":\"Dreamy Pink Vase\",\"order\":2,\"id\":\"AV5\",\"price\":\"$8.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Vase\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/AV6_99x99.jpg\",\"addon_text\":\"Luminous Silver Finish Vase\",\"max_quantity\":1,\"desc\":\"Radiant Silver Vase\",\"order\":3,\"id\":\"AV6\",\"price\":\"$9.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Vase\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/AV9_99x99.jpg\",\"addon_text\":\"Upgrade your bouquet with this colored vase.\",\"max_quantity\":1,\"desc\":\"Gold Touch Vase\",\"order\":4,\"id\":\"AV9\",\"price\":\"$9.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Vase\"}],\"addOns\":[{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/C11_99x99.jpg\",\"addon_text\":\"Add a Box of Chocolates and we will sweeten their day with a 4-piece box of sumptuous Godiva Chocolates to accompany your bouquet.\",\"max_quantity\":1,\"desc\":\"Godiva 4 Piece Box of Chocolates\",\"order\":1,\"id\":\"C11\",\"price\":\"$8.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Chocolate\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/PB111_99x99.jpg\",\"addon_text\":\"Add a bear to your bouquet and we will select a cute & cuddly brown bear that will send the perfect sentiment.\",\"max_quantity\":1,\"desc\":\"Adorable Plush Tan Bear\",\"order\":2,\"id\":\"PB111\",\"price\":\"$9.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Plush\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/P21_99x99.jpg\",\"addon_text\":\"Capture memories together with this 3 x 4-inch silver metal photo frame.  Fits a 2 x 3-inch photo.\",\"max_quantity\":1,\"desc\":\"Silver Metal Photo Frame\",\"order\":3,\"id\":\"P21\",\"price\":\"$4.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Photo\"},{\"show_price\":\"Y\",\"is_default\":\"N\",\"image\":\"https://www.ftdimg.com/pics/products/addons/J525X_99x99.jpg\",\"addon_text\":\"Featuring 25 round-cut Swarovski Crystals set in stunning sterling silver.  Heart-shaped pendant.  Total of 25 crystals.  18-inch sterling silver box chain with spring ring closure.  13/16 inch including bale.  Imported.\",\"max_quantity\":1,\"desc\":\"Swarovski Crystal Elements Sterling Silver Pendant\",\"order\":4,\"id\":\"J525X\",\"price\":\"$29.99\",\"selected\":0,\"available\":1,\"type_desc\":\"Other\"}],\"catalog\":\"FTDDirect\",\"gbb\":true,\"product_name\":\"You Had Me at Pink Bouquet\"}");
	}

}
