package com.traffic.httpclientUtil;

import java.util.Date;

import org.apache.http.Header;
import org.apache.http.impl.cookie.BasicClientCookie;

public class CookieUtil {
	public static BasicClientCookie GetCookieFromHeader(Header[] headers,String key) {
		for (Header header : headers) {
			String value = header.getValue();
			if(value.contains(key)){
				String pramars[] = value.split(";");
				String cookieValue = "";
				String cookieDomain = "";
				String cookiePath = "";
				for (String p : pramars) {
					if (p.contains(key))
						cookieValue = p.split(key+"=")[1];
					if (p.contains("domain"))
						cookieDomain = p.split("domain=")[1];
					if (p.contains("path"))
						cookiePath = p.split("path=")[1];
				}
				return CookieBuilder(key, cookieValue, cookieDomain, cookiePath);
			}
		}
		return null;
	}
	
	public static BasicClientCookie CookieBuilder(String key,String value,String domain,String path){
		BasicClientCookie cookie =new BasicClientCookie(key, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		
		return cookie;
	}
	public static BasicClientCookie CookieBuilder(String key,String value,String domain,String path,int expires){
		BasicClientCookie cookie =new BasicClientCookie(key, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setExpiryDate(new Date(System.currentTimeMillis()+expires));
		return cookie;
	}
	
}
