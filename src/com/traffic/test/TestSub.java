package com.traffic.test;

public class TestSub {
	public static void main(String[] args) {
		String nowStr = System.currentTimeMillis()+"";
		System.out.println(nowStr.substring(nowStr.length()-7));
	}
}
