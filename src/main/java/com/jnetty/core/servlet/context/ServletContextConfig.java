package com.jnetty.core.servlet.context;

import java.util.Map;

import javax.servlet.ServletContext;

import com.jnetty.core.Config.ServiceConfig;

public interface ServletContextConfig {
	
	public ServletContext newInstance();
	public void setServiceConfig(ServiceConfig serviceConfig);
	public void setContextPath(String contextPath);
	public void setInitParams(Map<String, String> initParams);
}
