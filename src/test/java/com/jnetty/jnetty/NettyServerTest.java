package com.jnetty.jnetty;

import com.jnetty.core.Bootstrap;
import com.jnetty.core.Config;
import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.util.log.JNettyLogger;

import java.io.*;
import java.lang.management.ManagementFactory;

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

		//kill -TERM `cat sconfig.JNettyBase + "/" + sconfig.WebAppName + "/" + sconfig.WebAppName + ".pid"`
		savePidFile(sconfig.JNettyBase + "/" + sconfig.WebAppName + "/" + sconfig.WebAppName + ".pid");

		addShutdownHandler();

		bootstrap.start();
	}

	private void savePidFile(String path) throws IOException {
		File pidFile = new File(path);
		if (!pidFile.exists()) {
			pidFile.createNewFile();
		}
		PrintWriter writer = new PrintWriter(new FileWriter(pidFile));
		String processName = ManagementFactory.getRuntimeMXBean().getName();
		String pid = processName.split("@")[0];
		writer.print(pid);
		writer.close();
	}

	private void addShutdownHandler() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				JNettyLogger.log("Shutdown.");
			}
		});
	}
}
