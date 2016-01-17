package com.jnetty.core.servlet.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.jnetty.core.servlet.context.ServletContextConfig;
import com.jnetty.util.collection.EnumerationImplIterator;

public class DefaultServletConfig implements ServletConfig {
	private Map<String, String> initParams = null;
	private ServletContextConfig servletContextConfig = null;
	private ServletContext servletContext = null;
	private String servletName = null;
	
	public DefaultServletConfig() {
		initParams = new HashMap<String, String>();
	}
	
	public void setInitParams(Map<String, String> initParams) {
		this.initParams = initParams;
	}
	
	public void setServletContextConfig(ServletContextConfig servletContextConfig) {
		this.servletContextConfig = servletContextConfig;
	}
	
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	public String getServletName() {
		return this.servletName;
	}

	public ServletContext getServletContext() {
		if (this.servletContext == null) {
			this.servletContext = this.servletContextConfig.newInstance();
		}
		
		return this.servletContext;
	}

	public String getInitParameter(String name) {
		return this.initParams.get(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getInitParameterNames() {
		return new EnumerationImplIterator<String>(this.initParams.keySet().iterator());
	}

}
