package com.ftd.cart.ssl.connection;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SSLConnection {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(SSLConnection.class);

	@Bean(name = "httpClient")
	@Profile("qa")
	public CloseableHttpClient getSSLConnectionSocketFactoryQA() {

		CloseableHttpClient httpclient = null;
		try {
			SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

			httpclient = HttpClients.custom()
					.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier()))
					.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
					.build();
			return httpclient;
		} catch (KeyManagementException e1) {
			log.error(e1.getMessage(), e1);
		} catch (NoSuchAlgorithmException e1) {
			log.error(e1.getMessage(), e1);
		} catch (KeyStoreException e1) {
			log.error(e1.getMessage(), e1);
		}
		return httpclient;
	}

	@Bean(name = "httpClient")
	@Profile("production")
	public CloseableHttpClient getSSLConnectionSocketFactoryLive() {

		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.custom()
					.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
					.build();
			return httpclient;
		} catch (Exception e1) {
			log.error(e1.getMessage(), e1);
		}
		return httpclient;
	}

}