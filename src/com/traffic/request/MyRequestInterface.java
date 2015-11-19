package com.traffic.request;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;

public interface MyRequestInterface {
	public HttpGet BuilderHttpGet() throws URISyntaxException;
}
