package com.jnetty.core.server;

import com.jnetty.core.IConfiguration;
import com.jnetty.core.connector.Connector;


public interface Server extends IConfiguration {
	public void initialize();
	public void start();
	public void stop();
	public void setParent(Connector connector);
	public Connector getParent();
}
