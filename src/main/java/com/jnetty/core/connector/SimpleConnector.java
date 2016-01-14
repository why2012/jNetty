package com.jnetty.core.connector;

import com.jnetty.core.Config;
import com.jnetty.core.Config.ConnectorConfig;
import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.server.Server;
import com.jnetty.core.service.Service;

public class SimpleConnector implements Connector {
	private ServiceConfig serviceConfig = null;
	private Server server = null;
	private Config.ConnectorConfig connectorConfig = null;
	private Service service = null;
	
	public ServiceConfig getConfig() {
		return this.serviceConfig;
	}

	public void setConfig(ServiceConfig config) {
		this.serviceConfig = config;
	}

	public void initialize() throws Exception {
		this.server = (Server) this.serviceConfig.defaultClassLoader.loadClass(this.connectorConfig.serverName).newInstance();
		this.server.setConfig(serviceConfig);
		this.server.setParent(this);
		this.server.initialize();
	}

	public void bind() {
		this.server.start();
	}
	
	public String getIp() {
		return this.connectorConfig.ip;
	}
	
	public int getPort() {
		return this.connectorConfig.port;
	}

	public void setParent(Service service) {
		this.service = service;
	}

	public Service getParent() {
		return this.service;
	}

	public void setConnectorConfig(ConnectorConfig config) {
		this.connectorConfig = config;
	}
	
	public ConnectorConfig getConnectorConfig() {
		return this.connectorConfig;
	}
}
