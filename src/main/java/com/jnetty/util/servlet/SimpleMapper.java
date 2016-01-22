package com.jnetty.util.servlet;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.jnetty.core.Config.MappingData;
import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.util.log.JNettyLogger;
import com.jnetty.util.url.URLMatch;

public class SimpleMapper {
	private List<MappingData> servletList = null;
	private ClassLoader classLoader = null;
	private Hashtable<String, MappingData> servletContext= null;//servletClassName, mappingData
	
	public SimpleMapper(ServiceConfig config) {
		this.servletList = config.servletList;
		this.classLoader = config.servletClassLoader;
		this.servletContext = new Hashtable<String, MappingData>();
	}
	
	public HttpServlet getHttpServlet(HttpServletRequest request) {
		HttpServlet servlet = null;
		String pathInfo = request.getPathInfo();
		for(MappingData mapping : servletList) {
			String path = mapping.urlPattern;
			if (!URLMatch.match(pathInfo, path)) continue;
			try {
				this.servletContext.put(mapping.servletClass, mapping);
				//servlet = (HttpServlet) this.classLoader.loadClass(mapping.servletClass).newInstance();
				servlet = (HttpServlet)Class.forName(mapping.servletClass, true, this.classLoader).newInstance();
				break;
			} catch (Exception e) {
				JNettyLogger.log(e);
				break;
			}
		}
		return servlet;
	}
	
	public ServletConfig getServletConfig(String servletClassName) {
		return this.servletContext.get(servletClassName).servletConfig;
	}
}
