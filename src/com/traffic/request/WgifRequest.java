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
import org.jsoup.nodes.Element;

import com.traffic.httpclientUtil.CookieUtil;

public class WgifRequest extends MyRequest{
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	private long startTime = 0;
	private long endTime = 0;
	private Document doc;
	private String word;
	private int tryTime=0;
	public void init(Document doc,String word,Document secondDoc) {
		
		setScheme("https");
		setHost("sp0.baidu.com");
		setPath("/-rU_dTmfKgQFm2e88IuM_a");
		setWord(word);
		setHeaderStr("Accept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\nAccept application/json\r\nReferer https://www.baidu.com/\r\nX-Requested-With XMLHttpRequest\r\nConnection keep-alive");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
		setDoc(doc);
		setParamar(secondDoc);
	}
	
	private void setParamar(Document sencondDoc){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//		try {
//			parameters.add(new BasicNameValuePair("word",URLEncoder.encode(word,"utf-8")));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		data-sids
		String parmas = praseSecondDoc(sencondDoc);
//		System.out.println(praseSecondDoc(sencondDoc));
		parameters.add(new BasicNameValuePair("eid",doc.select("#commonBase").attr("data-sids")));
		parameters.add(new BasicNameValuePair("baiduid",parmas.split("'baiduid':")[1].split(",")[0].replace("'", "").replace(" ", "")));
		
		try {
			parameters.add(new BasicNameValuePair("query",URLEncoder.encode(word, "gb2312")));
			parameters.add(new BasicNameValuePair("queryUtf8",URLEncoder.encode(word, "UTF8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		winHeight=640&winWidth=360&action=init&tag=ecom_wise_listen_n&rand=1447995755324
		
		parameters.add(new BasicNameValuePair("searchid",parmas.split("'searchid':")[1].split(",")[0].replace("'", "").replace(" ", "").toLowerCase()));
		parameters.add(new BasicNameValuePair("osid",parmas.split("'osid':")[1].split(",")[0].replace("'", "").replace(" ", "")));
		parameters.add(new BasicNameValuePair("bwsid",parmas.split("'bwsid':")[1].split(",")[0].replace("'", "").replace(" ", "")));
		parameters.add(new BasicNameValuePair("adt","0"));
		parameters.add(new BasicNameValuePair("adb","0"));
		parameters.add(new BasicNameValuePair("wst","98"));
		parameters.add(new BasicNameValuePair("top","0"));
		parameters.add(new BasicNameValuePair("wise","10"));
		parameters.add(new BasicNameValuePair("bottom","0"));
		parameters.add(new BasicNameValuePair("adpos","t_0_0.00"));
		parameters.add(new BasicNameValuePair("initViewZone","w_1_0.00%3Aw_4_0.41"));
		parameters.add(new BasicNameValuePair("adsHeight","_w1%3A140_w2%3A170_w3%3A144_w4%3A169_w5%3A166_w6%3A167_w7%3A166_w8%3A166_w9%3A166_w10%3A166"));
		parameters.add(new BasicNameValuePair("availHeight","640"));
		parameters.add(new BasicNameValuePair("availWidth","360"));
		parameters.add(new BasicNameValuePair("winHeight","640"));
		parameters.add(new BasicNameValuePair("winWidth","360"));
		parameters.add(new BasicNameValuePair("action","init"));
		parameters.add(new BasicNameValuePair("tag","ecom_wise_listen_n"));
		parameters.add(new BasicNameValuePair("rand",System.currentTimeMillis()+""));
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
	public static String praseSecondDoc(Document doc){
		Element js = doc.getElementById("ec_ls_js");
		return js.toString().split("win.ecom.wise.PARAMS = \\{")[1].split("}")[0];
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
