package com.traffic.request;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import com.traffic.Response.MyResponse;
import com.traffic.httpclientUtil.CookieUtil;
import com.traffic.httpclientUtil.ParamerUtil;

public class WeatherRequest extends BaiduRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	
	public void init(Header[] headers,Document doc,Header[] lastHeaders) {
		setScheme("https");
		setHost("www.baidu.com");
		setPath("/");
		setHeaderStr("Accept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\nAccept */*\r\nReferer https://www.baidu.com/\r\nConnection keep-alive");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setCookie(headers,lastHeaders);
		setDoc(doc);
		setParamar();
	}
	
	private void setParamar(){
		List<NameValuePair> parameters = ParamerUtil.getSsidParameters(doc);
		startTime = System.currentTimeMillis();
		parameters.add(new BasicNameValuePair("ms","1"));
		parameters.add(new BasicNameValuePair("action","getplus"));
		parameters.add(new BasicNameValuePair("merge",doc.select("#admin").attr("data-merge")));
		parameters.add(new BasicNameValuePair("plusmd5","%7B%7D"));
		parameters.add(new BasicNameValuePair("_",startTime+""));
		parameters.add(new BasicNameValuePair("callback","jsonp1"));
		setParameters(parameters);
	}
	
	private void setCookie(Header[] homeHeaders,Header[] lastHeaders){
		BasicCookieStore tmpCookieStore = new BasicCookieStore();
		tmpCookieStore.addCookie(CookieUtil.GetCookieFromHeader(homeHeaders, "BAIDUID"));
		tmpCookieStore.addCookie(CookieUtil.GetCookieFromHeader(homeHeaders, "H_WISE_SIDS"));
		if(lastHeaders!=null)
			tmpCookieStore.addCookie(CookieUtil.GetCookieFromHeader(lastHeaders, "__bsi"));
		else
			tmpCookieStore.addCookie(CookieUtil.GetCookieFromHeader(homeHeaders, "__bsi"));
		setCookieStore(tmpCookieStore);
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}
}
