package com.traffic.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import com.traffic.httpclientUtil.CookieUtil;
import com.traffic.httpclientUtil.ParamerUtil;


public class WordFinalRequest1 extends MyRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	private String word;
	private int tryTime=0;
	public void init(Header[] headers,Document doc,String word,Header[] lastHeader,int tryTime) {
		setScheme("https");
		setHost("www.baidu.com");
		setPath("/s");
		setWord(word);
		setTryTime(tryTime);
		setHeaderStr("Accept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\nAccept application/json\r\nReferer https://www.baidu.com/\r\nX-Requested-With XMLHttpRequest\r\nConnection keep-alive");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setCookie(headers,lastHeader);
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
		String nowStr = System.currentTimeMillis()+"";
		parameters.add(new BasicNameValuePair("ts",nowStr.substring(nowStr.length()-7)));
		parameters.add(new BasicNameValuePair("t_kt",(new Random().nextInt(30)+30)+""));
		parameters.add(new BasicNameValuePair("rsv_iqid",doc.getElementsByAttribute("name").get(0).val()));
		parameters.add(new BasicNameValuePair("sa","ib"));
		parameters.add(new BasicNameValuePair("pvt",doc.select("noscript").get(0).text().split("pvt=")[1].split("&")[0]));
		parameters.add(new BasicNameValuePair("ms","1"));
		startTime = System.currentTimeMillis();
		parameters.add(new BasicNameValuePair("rsv_sug4",(startTime-1000)+""));
		parameters.add(new BasicNameValuePair("inputT",(startTime-1000-new Random().nextInt(1000))+""));
		parameters.add(new BasicNameValuePair("ss","100"));
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

	public int getTryTime() {
		return tryTime;
	}

	public void setTryTime(int tryTime) {
		this.tryTime = tryTime;
	}
}
