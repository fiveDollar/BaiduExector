package com.traffic.httpclientUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

public class ParamerUtil {

	/**
	 * @param doc
	 * @return ssid from pu qid
	 * get [ssid from pu qid/logid] from homePage's Document
	 * [ssid=0, from=844b, pu=sz%401320_2001%2Cta%40iphone_1_8.0_3_600, qid=10099152867365468744]
	 */
	public static List<NameValuePair> getSsidParameters(Document doc){
		String ssidStr = doc.select("#commonBase").attr("data-prepath");
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (String parameterStr : ssidStr.split("#")) {
			if(parameterStr.contains("ssid")||parameterStr.contains("from")||parameterStr.contains("pu")||parameterStr.contains("qid")||parameterStr.contains("logid")){
				parameters.add(new BasicNameValuePair(parameterStr.split("=",2)[0],parameterStr.split("=",2)[1]));
			}
		}
		if(parameters.size()==3)
			parameters.add(new BasicNameValuePair("logid",doc.select("#commonBase").attr("data-logid")));
		return parameters;
	}
	
}
