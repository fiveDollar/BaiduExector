package com.traffic.request;



public class HomePageRequest extends BaiduRequest {
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;

	public void init(){
		setScheme("https");
		setHost("www.baidu.com");
		setPath("/");
		setHeaderStr("Connection keep-alive\r\nAccept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUpgrade-Insecure-Requests 1\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
	}
	
}
