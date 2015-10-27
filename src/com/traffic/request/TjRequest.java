package com.traffic.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import com.traffic.httpclientUtil.CookieUtil;

public class TjRequest extends BaiduRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	
	public void init(Header[] homeHeaders,Document doc,Header[] lastHeaders) {
		setScheme("https");
		setHost("www.baidu.com");
		setPath("/static/tj.gif");
		setHeaderStr("Accept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\nccept image/webp,image/*,*/*;q=0.8\r\nReferer https://www.baidu.com/\r\nConnection keep-alive");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setCookie(homeHeaders,lastHeaders);
		setDoc(doc);
		setParamar();
	}
	public void setParamar(){
		startTime = System.currentTimeMillis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("time", startTime+""));
		setParameters(parameters);
	}
	
	public void setCookie(Header[] homeHeaders,Header[] lastHeaders){
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(homeHeaders, "BAIDUID"));
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(homeHeaders, "H_WISE_SIDS"));
		if(lastHeaders==null)
			cookieStore.addCookie(CookieUtil.GetCookieFromHeader(homeHeaders, "__bsi"));
		else{
			cookieStore.addCookie(CookieUtil.GetCookieFromHeader(lastHeaders, "__bsi"));
			BasicClientCookie plus_lsv =  CookieUtil.CookieBuilder("plus_lsv", doc.select("[data-lsversion]").attr("data-lsversion"), getHost(), "/");
			cookieStore.addCookie(plus_lsv);
			BasicClientCookie Plus =  CookieUtil.CookieBuilder("PLUS", "1", getHost(), "/");
			cookieStore.addCookie(Plus);
		}
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
