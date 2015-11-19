package com.traffic.proxy;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.traffic.request.MyRequest;

public class CheckProxyRequest extends MyRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	
	public void init() {
		setScheme("http");
		setHost("115.159.3.51");
		setPath("/cookieShow.php");
		setHeaderStr("CheckProxy 1");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setCookie();
	}
	
	public void setCookie(){
		BasicCookieStore cookieStore = new BasicCookieStore(); 
		BasicClientCookie bc = new BasicClientCookie("proxyCheck", "1");
		bc.setDomain("115.159.3.51");
		bc.setPath("/");
		cookieStore.addCookie(bc);
		setCookieStore(cookieStore);
	}
	
}
