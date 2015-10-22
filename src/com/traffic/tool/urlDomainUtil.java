package com.traffic.tool;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class urlDomainUtil {
	public static String getDomain(String url) throws MalformedURLException {
		String host = new URL(url).getHost().toLowerCase();// �˴���ȡֵת��ΪСд
		Pattern pattern = Pattern.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.��˾|\\.�й�|\\.����)");
		Matcher matcher = pattern.matcher(host);
		while (matcher.find()) {
			return matcher.group().substring(0,matcher.group().lastIndexOf("."));
		}
		return null;
	}
}
