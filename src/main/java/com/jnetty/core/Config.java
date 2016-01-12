package com.jnetty.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Config {
	public List<ServiceConfig> serviceConfig = new ArrayList<ServiceConfig>();
	
	public static class ServiceConfig {
		public String staticResourceUrlPattern = "/resources";
		public String WebRoot = System.getProperty("user.dir");
	
		public int so_back_log = 128;
		public boolean so_keep_alive = true;
		
		public Hashtable<String, String> servletMapping = new Hashtable<String, String>();
		
		public ClassLoader defaultClassLoader = Config.class.getClassLoader();
	}
}
