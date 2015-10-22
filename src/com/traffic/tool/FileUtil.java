package com.traffic.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static List<String> readLine(String path,String charSet){
		File file = new File(path);
		InputStreamReader in = null;
		BufferedReader br = null; 
		List<String> lineList = new ArrayList<String>();
		try {
			in = new InputStreamReader(new FileInputStream(file), charSet);
			br = new BufferedReader(in);
			String line = null;
			while((line = br.readLine())!=null){
//				System.out.println(line);
				lineList.add(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(in!=null)
					in.close();
				if(br!=null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lineList;
	}

}
