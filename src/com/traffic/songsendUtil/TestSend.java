package com.traffic.songsendUtil;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.traffic.Response.MyResponse;
import com.traffic.httpclientUtil.EntityReaderUtil;
import com.traffic.httpclientUtil.HttpGetBuilderUtil;
import com.traffic.httpclientUtil.MyHttpClients;
import com.traffic.httpclientUtil.RequestSenderUtil;
import com.traffic.request.MyRequest;

public class TestSend {
	
	
	public static void main(String[] args) {
		HttpContext context = new BasicHttpContext();
		HttpClientContext httpContext = HttpClientContext.adapt(context); 
		CloseableHttpClient httpClient = MyHttpClients.custom()
				.setProxy("222.92.117.87", 31287)
				.setRetryHandler()
				.build();
		CookieStore cs = new BasicCookieStore();
		httpContext.setCookieStore(cs);
		BasicClientCookie cookie =new BasicClientCookie("ccc", "b");
		cookie.setDomain("115.159.3.51");
		cookie.setPath("/");
		cs.clear();
		cs.addCookie(cookie);
		MyRequest re = new MyRequest() {
		};
		re.setHeaderStr("Accept-Encoding gzip, deflate, sdch\r\nHost m.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36\r\nAccept image/webp,image/*,*/*;q=0.8\r\nReferer https://www.baidu.com/\r\nConnection keep-alive");
		re.setHeaders();
		re.setScheme("http");
		re.setHost("shanghai.baixing.com");
		re.setPath("/qitazhaopin/a291652800.html");

		HttpGet httpGet = HttpGetBuilderUtil.Builder(re);
		MyResponse myR = RequestSenderUtil.send(httpClient, httpGet, httpContext, true);
		String webCon = EntityReaderUtil.ReadeEntity(myR.getBhe(), "utf-8");
		System.out.println(webCon);
		
	}
	
}
