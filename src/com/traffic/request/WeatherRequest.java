package com.traffic.request;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import com.traffic.Response.MyRespone;
import com.traffic.httpclientUtil.CookieUtil;
import com.traffic.httpclientUtil.ParamerUtil;

public class WeatherRequest extends BaiduRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	
	public void init(Header[] headers,Document doc,MyRespone lastResponse) {
		setScheme("https");
		setHost("www.baidu.com");
		setPath("/");
		setHeaderStr("Accept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\nAccept */*\r\nReferer https://www.baidu.com/\r\nCookie BAIDUID=498FB2AA4AA3DD7B7A500D58F62F3ABC:FG=1; H_WISE_SIDS=100414_100805_100427_100935_100040_100295_100288_100381; __bsi=18308526112213641690_00_0_I_R_48_0303_C02F_N_I_I_0\r\nConnection keep-alive");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setCookie(headers);
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
	
	private void setCookie(Header[] headers){
		BasicCookieStore cookieStore = new BasicCookieStore(); 
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "BAIDUID"));
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "H_WISE_SIDS"));
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "__bsi"));
		setCookieStore(cookieStore);
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
