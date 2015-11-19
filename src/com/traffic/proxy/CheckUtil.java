package com.traffic.proxy;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.traffic.Response.MyResponse;
import com.traffic.httpclientUtil.EntityReaderUtil;
import com.traffic.httpclientUtil.HttpGetBuilderUtil;
import com.traffic.httpclientUtil.MyHttpClients;
import com.traffic.httpclientUtil.RequestSenderUtil;
import com.traffic.request.CheckProxyRequest;

public class CheckUtil {
	public static void main(String[] args) {
		HttpContext context = new BasicHttpContext();
		HttpClientContext httpContext = HttpClientContext.adapt(context); 
		CloseableHttpClient httpClient = MyHttpClients.custom()
				.setProxy("222.92.117.87", 31287)
				.build();
		CheckProxyRequest cr = new CheckProxyRequest();
		cr.init();
		httpContext.setCookieStore(cr.getCookieStore());
		try {
			
			HttpGet httpGet = HttpGetBuilderUtil.Builder(cr);
			MyResponse myR = RequestSenderUtil.send(httpClient, httpGet, httpContext, true);
			String webCon = EntityReaderUtil.ReadeEntity(myR.getBhe(), "utf-8");
			System.out.println(webCon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
