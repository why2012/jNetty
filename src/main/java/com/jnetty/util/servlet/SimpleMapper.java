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
	
	public SimpleMapper(ServiceConfig config) {
		this.servletList = config.servletList;
		this.classLoader = config.servletClassLoader;
	}
	
	public HttpServlet getHttpServlet(HttpServletRequest request) {
		HttpServlet servlet = null;
		String pathInfo = request.getPathInfo();
		for(MappingData mapping : servletList) {
			String path = mapping.urlPattern;
			if (!URLMatch.match(pathInfo, path)) continue;
			try {
				servlet = (HttpServlet) mapping.servlet;
				if (servlet == null) {
					servlet = (HttpServlet) Class.forName(mapping.servletClass, true, this.classLoader).newInstance();
					servlet.init(mapping.servletConfig);
					mapping.servlet = servlet;
				}
				break;
			} catch (Exception e) {
				JNettyLogger.log(e);
				break;
			}
		}
		return servlet;
	}
}
