# jNetty
<h3>About jNetty</h3>
a simple non-blocking servlet server/container based on netty.

1、bootstrap （启动connector）</br>
2、connector （建立server，创建httpprocessor处理HTTP请求）</br>
3、httpprocessor （生成httprequest，httpresponse，调用container处理servlet） 
   staticresourceprocessor （处理静态资源）</br>
4、httprequest，httpresponse （实现HttpServletRequest/Response）</br>
5、container（加载servlet，根据request选择servlet执行）</br>

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
   <code>sconfig.JNettyBase = "/Users/webapps-basedir/";</code>
<code>sconfig.WebAppName = "transaction";</code>
	<h5>Add service config to global config:</h5>
	<code>
	   config.serviceConfig.add(sconfig);
	</code>
	<h5>Finally, create a bootstrap and start it:</h5>
	   <code>Bootstrap bootstrap = new Bootstrap();</code>
	   <code>bootstrap.setConfig(config);</code>
	   <code>bootstrap.initialize();</code>
	   <code>bootstrap.start();</code>
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
