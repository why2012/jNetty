package com.jnetty.core.response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

import com.jnetty.core.Config;

public class HttpResponse implements Response {
	private FullHttpResponse response = null; 
	private Config.ConnectorConfig connectionConfig = null;
	private Config.ServiceConfig serviceConfig = null;
	private ChannelHandlerContext ctx = null;
	private boolean committed = false;//请求是否被提交
	
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

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public boolean isCommitted() {
		return committed;
	}

	public void setCommitted(boolean committed) {
		this.committed = committed;
	}

}
