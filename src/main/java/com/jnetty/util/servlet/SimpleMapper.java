package com.jnetty.util.servlet;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.jnetty.core.Config.MappingData;
import com.jnetty.core.Config.ServiceConfig;

public class SimpleMapper {
	private List<MappingData> servletMapping = null;
	private ClassLoader classLoader = null;
	
	public SimpleMapper(ServiceConfig config) {
		this.servletMapping = config.servletMapping;
		this.classLoader = config.servletClassLoader;
	}
	
	public HttpServlet getHttpServlet(HttpServletRequest request) {
		HttpServlet servlet = null;
		String pathInfo = request.getPathInfo();
		for(MappingData mapping : servletMapping) {
			String path = mapping.urlPattern;
			if (!pathInfo.startsWith(path)) continue;
			try {
				servlet = (HttpServlet) this.classLoader.loadClass(mapping.servletClass).newInstance();
				break;
			} catch (Exception e) {
				System.out.println(e);
				continue;
			}
		}
		return servlet;
	}
}
