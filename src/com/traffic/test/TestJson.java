package com.traffic.test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TestJson {
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.parse(new File("test.html"),"utf-8");
		Element e =doc.select("#index-card").get(0);
		System.out.println(e);
		
	}
}
 