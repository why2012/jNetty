package com.jnetty.core.processor;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.container.DefaultContainer;
import com.jnetty.core.container.FilterContainer;
import com.jnetty.core.container.PipeLine;
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
		httpResponseFacade.setCommitted();
		httpResponseFacade.setOnCommitted();
	}

	public void initialize() {
		this.pipeLine = new PipeLine();
		this.pipeLine.setConfig(serviceConfig);
		this.pipeLine.initialize();
		this.pipeLine.addContainer(new FilterContainer());
		this.pipeLine.addContainer(new DefaultContainer());
	}

	public ServiceConfig getConfig() {
		return this.serviceConfig;
	}

	public void setConfig(ServiceConfig config) {
		this.serviceConfig = config;
	}

	public void start() {
		pipeLine.start();
	}

	public void stop() {
		pipeLine.stop();
	}
}
