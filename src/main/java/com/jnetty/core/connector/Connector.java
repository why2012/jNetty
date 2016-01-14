package com.jnetty.core.connector;

import com.jnetty.core.Config;
import com.jnetty.core.IConfiguration;
import com.jnetty.core.Config.ConnectorConfig;
import com.jnetty.core.service.Service;

public interface Connector extends IConfiguration {
	
	public void setConnectorConfig(Config.ConnectorConfig config);
	public ConnectorConfig getConnectorConfig();
	public String getIp();
	public int getPort();
	public void setParent(Service service);
	public Service getParent();
	public void initialize() throws Exception;
	public void bind();
}
