package com.jnetty.core.servlet.context;

import java.util.Map;

import javax.servlet.ServletContext;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.servlet.session.ISessionManager;

public interface ServletContextConfig {
	
	public ServletContext getInstance();
	public void setServiceConfig(ServiceConfig serviceConfig);
	public void setContextPath(String contextPath);
	public void setInitParams(Map<String, String> initParams);
	public void setSessionManager(ISessionManager sessionManager);
	public ISessionManager getSessionManager();
}
