package com.jnetty.core;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.service.Service;

/**
 * 
 * @author wanghaiyang
 * -n-> has n
 * +++> invoke
 * 
 * Bootstrap -n-> Service -n-> Connector -1-> Server ++++
 * 					|			   						+
 * 					|-1-> StaticResourceProcessor <<+++++
 * 					|									+
 * 					|									+
 * 					|-1-> ServletProcessor <<++++++++++++
 * 								|-1-> Pipeline 
 * 										  |-n-> Container 
 * 										  |-1-> SimpleContainer -n-> Servlet
 */
public class Bootstrap implements LifeCycle {
	
	private Service[] services = null;
	private Config config = null;
	
	public void setConfig(Config config) {
		this.config = config;
	}
	
	public void initialize() throws Exception {
		int size = this.config.serviceConfig.size();
		this.services = new Service[size];
		for (int s_i = 0 ; s_i < size ; s_i++) {
			ServiceConfig serviceConfig = this.config.serviceConfig.get(s_i);
			Service service = (Service)serviceConfig.defaultClassLoader.loadClass(serviceConfig.serviceName).newInstance();
			service.setConfig(serviceConfig);
			service.initialize();
			this.services[s_i] = service;
		}
	}
	
	public void start() {
		int size = this.services.length;
		for (int s_i = 0 ; s_i < size ; s_i++) { 
			this.services[s_i].start();
		}
	}
	
	public void stop() {
		int size = this.services.length;
		for (int s_i = 0 ; s_i < size ; s_i++) { 
			this.services[s_i].stop();
		}
	}
}
