package com.traffic.test;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import cm.traffic.send.Sender;

public class TestHttpContext {
	  private static HttpContext localContext = new BasicHttpContext();
	    private static HttpClientContext context = HttpClientContext
	            .adapt(localContext);
	 
	    public static void main(String[] args) throws Exception {
	    	BasicClientCookie cookie =new BasicClientCookie("test","test");
			cookie.setDomain("115.159.3.51");
			cookie.setPath("/");
			CookieStore cs = new BasicCookieStore();
			cs.addCookie(cookie);
			CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cs).build();
	    	try {
	    		
	   	        HttpGet httpGet = new HttpGet("http://115.159.3.51/cookieShow.php");
	   	        httpGet.setHeaders(setHeaders());
	   	        CloseableHttpResponse response = httpClient.execute(httpGet,
	                       context);
	   	        try {
	                HttpEntity responseEntity = response.getEntity();
	                System.out.println(EntityUtils.toString(responseEntity));
	 
	            } finally {
	                response.close();
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}

	    	try {
	    		BasicClientCookie cookie2 =new BasicClientCookie("test1","test1");
				cookie2.setDomain("115.159.3.51");
				cookie2.setPath("/");
				CookieStore cs2 = new BasicCookieStore();
				cs2.addCookie(cookie2);
				context.setCookieStore(cs2);
//	    		CloseableHttpClient httpClient = HttpClients.custom().build();
	   	        HttpGet httpGet = new HttpGet("http://115.159.3.51/cookieShow.php");
	   	        CloseableHttpResponse response = httpClient.execute(httpGet,
	                       context);
//	   	       System.out.println(context.getR);
	   	        Sender.printHeader(context.getRequest().getAllHeaders());
	   	        try {
	                HttpEntity responseEntity = response.getEntity();
	                System.out.println(EntityUtils.toString(responseEntity));
	 
	            } finally {
	                response.close();
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}
	     
	    }
	    
	    public static Header[] setHeaders() {
			String[] headerStrArr = "Connection keep-alive\r\nAccept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUpgrade-Insecure-Requests 1\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36".split("\r\n");
			Header[] headers = new Header[headerStrArr.length];
			for (int i = 0; i < headerStrArr.length; i++) {
				headers[i] = new BasicHeader(headerStrArr[i].split(" ",2)[0], headerStrArr[i].split(" ",2)[1]);
			}
			return headers;
		}
}
