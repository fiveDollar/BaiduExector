package com.traffic.songsendUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.traffic.Response.MyResponse;
import com.traffic.httpclientUtil.EntityReaderUtil;
import com.traffic.httpclientUtil.HttpGetBuilderUtil;
import com.traffic.httpclientUtil.MyHttpClients;
import com.traffic.httpclientUtil.RequestSenderUtil;
import com.traffic.proxy.MyProxy;
import com.traffic.proxy.ProxyService;
import com.traffic.request.MyRequest;

public class ReadConf {
	public static void main(String[] args) {
		ArrayList<MyRequestForSong> reqList = read("data/request.conf");
		ProxyService.start();
		MyProxy mp = new MyProxy();
//		System.out.println(mp);
		for (MyRequestForSong myRequestForSong : reqList) {
			while(myRequestForSong.execTime>0){
				HttpContext context = new BasicHttpContext();
				HttpClientContext httpContext = HttpClientContext.adapt(context);
				httpContext.setCookieStore(myRequestForSong.getCookieStore());
				mp.changeWithcheck();
				CloseableHttpClient httpClient = MyHttpClients.custom()
						.setProxy(mp.host, mp.port)
						.setRetryHandler()
						.build();
				System.out.println(mp);	
				HttpGet httpGet = HttpGetBuilderUtil.Builder(myRequestForSong);
				MyResponse myR = RequestSenderUtil.send(httpClient, httpGet, httpContext, true);
				
				if(myR==null||myR.getHttpClientContext().getResponse() == null||myR.getHttpClientContext().getResponse().getStatusLine().getStatusCode()!=200){
					System.out.println("执行失败 TimeOut");
					continue;
				}else{
					myRequestForSong.execTime--;
					try {
						System.out.println("成功 剩余  "+myRequestForSong.execTime+"次 "+myRequestForSong.getURI());
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
//				String webCon = EntityReaderUtil.ReadeEntity(myR.getBhe(), "utf-8");
//				System.out.println(webCon);
			}
		}
	}
	public static ArrayList<MyRequestForSong> read(String path){
		ArrayList<MyRequestForSong> myRequestList = new ArrayList<>();
		File f = new File(path);
		if(!f.exists()){
			return myRequestList;
		}
		InputStream in = null;
		InputStreamReader ir = null;
		BufferedReader br = null;
		try {
			in = new FileInputStream(f);
			ir = new InputStreamReader(in);
			br = new BufferedReader(ir);
			String line = null;
			MyRequestForSong mq = new MyRequestForSong();
			while((line = br.readLine())!=null){
				if(line.startsWith("headerStr")){
					mq.setHeaderStr(line.split("=", 2)[1].replace("&", "\r\n"));
					mq.setHeaders();
				}else if(line.startsWith("scheme"))
					mq.setScheme(line.split("=", 2)[1]);
				else if(line.startsWith("host"))
					mq.setHost(line.split("=", 2)[1]);
				else if(line.startsWith("path"))
					mq.setPath(line.split("=", 2)[1]);
				else if(line.startsWith("cookie")){
					String cookies[] = line.split("=", 2)[1].split(",");
					mq.setCookieStore(new BasicCookieStore());
					for (String cookie : cookies) {
						BasicClientCookie bc = new BasicClientCookie(cookie.split(":")[0], cookie.split(":")[1]);
						bc.setDomain(mq.getHost());
						bc.setPath("/");
						mq.getCookieStore().addCookie(bc);
					}
				}else if(line.startsWith("execTime"))
					mq.setExecTime(Integer.parseInt(line.split("=", 2)[1]));
				else if(line.startsWith("parameters")){
					List<NameValuePair> parameters = new ArrayList<NameValuePair>();
					String parameterArr[] = line.split("=", 2)[1].split(",");
					for (String paramStr : parameterArr) {
						parameters.add(new BasicNameValuePair(paramStr.split(":")[0], paramStr.split(":")[1]));
					}
					mq.setParameters(parameters);
				}
				
				if(line.equals("")){
					myRequestList.add(mq);
					mq = new MyRequestForSong();
				}
			}
			myRequestList.add(mq);
			return myRequestList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(br!=null)
					br.close();
				if(ir!=null)
					ir.close();
				if(in!=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return myRequestList;
	}
	static class MyRequestForSong extends MyRequest{
		private int execTime = 0;
		public void setExecTime(int execTime){
			this.execTime = execTime;
		}
		public int getExecTime(){
			return this.execTime; 
		}
	}
}
