package com.jnetty.core.server;

import com.jnetty.core.IConfiguration;


public interface Server extends IConfiguration {
	public void initialize();
	public void start();
	public void stop();
}
