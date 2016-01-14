package com.jnetty.core.request;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import com.jnetty.core.Config;

public class HttpRequest implements Request {
	
	private FullHttpRequest fullHttpRequest = null;
	private Config.ConnectorConfig connectionConfig = null;
	private Config.ServiceConfig serviceConfig = null;
	private ChannelHandlerContext ctx = null;
	
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

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

}
