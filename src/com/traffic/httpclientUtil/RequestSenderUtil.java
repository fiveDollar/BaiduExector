package com.traffic.httpclientUtil;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.traffic.Response.MyResponse;


public class RequestSenderUtil {
	/**
	 * @param httpClient
	 * @param httpGet
	 * @param hcc
	 * @param isWebCon 是否需要读取response内容
	 * @return
	 */
	public static MyResponse send(CloseableHttpClient httpClient,HttpGet httpGet,HttpClientContext hcc,boolean isWebCon){
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet, hcc);
			BufferedHttpEntity bhe = null;
			if(httpResponse.getStatusLine().getStatusCode()==200&&isWebCon){
				bhe = new BufferedHttpEntity(httpResponse.getEntity());
			}else if(httpResponse.getStatusLine().getStatusCode()!=200){
				System.out.println(httpGet.getURI());
				System.out.println("RepsonseStatus: "+httpResponse.getStatusLine().getStatusCode());
			}
			return new MyResponse(hcc, bhe);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(httpResponse != null)
					httpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
