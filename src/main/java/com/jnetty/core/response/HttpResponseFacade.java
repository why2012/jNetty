package com.jnetty.core.response;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.jnetty.core.server.handler.NettyHandler.NettyHelper;
import com.jnetty.util.io.OutputStreamFacade;
import com.jnetty.util.io.ServletByteBufOutputStream;


public class HttpResponseFacade implements Response, HttpServletResponse {
	
	private HttpResponse httpResponse = null;
	private FullHttpResponse fullHttpResponse = null;
	private String characterEncoding = null;
	
	public HttpResponseFacade(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
		this.fullHttpResponse = httpResponse.getFullHttpResponse();
	}

	public void addCookie(Cookie cookie) {
		String cookieString = ResponseHelper.getCookieString(cookie);
		String cookiesString = (String)this.fullHttpResponse.headers().get(HttpHeaderNames.COOKIE);
		if (cookiesString != null) {
			cookiesString += cookieString;
		} else {
			cookiesString = cookieString;
		}
		this.fullHttpResponse.headers().set(HttpHeaderNames.SET_COOKIE, cookiesString);
	}

	public boolean containsHeader(String name) {
		return this.fullHttpResponse.headers().contains(name);
	}

	public String encodeURL(String url) {
		//是否启用session
		if (!this.httpResponse.getServiceConfig().useSession) {
			return url;
		}
		//判断客户端浏览器是否支持 Cookie，如果支持 Cookie，直接返回参数 url
		
		//不支持 Cookie，在参数 url 中加入 Session ID 信息，返回修改后的 url
		return null;
	}

	public String encodeRedirectURL(String url) {
		return this.encodeURL(url);
	}

	public String encodeUrl(String url) {
		return this.encodeURL(url);
	}

	public String encodeRedirectUrl(String url) {
		return this.encodeURL(url);
	}

	public void sendError(int sc, String msg) throws IOException {
		HttpResponseStatus status = HttpResponseStatus.valueOf(sc);
		this.fullHttpResponse.setStatus(status);
		NettyHelper.setResponseContent(this.fullHttpResponse, msg);
	}

	public void sendError(int sc) throws IOException {
		HttpResponseStatus status = HttpResponseStatus.valueOf(sc);
		this.fullHttpResponse.setStatus(status);
	}

	public void sendRedirect(String location) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void setDateHeader(String name, long date) {
		//暂不做long->string的转换
		this.fullHttpResponse.headers().set(name, String.valueOf(date));
	}

	public void addDateHeader(String name, long date) {
		//暂不做long->string的转换
		this.fullHttpResponse.headers().add(name, String.valueOf(date));
	}

	public void setHeader(String name, String value) {
		this.fullHttpResponse.headers().set(name, value);
	}

	public void addHeader(String name, String value) {
		this.fullHttpResponse.headers().add(name, value);
	}

	public void setIntHeader(String name, int value) {
		this.fullHttpResponse.headers().setInt(name, value);
	}

	public void addIntHeader(String name, int value) {
		this.fullHttpResponse.headers().addInt(name, value);
	}

	public void setStatus(int sc) {
		HttpResponseStatus status = HttpResponseStatus.valueOf(sc);
		this.fullHttpResponse.setStatus(status);
	}

	public void setStatus(int sc, String sm) {
		HttpResponseStatus status = HttpResponseStatus.valueOf(sc);
		this.fullHttpResponse.setStatus(status);
		NettyHelper.setResponseContent(this.fullHttpResponse, sm);
	}

	public String getCharacterEncoding() {
		return this.characterEncoding;
	}

	public String getContentType() {
		return (String) this.fullHttpResponse.headers().get(HttpHeaderNames.CONTENT_TYPE);
	}

	public ServletOutputStream getOutputStream() throws IOException {
		ServletByteBufOutputStream sbytebufous = new ServletByteBufOutputStream(this.fullHttpResponse.content());
		return sbytebufous;
	}

	public PrintWriter getWriter() throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamFacade(this.fullHttpResponse.content()));
		return printWriter;
	}

	public void setCharacterEncoding(String charset) {
		this.characterEncoding = charset;
		this.fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=" + charset);
	}

	public void setContentLength(int len) {
		this.fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(len));
	}

	public void setContentType(String type) {
		this.fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, type);
	}

	public void setBufferSize(int size) {
		this.fullHttpResponse.content().capacity(size);
	}

	public int getBufferSize() {
		return this.fullHttpResponse.content().capacity();
	}

	public void flushBuffer() throws IOException {
		this.httpResponse.getCtx().writeAndFlush(this.fullHttpResponse.content());
		this.fullHttpResponse.content().clear();
	}

	public void resetBuffer() {
		this.fullHttpResponse.content().clear();
	}

	public boolean isCommitted() {
		return this.httpResponse.isCommitted();
	}

	public void reset() throws IllegalStateException {
		if (this.httpResponse.isCommitted()) {
			throw new IllegalStateException("Response has been committed");
		}
		this.fullHttpResponse.content().clear();
		this.fullHttpResponse.headers().clear();
		this.fullHttpResponse.setStatus(HttpResponseStatus.OK);
	}

	public void setLocale(Locale loc) {
		// TODO Auto-generated method stub
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

}
