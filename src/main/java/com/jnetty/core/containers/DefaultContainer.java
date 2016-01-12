package com.jnetty.core.containers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jnetty.core.Config.ServiceConfig;

public class DefaultContainer implements Container {
	private ServiceConfig serviceConfig = null;

	public void invoke(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpServlet servlet = (HttpServlet)serviceConfig.defaultClassLoader.loadClass(serviceConfig.servletMapping.get("/basic")).newInstance();
			servlet.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ServiceConfig getConfig() {
		return this.serviceConfig;
	}

	public void setConfig(ServiceConfig config) {
		this.serviceConfig = config;
	}

}
