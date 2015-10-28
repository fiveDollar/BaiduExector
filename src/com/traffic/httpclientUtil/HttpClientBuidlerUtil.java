package com.traffic.httpclientUtil;


import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;


public class HttpClientBuidlerUtil {
	HttpClientBuilder httpBuilder =HttpClients.custom();
	
	public HttpClientBuidlerUtil setProxy(String host,int port){
		HttpHost proxy = new HttpHost(host, port);
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(
				proxy);
		httpBuilder.setRoutePlanner(routePlanner);
		return this;
	}
	public HttpClientBuidlerUtil setRetryHandler(){
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException arg0, int arg1, HttpContext arg2) {
				return false;
			}
		};
		httpBuilder.setRetryHandler(myRetryHandler);
		return this;
	}
	public HttpClientBuidlerUtil setConnectionManager(int maxTotal){
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(maxTotal);
		httpBuilder.setConnectionManager(cm);
//		httpBuilder.set
		return this;
	}
	public HttpClientBuidlerUtil setCookieStore(CookieStore cs){
		httpBuilder.setDefaultCookieStore(cs);
		return this;
	}
	
	public CloseableHttpClient build(){
		return httpBuilder.build();
	}
}
