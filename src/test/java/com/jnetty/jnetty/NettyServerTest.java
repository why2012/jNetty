package com.jnetty.jnetty;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.server.SimpleNettyServer;

public class NettyServerTest {
	
	public static void main(String[] args) {
		new NettyServerTest().testNettyServer();
	}
	
	public void testNettyServer() {
		SimpleNettyServer server = new SimpleNettyServer();
		ServiceConfig config = new ServiceConfig();
		config.servletMapping.put("/basic", "com.jnetty.jnetty.servlets.BasicServlet");
		server.setConfig(config);
		server.initialize();
		server.start();
	}
}
