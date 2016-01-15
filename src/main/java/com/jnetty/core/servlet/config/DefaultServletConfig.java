package com.jnetty.core.servlet.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.jnetty.util.collection.EnumerationImplIterator;

public class DefaultServletConfig implements ServletConfig {
	private Map<String, String> initParams = null;
	private ServletContext servletContext = null;
	private String servletName = null;
	
	public DefaultServletConfig() {
		initParams = new HashMap<String, String>();
	}
	
	public void setInitParams(Map<String, String> initParams) {
		this.initParams = initParams;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	public String getServletName() {
		return this.servletName;
	}

	public ServletContext getServletContext() {
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
