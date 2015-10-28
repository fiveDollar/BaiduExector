package com.traffic.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import com.traffic.Response.MyResponse;
import com.traffic.httpclientUtil.CookieUtil;

public class FileRequest extends BaiduRequest {
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	
	public void init(Header[] headers,Document doc) {
		setScheme("https");
		setHost("www.baidu.com");
		setPath("/");
		setHeaderStr("Connection keep-alive\r\nAccept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUpgrade-Insecure-Requests 1\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setCookie(headers);
		setParamar(doc);
		
	}
	
	public void setCookie(Header[] headers){
		BasicCookieStore cookieStore = new BasicCookieStore(); 
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "BAIDUID"));
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "H_WISE_SIDS"));
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "__bsi"));
		
		setCookieStore(cookieStore);
	}

	public void setParamar(Document doc){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("action", "static"));
		parameters.add(new BasicNameValuePair("ms", "1"));
		parameters.add(new BasicNameValuePair("version", getVersion(doc)));
		parameters.add(new BasicNameValuePair("callback","B.getCode"));
		parameters.add(new BasicNameValuePair("r",new Random().nextInt(1000)+""));
		setParameters(parameters);
	}
	private String getVersion(Document doc){
		return doc.select("[data-version]").attr("data-version").replaceAll("@\\d", "@0");
	}
}
