package com.traffic.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import com.traffic.httpclientUtil.CookieUtil;
import com.traffic.httpclientUtil.ParamerUtil;

public class TcResultRequest extends MyRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	
	public void init(Document doc,String word) {
		setScheme("https");
		setHost("m.baidu.com");
		setPath("/tc");
		setHeaderStr("Accept-Encoding gzip, deflate, sdch\r\nHost m.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\nAccept image/webp,image/*,*/*;q=0.8\r\nReferer https://www.baidu.com/\r\nConnection keep-alive");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
//		setCookie(headers);
		setDoc(doc);
		setParamar(word);
	}
	
	private void setParamar(String word){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		startTime = System.currentTimeMillis();
		List<NameValuePair> ssidParameters = ParamerUtil.getSsidParameters(doc);
		parameters.add(new BasicNameValuePair("tcreq4log","1"));
		parameters.addAll(ssidParameters);
		parameters.add(new BasicNameValuePair("ct","10"));
		parameters.add(new BasicNameValuePair("cst","1"));
		parameters.add(new BasicNameValuePair("clk_from","mainpage"));
		parameters.add(new BasicNameValuePair("clk_info","search-newi"));
		parameters.add(new BasicNameValuePair("ref","index_iphone"));
		parameters.add(new BasicNameValuePair("lid",ssidParameters.get(3).getValue()));
		try {
			parameters.add(new BasicNameValuePair("w",doc.select("#commonBase").attr("data-pn") + "_" + doc.select("#commonBase").attr("data-rn") + "_"+URLEncoder.encode(URLEncoder.encode(word, "utf-8"), "utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setParameters(parameters);
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
