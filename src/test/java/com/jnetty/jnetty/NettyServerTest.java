package com.jnetty.jnetty;

import com.jnetty.core.Bootstrap;
import com.jnetty.core.Config;
import com.jnetty.core.Config.ServiceConfig;

public class NettyServerTest {
	
	public static void main(String[] args) throws Exception {
		new NettyServerTest().testNettyServer();
	}
	
	public void testNettyServer() throws Exception {
		Config config = new Config();
		ServiceConfig sconfig = new ServiceConfig();
		sconfig.servletMapping.put("/basic", "com.jnetty.jnetty.servlets.BasicServlet");
		sconfig.connectorQueue.add(new Config.ConnectorConfig());
		//sconfig.connectorQueue.add(new Config.ConnectorConfig(8081));
		sconfig.staticResourceLoc = "/Users/wanghaiyang/Desktop/logs/resources";
		config.serviceConfig.add(sconfig);
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.setConfig(config);
		bootstrap.initialize();
		bootstrap.start();
	}
}
