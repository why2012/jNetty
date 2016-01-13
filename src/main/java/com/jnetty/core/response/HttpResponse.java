package com.jnetty.core.response;

import com.jnetty.core.Config;

import io.netty.handler.codec.http.FullHttpResponse;

public class HttpResponse implements Response {
	private FullHttpResponse response = null; 
	private Config.ConnectorConfig connectionConfig = null;
	private Config.ServiceConfig serviceConfig = null;
	
	public HttpResponse(FullHttpResponse response) {
		this.response = response;
	}
	
	public FullHttpResponse getFullHttpResponse() {
		return this.response;
	}

	public Config.ConnectorConfig getConnectionConfig() {
		return connectionConfig;
	}

	public void setConnectionConfig(Config.ConnectorConfig connectionConfig) {
		this.connectionConfig = connectionConfig;
	}

	public Config.ServiceConfig getServiceConfig() {
		return serviceConfig;
	}

	public void setServiceConfig(Config.ServiceConfig serviceConfig) {
		this.serviceConfig = serviceConfig;
	}

}
