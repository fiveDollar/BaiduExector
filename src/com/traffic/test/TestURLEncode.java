package com.traffic.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class TestURLEncode {
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String word = "[{\"kw\":\"À’÷›’–∆∏\",\"time\":1445910500}]";
//		String code = URLEncoder.encode(word, "utf-8");
//		System.out.println(code);
		String code = "%E8%8B%8F%E5%B7%9E%E6%8B%9B%E8%81%98";
		System.out.println(URLDecoder.decode(code, "utf-8"));
	}
}
