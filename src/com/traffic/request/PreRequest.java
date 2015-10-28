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

public class PreRequest extends BaiduRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	private int preTimes = 0;
	private String preWord;
	public void init(Header[] headers,Document doc,Header[] lastTimeHeader,int preTimes,String preWord) {
		setScheme("https");
		setHost("m.baidu.com");
		setPreTimes(preTimes);
		setPreWord(preWord);
		setPath("/su");
		setHeaderStr(":host m.baidu.com\r\naccept-encoding gzip, deflate, sdch\r\naccept-language zh-CN,zh;q=0.8\r\nuser-agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\n:path /su?pre=1&p=3&ie=utf-8&from=wise_web&net=1&os=2&sp=240&callback=jsonp3&wd=t&_=1445237998642\r\naccept */*\r\n:version HTTP/1.1\r\nreferer https://www.baidu.com/\r\n:scheme https\r\n:method GET");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setCookie(headers,lastTimeHeader);
		setDoc(doc);
		setParamar();
	}
	
	private void setParamar(){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		startTime = System.currentTimeMillis();
		parameters.add(new BasicNameValuePair("pre","1"));
		parameters.add(new BasicNameValuePair("p","3"));
		parameters.add(new BasicNameValuePair("ie","utf-8"));
		parameters.add(new BasicNameValuePair("from","wise_web"));
		parameters.add(new BasicNameValuePair("net",doc.select("#commonBase").attr("data-tanet")));
		parameters.add(new BasicNameValuePair("os",doc.select("#commonBase").attr("data-osid")));
		parameters.add(new BasicNameValuePair("sp",doc.select("#commonBase").attr("data-spd")));
		parameters.add(new BasicNameValuePair("callback","jsonp"+(getPreTimes())+2));
		parameters.add(new BasicNameValuePair("wd",getPreWord()));
		parameters.add(new BasicNameValuePair("_",startTime+""));
		setParameters(parameters);
	}
	
	private void setCookie(Header[] headers,Header[] lastTimeHeader){
		BasicCookieStore cookieStore = new BasicCookieStore(); 
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "BAIDUID"));
		cookieStore.addCookie(CookieUtil.CookieBuilder("plus_lsv",doc.getElementById("index-card").attr("data-lsversion"), getHost(), getPath()));
		cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "H_WISE_SIDS"));
		cookieStore.addCookie(CookieUtil.CookieBuilder("PLUS","1", getHost(), getPath()));
		if(preTimes > 0){
			cookieStore.addCookie(CookieUtil.GetCookieFromHeader(headers, "__bsi"));
		}
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

	public int getPreTimes() {
		return preTimes;
	}

	public void setPreTimes(int preTimes) {
		this.preTimes = preTimes;
	}

	public String getPreWord() {
		return preWord;
	}

	public void setPreWord(String preWord) {
		this.preWord = preWord;
	}
}
