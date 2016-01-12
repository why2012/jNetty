package com.jnetty.core.server.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import com.jnetty.core.processor.HttpServletProcessor;
import com.jnetty.core.processor.StaticResourceProcessor;
import com.jnetty.core.request.HttpRequest;
import com.jnetty.core.request.HttpRequestFacade;
import com.jnetty.core.response.HttpResponse;
import com.jnetty.core.response.HttpResponseFacade;
import com.jnetty.core.server.Server;

public class NettyHandler extends ChannelHandlerAdapter {
	private Server server = null;
	
	@Override 
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		FullHttpRequest fullHttpRequest = (FullHttpRequest)msg;
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		if (!fullHttpRequest.decoderResult().isSuccess()) {
			response.setStatus(HttpResponseStatus.BAD_REQUEST);
			NettyHelper.flushAndClose(ctx, response);
		}
		//构建request、response
		HttpRequest httpRequest = new HttpRequest(fullHttpRequest);
		HttpResponse httpResponse = new HttpResponse(response);
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
		NettyHelper.flushAndClose(ctx, response);
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
        	FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        	NettyHelper.flushAndClose(ctx, response);
        }
    } 
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public Server getServer() {
		return this.server;
	}
	
	public static class NettyHelper {
		
		public static void flushAndClose(ChannelHandlerContext ctx, FullHttpResponse response) {
			try {
				ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE).sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
