package com.jnetty.jnetty;

import com.jnetty.core.Bootstrap;
import com.jnetty.core.Config;
import com.jnetty.core.Config.MappingData;
import com.jnetty.core.Config.ServiceConfig;

public class NettyServerTest {
	
	public static void main(String[] args) throws Exception {
		new NettyServerTest().testNettyServer();
	}
	
	public void testNettyServer() throws Exception {
		Config config = new Config();
		ServiceConfig sconfig = new ServiceConfig();
		sconfig.servletMapping.add(new MappingData("name", "com.jnetty.jnetty.servlets.BasicServlet", "/basic"));
		sconfig.connectorQueue.add(new Config.ConnectorConfig());
		//sconfig.connectorQueue.add(new Config.ConnectorConfig(8081));
		sconfig.JNettyBase = "/Users/wanghaiyang/Desktop/logs/webapps";
		sconfig.WebAppName = "spring-mvc-showcase";
		sconfig.staticResourceLoc = "/resources/";
		config.serviceConfig.add(sconfig);
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.setConfig(config);
		bootstrap.initialize();
		System.out.println(sconfig);
		//bootstrap.start();
	}
}
