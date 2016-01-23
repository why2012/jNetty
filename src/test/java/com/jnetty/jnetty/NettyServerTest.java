package com.jnetty.jnetty;

import com.jnetty.core.Bootstrap;
import com.jnetty.core.Config;
import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.util.log.JNettyLogger;

public class NettyServerTest {
	
	public static void main(String[] args) throws Exception {
		new NettyServerTest().testNettyServer();
	}
	
	public void testNettyServer() throws Exception {
		JNettyLogger.debug = false;
		JNettyLogger.printStackTrace = true;
		Config config = new Config();
		ServiceConfig sconfig = new ServiceConfig();
		sconfig.servletList.add(new Config.MappingData("name", "com.jnetty.jnetty.servlets.SessionServlet", "/session", 0));
		sconfig.connectorQueue.add(new Config.ConnectorConfig());
		//sconfig.connectorQueue.add(new Config.ConnectorConfig(8081));
		sconfig.JNettyBase = "/Users/wanghaiyang/Desktop/logs/webapps";
		sconfig.WebAppName = "transaction";
		sconfig.staticResourceLoc = "/resources/";
		config.serviceConfig.add(sconfig);
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.setConfig(config);
		bootstrap.initialize();
		System.out.println(sconfig);

		bootstrap.start();
	}
}
