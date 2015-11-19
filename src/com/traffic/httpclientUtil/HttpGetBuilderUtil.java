package com.traffic.httpclientUtil;

import java.net.URISyntaxException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;

import com.traffic.request.MyRequest;

public class HttpGetBuilderUtil {
	public static HttpGet Builder(MyRequest baiduRequest){
		HttpGet httpGet = new HttpGet();
		try {
			httpGet.setURI(baiduRequest.getURI());
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		if (baiduRequest.getHeaders()!=null) 
			httpGet.setHeaders(baiduRequest.getHeaders());
		
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(baiduRequest.getSocketTimeout())
				.setConnectTimeout(baiduRequest.getConnectTimeout())
				.build();
		httpGet.setConfig(requestConfig);
		return httpGet;
	}
}
