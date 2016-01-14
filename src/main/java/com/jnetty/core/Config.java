package com.jnetty.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Config {
	public List<ServiceConfig> serviceConfig = new ArrayList<ServiceConfig>();
	
	public static class ServiceConfig {
		public String staticResourceUrlPattern = "/resources";
		public String staticResourceLoc = "/resources";
		public String serverName= "localhost";
		public String WebRoot = System.getProperty("user.dir");
		public String WebAppName = "webapp";
		
		//session
		public boolean useSession = false;
		public String sessionId = "jsessionid";
	
		public int so_back_log = 128;
		public boolean so_keep_alive = true;
		
		//SSL
		public boolean useSSL = false;
		
		public Hashtable<String, String> servletMapping = new Hashtable<String, String>();
		public ConcurrentLinkedQueue<ConnectorConfig> connectorQueue = new ConcurrentLinkedQueue<ConnectorConfig>();
		
		public ClassLoader defaultClassLoader = Config.class.getClassLoader();
		public ClassLoader servletClassLoader = Config.class.getClassLoader();
		
		public String className = "com.jnetty.core.service.DefaultNettyService";
		public String staticProcessorName = "com.jnetty.core.processor.StaticResourceProcessor";
		public String servletProcessorName = "com.jnetty.core.processor.HttpServletProcessor";
	}
	
	public static class ConnectorConfig {
		public String ip = "127.0.0.1";
		public int port = 8080;
		public String className = "com.jnetty.core.connector.SimpleConnector";
		public String serverName = "com.jnetty.core.server.SimpleNettyServer";
		
		public ConnectorConfig() {}
		
		public ConnectorConfig(int port) {
			this.port = port;
		}
		
		public ConnectorConfig(String ip, int port) {
			this.ip = ip;
			this.port = port;
		}
	}
}
