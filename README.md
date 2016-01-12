# jNetty
a simple servlet container based on netty.

1、bootstrap （启动connector）
2、connector （建立server，创建httpprocessor处理HTTP请求）
3、httpprocessor （生成httprequest，httpresponse，调用container处理servlet） 
  staticresourceprocessor （处理静态资源）
4、httprequest，httpresponse （实现HttpServletRequest/Response）
5、container（加载servlet，根据request选择servlet执行）

Bootstrap
    |- n -Service
   	    |- n -Connector
   	    |- 1 -Container
	    	     |- n -Servlet
