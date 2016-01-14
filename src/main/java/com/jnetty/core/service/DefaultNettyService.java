package com.jnetty.core.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jnetty.core.Config;
import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.connector.Connector;
import com.jnetty.core.processor.HttpServletProcessor;
import com.jnetty.core.processor.Processor;
import com.jnetty.core.processor.StaticResourceProcessor;

public class DefaultNettyService implements Service {
	private ServiceConfig serviceConfig = null;
	private ExecutorService executorService = null;
	private Connector[] connectors = null;
	
	private StaticResourceProcessor srp = null;
	private HttpServletProcessor hsp = null;

	public ServiceConfig getConfig() {
		return this.serviceConfig;
	}

	public void setConfig(ServiceConfig config) {
		this.serviceConfig = config;
	}

	public void initialize() throws Exception {
		srp = (StaticResourceProcessor) serviceConfig.defaultClassLoader.loadClass(serviceConfig.staticProcessorName).newInstance();
		hsp = (HttpServletProcessor) serviceConfig.defaultClassLoader.loadClass(serviceConfig.servletProcessorName).newInstance();
		srp.setConfig(this.serviceConfig);
		hsp.setConfig(this.serviceConfig);
		srp.initialize();
		hsp.initialize();
		
		int size = this.serviceConfig.connectorQueue.size();
		this.executorService = Executors.newFixedThreadPool(size);
		this.connectors = new Connector[size];
		Config.ConnectorConfig[] cconfig = new Config.ConnectorConfig[size];
		this.serviceConfig.connectorQueue.toArray(cconfig);
		for (int c_i = 0 ; c_i < size ; c_i++) {
			Connector connector = (Connector)this.serviceConfig.defaultClassLoader.loadClass(cconfig[c_i].className).newInstance();
			connector.setConfig(serviceConfig);
			connector.setConnectorConfig(cconfig[c_i]);
			connector.setParent(this);
			connector.initialize();
			this.connectors[c_i] = connector;
		}
	}

	public void start() {
		int size = this.connectors.length;
		for (int c_i = 0 ; c_i < size ; c_i++) {
			final Connector connector = this.connectors[c_i];
			this.executorService.execute(new Runnable() {
				public void run() {
					connector.bind();
				}
			});
		}
		this.executorService.shutdown();
	}

	public Processor getStaticResourceProcessor() {
		return this.srp;
	}

	public Processor getServletProcessor() {
		return this.hsp;
	}

}
