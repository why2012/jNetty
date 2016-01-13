package com.jnetty.core.request;

import com.jnetty.core.Config;

import io.netty.handler.codec.http.FullHttpRequest;

public class HttpRequest implements Request {
	
	private FullHttpRequest fullHttpRequest = null;
	private Config.ConnectorConfig connectionConfig = null;
	private Config.ServiceConfig serviceConfig = null;
	
	public HttpRequest(FullHttpRequest fullHttpRequest) {
		this.fullHttpRequest = fullHttpRequest;
	}
	
	public FullHttpRequest getFullHttpRequest() {
		return this.fullHttpRequest;
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
