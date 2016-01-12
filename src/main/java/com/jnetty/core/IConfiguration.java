package com.jnetty.core;

import com.jnetty.core.Config.ServiceConfig;

public interface IConfiguration {
	public ServiceConfig getConfig();
	public void setConfig(ServiceConfig config);
}
