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

public class TCRequestShop extends MyRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	
	public void init(Document doc) {
		setScheme("https");
		setHost("m.baidu.com");
		setPath("/tc");
		setHeaderStr("Accept-Encoding gzip, deflate, sdch\r\nHost m.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\nAccept image/webp,image/*,*/*;q=0.8\r\nReferer https://www.baidu.com/\r\nConnection keep-alive");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setDoc(doc);
		setParamar();
	}
	
	private void setParamar(){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		startTime = System.currentTimeMillis();
		List<NameValuePair> ssidParameters = ParamerUtil.getSsidParameters(doc);
		parameters.add(new BasicNameValuePair("tcreq4log","1"));
		parameters.addAll(ssidParameters);
		parameters.add(new BasicNameValuePair("ct","10"));
		parameters.add(new BasicNameValuePair("cst","1"));
		parameters.add(new BasicNameValuePair("ref","index_iphone"));
		parameters.add(new BasicNameValuePair("lid",ssidParameters.get(3).getValue()));
		parameters.add(new BasicNameValuePair("w",doc.select("#commonBase").attr("data-pn") + "_" + doc.select("#commonBase").attr("data-rn") + "_"));
		parameters.add(new BasicNameValuePair("sid",doc.select("#commonBase").attr("data-sids")));
		parameters.add(new BasicNameValuePair("clk_from","shopping"));
		parameters.add(new BasicNameValuePair("clk_info","a2_l1"));
		parameters.add(new BasicNameValuePair("clk_extra","p_11503"));
		parameters.add(new BasicNameValuePair("card_index","3"));
		parameters.add(new BasicNameValuePair("r",startTime+""));
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
