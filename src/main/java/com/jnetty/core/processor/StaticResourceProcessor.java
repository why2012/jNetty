package com.jnetty.core.processor;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.request.HttpRequest;
import com.jnetty.core.request.Request;
import com.jnetty.core.response.HttpResponse;
import com.jnetty.core.response.Response;

public class StaticResourceProcessor implements Processor {
	private ServiceConfig serviceConfig = null;

	public void process(Request request, Response response) {
		HttpRequest httpRequest = (HttpRequest)request;
		HttpResponse httpResponse = (HttpResponse)response;
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
