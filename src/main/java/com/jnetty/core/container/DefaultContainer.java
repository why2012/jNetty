package com.jnetty.core.container;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.util.servlet.SimpleMapper;

public class DefaultContainer implements Container {
	private ServiceConfig serviceConfig = null;
	private SimpleMapper mapper = null;

	public void invoke(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpServlet servlet = mapper.getHttpServlet(request);
			if (servlet != null) {
				servlet.init(null);
				servlet.service(request, response);
				servlet.destroy();
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
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

	public void initialize() {
		this.mapper = new SimpleMapper(this.serviceConfig);
	}
}
