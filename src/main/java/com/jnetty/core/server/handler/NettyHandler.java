package com.jnetty.core.server.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import com.jnetty.core.processor.HttpServletProcessor;
import com.jnetty.core.processor.StaticResourceProcessor;
import com.jnetty.core.request.HttpRequest;
import com.jnetty.core.request.HttpRequestFacade;
import com.jnetty.core.response.HttpResponse;
import com.jnetty.core.response.HttpResponseFacade;
import com.jnetty.core.server.Server;

@Sharable
public class NettyHandler extends ChannelHandlerAdapter {
	private NettyHelper nettyHelper = new NettyHelper();
	private Server server = null;
	
	@Override 
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		FullHttpRequest fullHttpRequest = (FullHttpRequest)msg;
		nettyHelper.setChannelHandlerContext(ctx);
		if (!fullHttpRequest.decoderResult().isSuccess()) {
			nettyHelper.sendError(HttpResponseStatus.BAD_REQUEST, "Server Can not decode http request.");
		}
		//构建request、response
		HttpRequest httpRequest = new HttpRequest(fullHttpRequest);
		HttpResponse httpResponse = new HttpResponse(nettyHelper);
		//区分静态资源请求与servlet请求
		String uri = fullHttpRequest.uri();
		if (uri.contains(this.server.getConfig().staticResourceUrlPattern)) {
			StaticResourceProcessor srp = new StaticResourceProcessor();
			srp.setConfig(this.server.getConfig());
			srp.initialize();
			srp.process(httpRequest, httpResponse);
		} else {
			HttpRequestFacade httpRequestFacade = new HttpRequestFacade(httpRequest);
			HttpResponseFacade httpResponseFacade = new HttpResponseFacade(httpResponse);
			HttpServletProcessor hsp = new HttpServletProcessor();
			hsp.setConfig(this.server.getConfig());
			hsp.initialize();
			hsp.process(httpRequestFacade, httpResponseFacade);
		}
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
        	nettyHelper.setChannelHandlerContext(ctx);
        	nettyHelper.sendError(HttpResponseStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
        }
    } 
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public Server getServer() {
		return this.server;
	}
	
	public static class NettyHelper {
		
		private ChannelHandlerContext ctx = null;
		
		public void setChannelHandlerContext(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}
		
		public ChannelHandlerContext getChannelHandlerContext() {
			return this.ctx;
		}
		
		public void sendError(HttpResponseStatus status, String msg) {
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, 
					Unpooled.copiedBuffer((msg + "\r\n"), CharsetUtil.UTF_8));
			response.headers().set("content-type", "text/plain;charset=UTF-8");
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		}
		
		public void sendRedirect(String url) {
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			response.headers().set(HttpHeaderNames.LOCATION, url);
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);	
		}
	}
}
