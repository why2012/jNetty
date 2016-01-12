package com.jnetty.core.processor;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.containers.Container;
import com.jnetty.core.containers.DefaultContainer;
import com.jnetty.core.request.HttpRequestFacade;
import com.jnetty.core.request.Request;
import com.jnetty.core.response.HttpResponseFacade;
import com.jnetty.core.response.Response;

public class HttpServletProcessor implements Processor {
	private Container container = null;
	private ServiceConfig serviceConfig = null;

	public void process(Request request, Response response) {
		HttpRequestFacade httpRequestFacade = (HttpRequestFacade)request;
		HttpResponseFacade httpResponseFacade = (HttpResponseFacade)response;
		this.container.invoke(httpRequestFacade, httpResponseFacade);
	}

	public void initialize() {
		this.container = new DefaultContainer();
		this.container.setConfig(serviceConfig);
	}

	public ServiceConfig getConfig() {
		return this.serviceConfig;
	}

	public void setConfig(ServiceConfig config) {
		this.serviceConfig = config;
	}

}
