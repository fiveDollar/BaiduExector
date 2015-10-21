package com.traffic.test;

public class TestSplit {
	public static void main(String[] args) {
		String testStr = "Connection keep-alive\r\nAccept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUpgrade-Insecure-Requests 1\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36";
		String testStrArr[] = testStr.split("\r\n");
		for (int i = 0; i < testStrArr.length; i++) {
			System.out.println(testStrArr[i].split(" ", 1)[0]);
		}
	}
}
