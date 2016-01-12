package com.jnetty.core.request;

import io.netty.handler.codec.http.FullHttpRequest;

public class HttpRequest implements Request {
	
	private FullHttpRequest fullHttpRequest = null;
	
	public HttpRequest(FullHttpRequest fullHttpRequest) {
		this.fullHttpRequest = fullHttpRequest;
	}
	
	public FullHttpRequest getFullHttpRequest() {
		return this.fullHttpRequest;
	}

}
