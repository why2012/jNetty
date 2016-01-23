# jNetty
<h3>About jNetty</h3>
a simple non-blocking servlet server/container based on netty.

1、bootstrap （start Service and Connector）</br>
2、connector （build Server，create Httpprocessor and process HTTP request）</br>
3、httpprocessor （establish Httprequest，Httpresponse，invoke Container to process Servlet） 
   staticresourceprocessor （process static resource）</br>
4、httprequest，httpresponse （implement HttpServletRequest/Response）</br>
5、container（load servlet，choose a servlet based on request）</br>

Bootstrap </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|- n -Service </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|- n -Connector </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|- 1 -Container </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|- n -Servlet </br>

<h3>How to use: </h3>
   <h5>First, you should create a global configuration like this :</h5>
   <code>
      Config config = new Config();
   </code></br>
   <h5>Then, create a service config, a service is mapping to a webapp:</h5>
   <code>
      ServiceConfig sconfig = new ServiceConfig();
   </code>
   <h5>And configure the service config:</h5>
   <code>sconfig.connectorQueue.add(new Config.ConnectorConfig());</code></br>
   <code>sconfig.JNettyBase = "/Users/webapps-basedir/";</code></br>
<code>sconfig.WebAppName = "transaction";</code></br>
	<h5>Add service config to global config:</h5>
	<code>
	   config.serviceConfig.add(sconfig);
	</code>
	<h5>Finally, create a bootstrap and start it:</h5>
	   <code>Bootstrap bootstrap = new Bootstrap();</code></br>
	   <code>bootstrap.setConfig(config);</code></br>
	   <code>bootstrap.initialize();</code></br>
	   <code>bootstrap.start();</code></br>
<h3>Code:</h3>
		Config config = new Config();
		
		ServiceConfig sconfig = new ServiceConfig();
		//if you don't want to use WAR file, you could, alternatively , add a servlet like this.
		sconfig.servletList.add(new Config.MappingData("name", "com.jnetty.jnetty.servlets.SessionServlet", "/session", 0));
		//add a connector, default port is 8080
		sconfig.connectorQueue.add(new Config.ConnectorConfig());
		sconfig.JNettyBase = "/Users/webapps-basedir/";
		sconfig.WebAppName = "transaction";
		sconfig.staticResourceLoc = "/resources/";
		
		config.serviceConfig.add(sconfig);
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.setConfig(config);
		bootstrap.initialize();
		System.out.println(sconfig);//print config params

		bootstrap.start();
