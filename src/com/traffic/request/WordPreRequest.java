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

public class WordPreRequest extends MyRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	private String word;
	private int jsonIndex=0;
	public void init(Document doc,String word,int jsonIndex) {
		
		setScheme("https");
		setHost("www.baidu.com");
		setPath("/s");
		setWord(word);
		setJsonIndex(jsonIndex);
		setHeaderStr("Accept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\nAccept application/json\r\nReferer https://www.baidu.com/\r\nX-Requested-With XMLHttpRequest\r\nConnection keep-alive");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setDoc(doc);
		setParamar();
	}
	
	private void setParamar(){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		try {
			parameters.add(new BasicNameValuePair("word",URLEncoder.encode(word,"utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		parameters.add(new BasicNameValuePair("tn","iphone"));
		parameters.add(new BasicNameValuePair("rn","10"));
		parameters.add(new BasicNameValuePair("wpo","base"));
		List<NameValuePair> ssidParameters = ParamerUtil.getSsidParameters(doc);
		parameters.addAll(ssidParameters);
		String nowStr = System.currentTimeMillis()+"";
		parameters.add(new BasicNameValuePair("ts",nowStr.substring(nowStr.length()-7)));
		parameters.add(new BasicNameValuePair("from","844b"));
		parameters.add(new BasicNameValuePair("ms","1"));
		parameters.add(new BasicNameValuePair("mod","1"));
		parameters.add(new BasicNameValuePair("isid",doc.select("#commonBase").attr("data-logid")));
		if(jsonIndex==1||jsonIndex==0)
			parameters.add(new BasicNameValuePair("sa","ib"));
		else
			parameters.add(new BasicNameValuePair("sa","is_"+(jsonIndex-1)));
		parameters.add(new BasicNameValuePair("at","3"));
		parameters.add(new BasicNameValuePair("ss","100"));
		startTime = System.currentTimeMillis();
		parameters.add(new BasicNameValuePair("_",startTime+""));
		setParameters(parameters);
	}
	
//	private void setCookie(Header[] headers,Header[] lastHeader){
//		BasicCookieStore cookieStore = new BasicCookieStore();
//		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "BAIDUID"));
//		cookieStore.addCookie(CookieUtil.CookieBuilder("plus_lsv",doc.getElementById("index-card").attr("data-lsversion"), getHost(), getPath()));
//		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "H_WISE_SIDS"));
//		cookieStore.addCookie(CookieUtil.CookieBuilder("PLUS","1", getHost(), getPath()));
//		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(lastHeader, "__bsi"));
//		setCookieStore(cookieStore);
//	}

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

	public int getJsonIndex() {
		return jsonIndex;
	}

	public void setJsonIndex(int jsonIndex) {
		this.jsonIndex = jsonIndex;
	}

}
