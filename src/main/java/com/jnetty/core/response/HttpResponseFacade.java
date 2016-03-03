package com.jnetty.core.response;

import com.jnetty.core.Config;
import com.jnetty.core.request.HttpRequestFacade;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
	private HttpRequestFacade httpRequestFacade = null;
	private Config.ServiceConfig serviceConfig = null;

	public HttpResponseFacade(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
		this.fullHttpResponse = httpResponse.getFullHttpResponse();
		this.serviceConfig = httpResponse.getServiceConfig();
	}

	public void setHttpRequestFacade(HttpRequestFacade httpRequestFacade) {
		this.httpRequestFacade = httpRequestFacade;
	}

	public void setOnCommitted() {
		String sessionId = httpRequestFacade.getRequestedSessionId();
		if (serviceConfig.useSession && sessionId != null && httpRequestFacade.isRequestedSessionIdFromCookie()) {
			this.addCookie(new Cookie(serviceConfig.sessionId, sessionId));
		}
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
		if (!this.serviceConfig.useSession) {
			return url;
		}
		//判断客户端浏览器是否支持 Cookie，如果支持 Cookie，直接返回参数 url
		if (httpRequestFacade.isRequestedSessionIdFromCookie()) {
			return url;
		} else {//不支持 Cookie，在参数 url 中加入 Session ID 信息，返回修改后的 url
			String splitParam = serviceConfig.sessionId + "=" + httpRequestFacade.getRequestedSessionId();
			if (url.indexOf("&") != -1) {
				url = url + "&" +splitParam;
			} else if (url.indexOf("?") != -1) {
				url = url + splitParam;
			} else {
				url = url + "?" + splitParam;
			}
			return url;
		}
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

	public int getStatus() {
		return 0;
	}

	public String getHeader(String s) {
		return (String)this.fullHttpResponse.headers().get(s);
	}

	public Collection<String> getHeaders(String s) {
		return Arrays.asList((String)this.fullHttpResponse.headers().get(s));
	}

	public Collection<String> getHeaderNames() {
		List<String> nameList = new ArrayList<String>();
		Set<CharSequence> nameListCh = this.fullHttpResponse.headers().names();
		for (Iterator<CharSequence> ite = nameListCh.iterator() ; ite.hasNext(); ) {
			nameList.add(ite.next().toString());
		}
		return nameList;
	}

	public String getCharacterEncoding() {
		return this.characterEncoding;
	}

	public String getContentType() {
		return (String) this.fullHttpResponse.headers().get(HttpHeaderNames.CONTENT_TYPE);
	}

	public ServletOutputStream getOutputStream() throws IOException {
		ServletByteBufOutputStream sbytebufous = new ServletByteBufOutputStream(this.httpResponse.getResponseBuf());
		return sbytebufous;
	}

	public PrintWriter getWriter() throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamFacade(this.httpResponse.getResponseBuf()), true);
		return printWriter;
	}

	public void setCharacterEncoding(String charset) {
		this.characterEncoding = charset;
		this.fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=" + charset);
	}

	public void setContentLength(int len) {
		this.fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(len));
	}

	public void setContentLengthLong(long l) {

	}

	public void setContentType(String type) {
		this.fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, type);
	}

	public void setBufferSize(int size) {
		this.httpResponse.getResponseBuf().capacity(size);
	}

	public int getBufferSize() {
		return this.httpResponse.getResponseBuf().capacity();
	}

	public void flushBuffer() throws IOException {
		//this.httpResponse.getCtx().writeAndFlush(this.fullHttpResponse);
		//this.fullHttpResponse.content().clear();
	}

	public void resetBuffer() {
		this.httpResponse.getResponseBuf().clear();
	}

	public boolean isCommitted() {
		return this.httpResponse.isCommitted();
	}

	public void setCommitted() { this.httpResponse.setCommitted(true);}

	public void reset() throws IllegalStateException {
		if (this.httpResponse.isCommitted()) {
			throw new IllegalStateException("Response has been committed");
		}
		this.httpResponse.getResponseBuf().clear();
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
