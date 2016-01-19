package com.jnetty.core.servlet.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.servlet.session.ISessionManager;
import com.jnetty.util.collection.EnumerationImplIterator;
import com.jnetty.util.log.JNettyLogger;

public class DefaultServletContext implements ServletContext, ServletContextConfig {
	private ServiceConfig serviceConfig = null;
	private String contextPath = null;
	private Map<String, String> initParams = null;
	private Hashtable<String, Object> attribute = new Hashtable<String, Object>();
	private ISessionManager sessionManager = null;
	
	//instance for each request
	public ServletContext newInstance() {
		DefaultServletContext servletContext = new DefaultServletContext();
		servletContext.setServiceConfig(serviceConfig);
		servletContext.setContextPath(contextPath);
		servletContext.setInitParams(initParams);
		
		return servletContext;
	}
	
	public void setServiceConfig(ServiceConfig serviceConfig) {
		this.serviceConfig = serviceConfig;
	}
	
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	public void setInitParams(Map<String, String> initParams) {
		this.initParams = initParams;
	}

	public void setSessionManager(ISessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public ISessionManager getSessionManager() {
		return this.sessionManager;
	}

	public String getContextPath() {
		return this.contextPath;
	}

	public ServletContext getContext(String uripath) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getMimeType(String file) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Set getResourcePaths(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getResource(String path) throws MalformedURLException {
		return new URL(this.serviceConfig.JNettyBase + "/" + this.serviceConfig.WebAppName + path);
	}

	public InputStream getResourceAsStream(String path) {
		File file = new File(this.serviceConfig.JNettyBase + "/" + this.serviceConfig.WebAppName + path);
		InputStream ins = null;
		try {
			ins = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			JNettyLogger.log(e);
		}
		return ins;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public RequestDispatcher getNamedDispatcher(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Servlet getServlet(String name) throws ServletException {
		Servlet servlet = null;
		try {
			servlet = (Servlet)this.serviceConfig.servletClassLoader.loadClass(name).newInstance();
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
		return servlet;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getServlets() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getServletNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public void log(String msg) {
		JNettyLogger.log(msg);
	}

	public void log(Exception exception, String msg) {
		JNettyLogger.logWithMsg(msg, exception, JNettyLogger.WARN);
	}

	public void log(String message, Throwable throwable) {
		JNettyLogger.logWithMsg(message, throwable, JNettyLogger.WARN);
	}

	public String getRealPath(String path) {
		String uripath = null;
		if (this.serviceConfig.useSSL) {
			uripath = "https://";
		} else {
			uripath = "http://";
		}
		//TODO:how to get connection config from serviceconfig?
		uripath += this.serviceConfig.serverName + ":" + this.serviceConfig + "/" + this.serviceConfig.WebAppName + path;
		
		return uripath;
	}

	public String getServerInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInitParameter(String name) {
		return this.initParams.get(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getInitParameterNames() {
		return new EnumerationImplIterator<String>(this.initParams.keySet().iterator());
	}

	public Object getAttribute(String name) {
		return this.attribute.get(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		return this.attribute.keys();
	}

	public void setAttribute(String name, Object object) {
		this.attribute.put(name, object);
	}

	public void removeAttribute(String name) {
		this.attribute.remove(name);
	}

	public String getServletContextName() {
		return this.serviceConfig.WebAppName;
	}

}
