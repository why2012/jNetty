package com.jnetty.core.containers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jnetty.core.Config.ServiceConfig;

public class PipeLine implements Container {
	private List<Container> containers = new ArrayList<Container>();
	private ServiceConfig serviceConfig = null;
	
	public void addContainer(Container container) {
		container.setConfig(serviceConfig);
		this.containers.add(container);
	}
	
	public Container[] getContainers() {
		Container[] cons = new Container[this.containers.size()];
		this.containers.toArray(cons);
		return cons;
	}

	public ServiceConfig getConfig() {
		return this.serviceConfig;
	}

	public void setConfig(ServiceConfig config) {
		this.serviceConfig = config;
	}

	public void invoke(HttpServletRequest request, HttpServletResponse response) {
		int size = this.containers.size();
		for (int c_i = 0 ; c_i < size ; c_i++) {
			this.containers.get(c_i).invoke(request, response);
		}
	}

}
