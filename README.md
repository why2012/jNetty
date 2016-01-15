# jNetty
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
