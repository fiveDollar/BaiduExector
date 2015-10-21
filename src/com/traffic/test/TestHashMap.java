package com.traffic.test;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.client.cache.HeaderConstants;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.HeaderGroup;

public class TestHashMap {
	public static void main(String[] args) {
		HashMap<String , String > map = new HashMap<>();
		map.put("1", "2");
		map.put("3", "4");
		map.put("1", "4");
		
		Iterator<String> keySetIterator = map.keySet().iterator();
		while(keySetIterator.hasNext()){
			String key = keySetIterator.next();
			System.out.println(key);
			System.out.println(map.get(key));
		}
		Header header = new BasicHeader("", "");
		
	}
}
