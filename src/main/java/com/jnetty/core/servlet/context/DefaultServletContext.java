package com.jnetty.core.servlet.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.servlet.listener.event.EventUtils;
import com.jnetty.core.servlet.session.ISessionManager;
import com.jnetty.util.collection.EnumerationImplIterator;
import com.jnetty.util.log.JNettyLogger;

public class DefaultServletContext implements ServletContext, ServletContextConfig {
	private ServiceConfig serviceConfig = null;
	private String contextPath = null;
	private Map<String, String> initParams = null;
	private ConcurrentHashMap<String, Object> attribute = new ConcurrentHashMap<String, Object>();
	private ISessionManager sessionManager = null;
	
	//instance for each request
	public ServletContext getInstance() {
		return this;
	}
	
	public void setServiceConfig(ServiceConfig serviceConfig) { this.serviceConfig = serviceConfig; }
	
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

	public ServiceConfig getServiceConfig() { return this.serviceConfig; }

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

	public int getEffectiveMajorVersion() {
		return 0;
	}

	public int getEffectiveMinorVersion() {
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
		return new URL("file" , null, this.serviceConfig.JNettyBase + "/" + this.serviceConfig.WebAppName + path);
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

	public boolean setInitParameter(String s, String s1) {
		return false;
	}

	public Object getAttribute(String name) {
		return this.attribute.get(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		return this.attribute.keys();
	}

	public void setAttribute(String name, Object object) {
		Object oldValue = this.attribute.put(name, object);

		if (oldValue != null) {
			serviceConfig.listenerManager.fireEvent(EventUtils.CONTEXT_ATTRIBUTE_REPLACED,
					serviceConfig.listenerManager.getEventUtils().buildServletContextAttributeEvent(name, oldValue));
		} else {
			serviceConfig.listenerManager.fireEvent(EventUtils.CONTEXT_ATTRIBUTE_ADDED,
					serviceConfig.listenerManager.getEventUtils().buildServletContextAttributeEvent(name, object));
		}
	}

	public void removeAttribute(String name) {
		Object value = this.attribute.remove(name);

		serviceConfig.listenerManager.fireEvent(EventUtils.CONTEXT_ATTRIBUTE_REMOVED,
				serviceConfig.listenerManager.getEventUtils().buildServletContextAttributeEvent(name, value));
	}

	public String getServletContextName() {
		return this.serviceConfig.WebAppName;
	}

	public ServletRegistration.Dynamic addServlet(String s, String s1) {
		return null;
	}

	public ServletRegistration.Dynamic addServlet(String s, Servlet servlet) {
		return null;
	}

	public ServletRegistration.Dynamic addServlet(String s, Class<? extends Servlet> aClass) {
		return null;
	}

	public <T extends Servlet> T createServlet(Class<T> aClass) throws ServletException {
		return null;
	}

	public ServletRegistration getServletRegistration(String s) {
		return null;
	}

	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		return null;
	}

	public FilterRegistration.Dynamic addFilter(String s, String s1) {
		return null;
	}

	public FilterRegistration.Dynamic addFilter(String s, Filter filter) {
		return null;
	}

	public FilterRegistration.Dynamic addFilter(String s, Class<? extends Filter> aClass) {
		return null;
	}

	public <T extends Filter> T createFilter(Class<T> aClass) throws ServletException {
		return null;
	}

	public FilterRegistration getFilterRegistration(String s) {
		return null;
	}

	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		return null;
	}

	public SessionCookieConfig getSessionCookieConfig() {
		return null;
	}

	public void setSessionTrackingModes(Set<SessionTrackingMode> set) {

	}

	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		return null;
	}

	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		return null;
	}

	public void addListener(String s) {

	}

	public <T extends EventListener> void addListener(T t) {

	}

	public void addListener(Class<? extends EventListener> aClass) {

	}

	public <T extends EventListener> T createListener(Class<T> aClass) throws ServletException {
		return null;
	}

	public JspConfigDescriptor getJspConfigDescriptor() {
		return null;
	}

	public ClassLoader getClassLoader() {
		return this.getClassLoader();
	}

	public void declareRoles(String... strings) {

	}

	public String getVirtualServerName() {
		return null;
	}

}
