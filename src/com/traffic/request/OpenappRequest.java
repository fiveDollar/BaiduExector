package com.traffic.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import com.traffic.Response.MyResponse;
import com.traffic.httpclientUtil.CookieUtil;
import com.traffic.httpclientUtil.ParamerUtil;

public class OpenappRequest extends BaiduRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	
	public void init(Header[] headers,Document doc,MyResponse lastResponse) {
		setScheme("https");
		setHost("m.baidu.com");
		setPath("/static/search/inapp/lib/openapp-v3-min.js");
		setHeaderStr(":host m.baidu.com\r\naccept-encoding gzip, deflate, sdch\r\naccept-language zh-CN,zh;q=0.8\r\nuser-agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\n:path /static/search/inapp/lib/openapp-v3-min.js?t=1445237915567\r\naccept */*\r\n:version HTTP/1.1\r\nreferer https://www.baidu.com/\r\n:scheme https\r\n:method GET");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setCookie(headers);
		setDoc(doc);
		setParamar();
	}
	
	private void setParamar(){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		startTime = System.currentTimeMillis();
		parameters.add(new BasicNameValuePair("t",startTime+""));
		setParameters(parameters);
	}
	
	private void setCookie(Header[] headers){
		BasicCookieStore cookieStore = new BasicCookieStore(); 
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "BAIDUID"));
		cookieStore.addCookie(CookieUtil.CookieBuilder("plus_lsv",doc.getElementById("index-card").attr("data-lsversion"), getHost(), getPath()));
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "H_WISE_SIDS"));
		cookieStore.addCookie(CookieUtil.CookieBuilder("PLUS","1", getHost(), getPath()));
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
