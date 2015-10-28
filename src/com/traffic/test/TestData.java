package com.traffic.test;

import java.util.Date;
import java.util.Random;

public class TestData {
	public static void main(String[] args) {
//		System.out.println();
		System.out.println(System.currentTimeMillis()+604800000);
		Date d = new Date(System.currentTimeMillis()+604800000);
		System.out.println(d);
	}
}
