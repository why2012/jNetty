package com.jnetty.core.response;

import io.netty.handler.codec.http.FullHttpResponse;

public class HttpResponse implements Response {
	private FullHttpResponse response = null; 
	
	public HttpResponse(FullHttpResponse response) {
		this.response = response;
	}
	
	public FullHttpResponse getFullHttpResponse() {
		return this.response;
	}

}
