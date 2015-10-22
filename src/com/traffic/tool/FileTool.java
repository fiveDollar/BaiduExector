package com.traffic.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileTool {
	public static void write(String html,String path,String charSet){
		File f  = new File(path);
		BufferedWriter bw = null;
		try {
			if(!f.exists()){
				f.createNewFile();
			}
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(f),"utf-8");
			bw = new BufferedWriter(out);
			bw.write(html);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void write(String html,String path,String charSet,boolean isAppend){
		File f  = new File(path);
		BufferedWriter bw = null;
		try {
			if(!f.exists()){
				f.createNewFile();
			}
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(f,isAppend),"utf-8");
			bw = new BufferedWriter(out);
			bw.write(html);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String read(String path,String charSet) {
		File f  = new File(path);
		String html = "";
		InputStream in = null;
		InputStreamReader ir = null;
		BufferedReader br = null;
		try {
			in = new FileInputStream(f);
			ir = new InputStreamReader(in,charSet);
			br = new BufferedReader(ir);
			String line = "";
			while((line = br.readLine())!=null){
				html += line+"\r\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					if(in!=null)
						in.close();
					if(ir!=null)
						ir.close();
					if(br!=null)
						br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return html;
	}
}
