package com.traffic.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;

public abstract class BaiduRequest implements BaiduRequestInterface{
	private String scheme;
	private String host;
	private String path;
	private Header[] headers;
	private List<NameValuePair> parameters;
	private CookieStore cookieStore;
	private String headerStr;
	private int socketTimeout = 3000;
	private int connectTimeout = 3000;
	public String getHeaderStr() {
		return headerStr;
	}
	public void setHeaderStr(String headerStr) {
		this.headerStr = headerStr;
	}
	public CookieStore getCookieStore() {
		return cookieStore;
	}
	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}
	public Header[] getHeaders() {
		
		return headers;
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public int getSocketTimeout() {
		return socketTimeout;
	}
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	
	public void setHeaders() {
		String[] headerStrArr = headerStr.split("\r\n");
		headers = new Header[headerStrArr.length];
		for (int i = 0; i < headerStrArr.length; i++) {
			headers[i] = new BasicHeader(headerStrArr[i].split(" ",2)[0], headerStrArr[i].split(" ",2)[1]);
		}
	}
	public List<NameValuePair> getParameters() {
		return parameters;
	}
	public void setParameters(List<NameValuePair> parameters) {
		this.parameters = parameters;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * @return uri
	 * @throws URISyntaxException
	 * ªÒ»°URI
	 * 
	 */
	public URI getURI() throws URISyntaxException {
		
		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setScheme(scheme);
		uriBuilder.setHost(host);
		uriBuilder.setPath(path);
		
		if(parameters!=null)
			uriBuilder.addParameters(parameters);
		
		return uriBuilder.build();
	}
	
	@Override
	public HttpGet BuilderHttpGet() throws URISyntaxException{
		HttpGet httpGet = new HttpGet();
		httpGet.setURI(getURI());
		httpGet.setHeaders(headers);
		//set timeOut
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout)
				.build();
		httpGet.setConfig(requestConfig);
		return httpGet;
	}

}
