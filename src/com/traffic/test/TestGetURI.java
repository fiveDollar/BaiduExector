package com.traffic.test;

import java.net.URI;

import org.apache.http.client.methods.HttpGet;

public class TestGetURI {

	public static void main(String[] args) {
//		
//		URI uri = new URI()
		HttpGet httpget =new HttpGet("https://www.baidu.com/?ssid=0&from=844b&pu=sz%401320_1001%2Cta%40iphone_2_4.2_3_537&logid=11082940213867178134&ms=1&action=getplus&merge=1&plusmd5=%7B%7D&_=1445911129653&callback=jsonp1");
		System.out.println(httpget.getURI());
	}

}
