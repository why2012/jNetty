package com.jnetty.util.servlet;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.jnetty.core.Config.ServiceConfig;

public class SimpleMapper {
	private Map<String, String> servletMapping = null;
	private ClassLoader classLoader = null;
	
	public SimpleMapper(ServiceConfig config) {
		this.servletMapping = config.servletMapping;
		this.classLoader = config.servletClassLoader;
	}
	
	public HttpServlet getHttpServlet(HttpServletRequest request) {
		HttpServlet servlet = null;
		String pathInfo = request.getPathInfo();
		Set<String> keySet = this.servletMapping.keySet();
		Iterator<String> ite = keySet.iterator();
		while (ite.hasNext()) {
			String path = ite.next();
			if (!pathInfo.startsWith(path)) continue;
			try {
				servlet = (HttpServlet) this.classLoader.loadClass(this.servletMapping.get(path)).newInstance();
			} catch (Exception e) {
				System.out.println(e);
				continue;
			}
		}
		return servlet;
	}
}
