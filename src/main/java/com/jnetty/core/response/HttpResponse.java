package com.jnetty.core.response;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.jnetty.core.Config;

public class HttpResponse implements Response {
	private FullHttpResponse response = null; 
	private Config.ConnectorConfig connectionConfig = null;
	private Config.ServiceConfig serviceConfig = null;
	private ChannelHandlerContext ctx = null;
	private boolean committed = false;//请求是否被提交
	private ByteBuf responseBuf = null;
	
	public HttpResponse(FullHttpResponse response) {
		this.response = response;
		this.responseBuf = response.content();
	}
	
	public void sendResource(String filePath) {
		try {
			File file = new File(filePath);
			if (file.isDirectory()) {
				throw new FileNotFoundException("File requested is a directory.");
			}
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			BufferedInputStream bufIns = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[1024];
			int count = -1;
			while((count = bufIns.read(buffer)) > 0) {
				this.response.content().writeBytes(buffer, 0, count);
			}
			bufIns.close();
		} catch (FileNotFoundException e) {
			response.setStatus(HttpResponseStatus.NOT_FOUND);
			System.out.println(e);
		} catch (IOException e) {
			response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
			System.out.println(e);
		} finally {
			this.ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	//still can not work
	public void sendResourceChunked(String filePath) {
		try {
			File file = new File(filePath);
			if (file.isDirectory()) {
				throw new FileNotFoundException("File requested is a directory.");
			}
			RandomAccessFile randomfile = new RandomAccessFile(filePath, "r");
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			response.headers().setLong(HttpHeaderNames.CONTENT_LENGTH, randomfile.length());
			ChunkedFile chunkedFile = new ChunkedFile(randomfile, 0L, randomfile.length(), 8192);
		
			this.ctx.write(response);
			this.ctx.writeAndFlush(chunkedFile);
			this.ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT).addListener(ChannelFutureListener.CLOSE);
		} catch (FileNotFoundException e) {
			response.setStatus(HttpResponseStatus.NOT_FOUND);
			this.ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
			System.out.println(e);
		} catch (IOException e) {
			response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
			this.ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
			System.out.println(e);
		}
	}
	
	public FullHttpResponse getFullHttpResponse() {
		return this.response;
	}

	public Config.ConnectorConfig getConnectionConfig() {
		return connectionConfig;
	}

	public void setConnectionConfig(Config.ConnectorConfig connectionConfig) {
		this.connectionConfig = connectionConfig;
	}

	public Config.ServiceConfig getServiceConfig() {
		return serviceConfig;
	}

	public void setServiceConfig(Config.ServiceConfig serviceConfig) {
		this.serviceConfig = serviceConfig;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public boolean isCommitted() {
		return committed;
	}

	public void setCommitted(boolean committed) {
		this.committed = committed;
	}

	public ByteBuf getResponseBuf() {
		return this.responseBuf;
	}
}
