package com.jnetty.core.processor;

import io.netty.handler.codec.http.FullHttpRequest;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.request.HttpRequest;
import com.jnetty.core.request.Request;
import com.jnetty.core.response.HttpResponse;
import com.jnetty.core.response.Response;
import com.jnetty.util.log.JNettyLogger;

public class StaticResourceProcessor implements Processor {
	private ServiceConfig serviceConfig = null;
	
	public void process(Request request, Response response) {
		FullHttpRequest httpRequest = ((HttpRequest)request).getFullHttpRequest();

		String uri = httpRequest.uri();
		String resourcePath = "";
		int indexSrc = uri.indexOf(this.serviceConfig.staticResourceUrlPattern);
		int indexSlash = uri.indexOf("/", indexSrc + this.serviceConfig.staticResourceUrlPattern.length());
		int queIndex = uri.indexOf("?");
		if (queIndex == -1) {
			if (indexSlash == -1) {
				resourcePath = "/";
			} else {
				resourcePath = uri.substring(indexSlash);
			}
		} else {
			resourcePath = uri.substring(indexSlash, queIndex);
		}
		
		resourcePath = this.serviceConfig.staticResourceLoc + resourcePath;
		try {
			((HttpResponse)response).sendResource(resourcePath);
		} catch (Exception e) {
			JNettyLogger.log(e);
		}
	}

	public void initialize() {

	}

	public ServiceConfig getConfig() {
		return this.serviceConfig;
	}

	public void setConfig(ServiceConfig config) {
		this.serviceConfig = config;
	}

}
