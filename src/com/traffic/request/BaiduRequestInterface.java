package com.traffic.request;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;

public interface BaiduRequestInterface {
	public HttpGet BuilderHttpGet() throws URISyntaxException;
}
