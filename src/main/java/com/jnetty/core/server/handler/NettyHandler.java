package com.jnetty.core.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
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
import com.jnetty.util.log.JNettyLogger;

public class NettyHandler extends ChannelHandlerAdapter {
	private Server server = null;
	
	@Override 
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		FullHttpRequest fullHttpRequest = (FullHttpRequest)msg;
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		if (!fullHttpRequest.decoderResult().isSuccess()) {
			response.setStatus(HttpResponseStatus.BAD_REQUEST);
			NettyHelper.flushAndClose(ctx, response);
			return;
		}
		//Error 404
		if (!fullHttpRequest.uri().startsWith("/" + this.server.getConfig().WebAppName)) {
			response.setStatus(HttpResponseStatus.NOT_FOUND);
			NettyHelper.flushAndClose(ctx, response);
			return;
		}
		//Default content-type: text/html; charset=utf-8
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=utf-8");
		//构建request、response
		HttpRequest httpRequest = new HttpRequest(fullHttpRequest);
		HttpResponse httpResponse = new HttpResponse(response);
		httpRequest.setConnectionConfig(this.getServer().getParent().getConnectorConfig());
		httpRequest.setServiceConfig(this.server.getConfig());
		httpRequest.setCtx(ctx);
		httpResponse.setConnectionConfig(this.getServer().getParent().getConnectorConfig());
		httpResponse.setServiceConfig(this.server.getConfig());
		httpResponse.setCtx(ctx);
		//区分静态资源请求与servlet请求
		String pathInfo = NettyHelper.getPathInfo(fullHttpRequest);
		if (pathInfo.startsWith(this.server.getConfig().staticResourceUrlPattern)) {
			//static resource
			StaticResourceProcessor srp = (StaticResourceProcessor) this.server.getParent().getParent().getStaticResourceProcessor();
			srp.process(httpRequest, httpResponse);
		} else {
			//servlet resource
			HttpRequestFacade httpRequestFacade = new HttpRequestFacade(httpRequest);
			HttpResponseFacade httpResponseFacade = new HttpResponseFacade(httpResponse);
			HttpServletProcessor hsp = (HttpServletProcessor) this.server.getParent().getParent().getServletProcessor();
			hsp.process(httpRequestFacade, httpResponseFacade);
			NettyHelper.flushAndClose(ctx, response);
		}
		httpResponse.setCommitted(true);
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
				JNettyLogger.log(e);
			}
		}
		
		public static void flushAndClose(ChannelHandlerContext ctx, Object response) {
			try {
				ctx.write(response).addListener(ChannelFutureListener.CLOSE).sync();
			} catch (InterruptedException e) {
				JNettyLogger.log(e);
			}
		}
		
		public static String getPathInfo(FullHttpRequest fullHttpRequest) {
			String uri = fullHttpRequest.uri();
			int slashIndex = uri.indexOf("/", 1);
			int queIndex = uri.indexOf("?", 0);
			if (slashIndex > queIndex) {
				slashIndex = -1;
			} else if (slashIndex + 1 == queIndex) {
				slashIndex = -1;
			}
			if (slashIndex == -1) {
				return "/";
			} else {
				return uri.substring(slashIndex, (queIndex == -1 ? uri.length() : queIndex));
			}
		}
		
		public static void setResponseContent(FullHttpResponse response, String content) {
			ByteBuf buf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
			response.content().writeBytes(buf);
		}
	}
}
