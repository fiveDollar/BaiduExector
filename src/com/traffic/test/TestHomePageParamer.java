package com.traffic.test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TestHomePageParamer {
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.parse(new File("test.html"),"utf-8");
		Elements es = doc.select("[data-version]");
		for (int i = 0; i < es.size(); i++) {
			System.out.println(es.get(i).attr("data-version"));
		}
	}
}
