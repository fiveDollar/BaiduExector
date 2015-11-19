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

public class HisRequest extends MyRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	private String word;
	public void init(Header[] headers,Document doc,String word,Header[] lasHeaders) {
		setScheme("https");
		setHost("m.baidu.com");
		setPath("/his");
		setWord(word);
		setHeaderStr("Accept-Encoding gzip, deflate, sdch\r\nHost m.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\nAccept */*\r\nReferer https://www.baidu.com/\r\nConnection keep-alive");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setCookie(headers,lasHeaders);
		setDoc(doc);
		setParamar();
	}
	
	private void setParamar(){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		
		parameters.add(new BasicNameValuePair("callback","jsonp1"));
		parameters.add(new BasicNameValuePair("type","3"));
		parameters.add(new BasicNameValuePair("pic","1"));
		parameters.add(new BasicNameValuePair("net",doc.select("#commonBase").attr("data-tanet")));
		startTime = System.currentTimeMillis();
		String startTimeStr = startTime+"";
		try {
			parameters.add(new BasicNameValuePair("hisdata",URLEncoder.encode("[{\"kw\":\""+getWord()+"\",\"time\":"+startTimeStr.substring(0, 10)+"}]","utf-8") ));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		parameters.add(new BasicNameValuePair("_",startTimeStr));
		setParameters(parameters);
	}
	
	private void setCookie(Header[] headers,Header[] lastHeader){
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "BAIDUID"));
		cookieStore.addCookie(CookieUtil.CookieBuilder("plus_lsv",doc.getElementById("index-card").attr("data-lsversion"), getHost(), getPath()));
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "H_WISE_SIDS"));
		cookieStore.addCookie(CookieUtil.CookieBuilder("PLUS","1", getHost(), getPath()));
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(lastHeader, "__bsi"));
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

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
}
