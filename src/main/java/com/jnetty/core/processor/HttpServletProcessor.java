package com.jnetty.core.processor;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.containers.DefaultContainer;
import com.jnetty.core.containers.PipeLine;
import com.jnetty.core.request.HttpRequestFacade;
import com.jnetty.core.request.Request;
import com.jnetty.core.response.HttpResponseFacade;
import com.jnetty.core.response.Response;

public class HttpServletProcessor implements Processor {
	private PipeLine pipeLine = null;
	private ServiceConfig serviceConfig = null;

	public void process(Request request, Response response) {
		HttpRequestFacade httpRequestFacade = (HttpRequestFacade)request;
		HttpResponseFacade httpResponseFacade = (HttpResponseFacade)response;
		this.pipeLine.invoke(httpRequestFacade, httpResponseFacade);
	}

	public void initialize() {
		this.pipeLine = new PipeLine();
		this.pipeLine.setConfig(serviceConfig);
		this.pipeLine.addContainer(new DefaultContainer());
	}

	public ServiceConfig getConfig() {
		return this.serviceConfig;
	}

	public void setConfig(ServiceConfig config) {
		this.serviceConfig = config;
	}

}
