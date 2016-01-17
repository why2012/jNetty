package com.jnetty.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletConfig;

import com.jnetty.core.servlet.context.ServletContextConfig;

public class Config {
	public List<ServiceConfig> serviceConfig = new ArrayList<ServiceConfig>();
	
	public static class ServiceConfig {
		public String staticResourceUrlPattern = "/resources";
		public String serverName= "localhost";
		public String WebAppName = "webapp";
		
		//Webapp base location
		public String JNettyBase = System.getProperty("user.dir");//webapp base location
		
		//Static resource location, default : / JNettyBase / WebAppName / staticResourceLoc
		public String staticResourceLoc = "/resources";
		
		//Session
		public boolean useSession = false;
		public String sessionId = "jsessionid";
	
		public int so_back_log = 128;
		public boolean so_keep_alive = true;
		
		//SSL
		public boolean useSSL = false;
		
		//url match,see SimpleMapper
		public List<MappingData> servletList = new ArrayList<MappingData>();
		public ConcurrentLinkedQueue<ConnectorConfig> connectorQueue = new ConcurrentLinkedQueue<ConnectorConfig>();
		
		public ClassLoader defaultClassLoader = Config.class.getClassLoader();
		public ClassLoader servletClassLoader = Config.class.getClassLoader();
		
		public String serviceName = "com.jnetty.core.service.DefaultNettyService";
		public String staticProcessorName = "com.jnetty.core.processor.StaticResourceProcessor";
		public String servletProcessorName = "com.jnetty.core.processor.HttpServletProcessor";
		
		public String toString() {
			String configString = "";
			String format = "staticResourceUrlPattern: %s\n"
					+ "serverName: %s\n"
					+ "WebAppName: %s\n"
					+ "JNettyBase: %s\n"
					+ "staticResourceLoc: %s\n"
					+ "useSession: %s\n"
					+ "sessionId: %s\n"
					+ "so_back_log: %d\n"
					+ "so_keep_alive: %s\n"
					+ "useSSL: %s\n"
					+ "defaultClassLoader: %s\n"
					+ "servletClassLoader: %s\n"
					+ "serviceName: %s\n"
					+ "staticProcessorName: %s\n"
					+ "servletProcessorName: %s\n";
			configString = "[ServiceConfig(\n" + String.format(format, staticResourceUrlPattern, serverName, WebAppName, JNettyBase, staticResourceLoc,
					String.valueOf(useSession), sessionId, so_back_log, String.valueOf(so_keep_alive), String.valueOf(useSSL),
					defaultClassLoader, servletClassLoader, serviceName, staticProcessorName, servletProcessorName);
			for (MappingData data : servletList) {
				configString += data + "\n";
			}
			for (ConnectorConfig data : connectorQueue) {
				configString += data + "\n";
			}
			configString += ")]";
			return configString;
		}
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
		
		public String toString() {
			return String.format("[ConnectorConfig(ip: %s; port: %d; className: %s; serverName: %s)]", ip, port, className, serverName);
		}
	}
	
	public static class MappingData {
		public String urlPattern = "";
		public String servletClass = "";
		public String servletName = "";
		public ServletConfig servletConfig = null;
		public ServletContextConfig servletContextConfig = null;
		
		public MappingData(String servletName, String servletClass) {
			this.servletClass = servletClass;
			this.servletName = servletName;
		}
		
		public MappingData(String servletName, String servletClass, String urlPattern) {
			this.urlPattern = urlPattern;
			this.servletClass = servletClass;
			this.servletName = servletName;
		}
		
		public String toString() {
			return String.format("[MappingData(servletName: %s; servletClass: %s; urlPattern: %s)]", servletName, servletClass, urlPattern);
		}
	}
}
