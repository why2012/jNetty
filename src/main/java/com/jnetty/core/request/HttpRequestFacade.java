package com.jnetty.core.request;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.MixedAttribute;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jnetty.util.collection.EnumerationImplIterator;
import com.jnetty.util.collection.EnumerationImplList;
import com.jnetty.util.io.ServletByteArrayInputStream;

public class HttpRequestFacade implements Request, HttpServletRequest {
	
	private HttpRequest httpRequest = null;
	private FullHttpRequest fullHttpRequest = null;
	
	//reuqest scope
	private Map<String, Object> requestScopeMap = null;
	
	//servlet param
	private Cookie[] cookies = null;
	private List<String> headerNames = null;
	private Map<String, List<String>> headersMap = null;
	private String queryString = null;
	private StringBuffer requestUrl = null;
	private Map<String, String> parameterMap = null;
	
	public HttpRequestFacade(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
		this.fullHttpRequest = httpRequest.getFullHttpRequest();
		this.headersMap = new HashMap<String, List<String>>();
		this.requestScopeMap = new HashMap<String, Object>();
	}

	public String getAuthType() {
		return null;
	}

	public Cookie[] getCookies() {
		if (cookies == null) {
			String cookieString = (String) this.fullHttpRequest.headers().get(HttpHeaderNames.COOKIE);
			if(cookieString == null) {
				return cookies;
			}
			String[] cookieStringArray = cookieString.split(";");
			ArrayList<Cookie> cookieL = new ArrayList<Cookie>();
			for (String cookie : cookieStringArray) {
				String[] keyAndValue = cookie.split("=", 2);
				if (keyAndValue.length >= 2) {
					cookieL.add(new Cookie(keyAndValue[0].trim(), keyAndValue[1]));
				}
			}
			cookies = new Cookie[cookieL.size()];
			cookieL.toArray(cookies);
		}
		return cookies;
	}

	public long getDateHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getHeader(String name) {
		return (String) this.fullHttpRequest.headers().get(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getHeaders(String name) {
		List<String> list = this.headersMap.get(name);
		if (list == null) {
			list = new ArrayList<String>();
			List<CharSequence> hlist = this.fullHttpRequest.headers().getAll(name);
			for (Object h : hlist) {
				list.add(h.toString());
			}
			this.headersMap.put(name, list);
		}
		EnumerationImplList<String> enumL = new EnumerationImplList<String>(list);
		return enumL;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getHeaderNames() {
		if (headerNames == null) {
			headerNames = new ArrayList<String>();
			List<CharSequence> hlist = this.fullHttpRequest.headers().namesList();
			for (Object h : hlist) {
					headerNames.add(h.toString());
			}
		}
		EnumerationImplList<String> enumL = new EnumerationImplList<String>(headerNames);
		return enumL;
	}

	public int getIntHeader(String name) {
		return this.fullHttpRequest.headers().getInt(name);
	}

	public String getMethod() {
		return this.fullHttpRequest.method().name().toString();
	}

	public String getPathInfo() {
		String uri = this.fullHttpRequest.uri();
		int slashIndex = uri.indexOf("/", 1);
		int queIndex = uri.indexOf("?", 0);
		if (slashIndex > queIndex && queIndex != -1) {
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

	public String getPathTranslated() {
		return null;
	}

	public String getContextPath() {
		String uri = this.fullHttpRequest.uri();
		int slashIndex = uri.indexOf("/", 1);
		int queIndex = uri.indexOf("?", 0);
		if (slashIndex > queIndex && queIndex != -1) {
			slashIndex = -1;
		}
		return uri.substring(0, slashIndex == -1 ? (queIndex == -1 ? uri.length() : queIndex) : slashIndex);
	}

	public String getQueryString() {
		if (queryString == null) {
			String[] uriArray = this.fullHttpRequest.uri().split("\\?", 2);
			if (uriArray.length >= 2) {
				queryString = uriArray[1];
			}
		}
		return queryString;
	}

	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isUserInRole(String role) {
		// TODO Auto-generated method stub
		return false;
	}

	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRequestedSessionId() {
		return this.getParameter(this.httpRequest.getServiceConfig().sessionId);
	}

	public String getRequestURI() {
		return this.fullHttpRequest.uri();
	}

	public StringBuffer getRequestURL() {
		if (requestUrl == null) {
			requestUrl = new StringBuffer((this.httpRequest.getServiceConfig().useSSL ? "https://" : "http://") + 
					this.fullHttpRequest.headers().get(HttpHeaderNames.HOST) +
					this.fullHttpRequest.uri());
		}
		return requestUrl;
	}

	public String getServletPath() {
		return this.getPathInfo();
	}

	public HttpSession getSession(boolean create) {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdFromUrl() {
		return this.isRequestedSessionIdFromURL();
	}

	public Object getAttribute(String name) {
		return this.requestScopeMap.get(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCharacterEncoding(String env)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
	}

	public int getContentLength() {
		return this.fullHttpRequest.headers().getInt(HttpHeaderNames.CONTENT_LENGTH);
	}

	public String getContentType() {
		return (String) this.fullHttpRequest.headers().get(HttpHeaderNames.CONTENT_TYPE);
	}

	public ServletInputStream getInputStream() throws IOException {
		ByteArrayInputStream bins = new ByteArrayInputStream(this.fullHttpRequest.content().array());
		return new ServletByteArrayInputStream(bins);
	}

	public String getParameter(String name) {
		@SuppressWarnings("unchecked")
		Map<String, String> paramMap = this.getParameterMap();
		return paramMap.get(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getParameterNames() {
		@SuppressWarnings("unchecked")
		Map<String, String> paramMap = this.getParameterMap();
		EnumerationImplIterator<String> enumI = new EnumerationImplIterator<String>(paramMap.keySet().iterator());
		return enumI;
	}

	public String[] getParameterValues(String name) {
		@SuppressWarnings("unchecked")
		Map<String, String> paramMap = this.getParameterMap();
		Collection<String> co = paramMap.values();
		String[] values = new String[co.size()];
		int index = 0;
		for (String str : co) {
			values[index] = str;
			index++;
		}
		return values;
	}

	@SuppressWarnings("rawtypes")
	public Map getParameterMap() {
		if (this.parameterMap == null) {
			this.parameterMap = new HashMap<String, String>();
			String queryString = this.getQueryString();
			if (queryString != null) {
				String[] queryArray = queryString.split("&");
				for (String queryToken : queryArray) {
					String[] keyAndValue = queryToken.split("=", 2);
					if (keyAndValue.length >= 2) {
						this.parameterMap.put(keyAndValue[0], keyAndValue[1]);
					}
				}
			}
			//parse post data
			if (this.fullHttpRequest.method().equals(HttpMethod.POST)) {
				HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
				HttpPostRequestDecoder postRequestDecoder = new HttpPostRequestDecoder(factory, this.fullHttpRequest);
				while(postRequestDecoder.hasNext()) {
					MixedAttribute httpData = (MixedAttribute)postRequestDecoder.next();
					try {
						this.parameterMap.put(httpData.getName(), httpData.getValue());
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						httpData.release();
					}
				}
			}
		}
		return this.parameterMap;
	}

	public String getProtocol() {
		return this.fullHttpRequest.protocolVersion().protocolName().toString();
	}

	public String getScheme() {
		return this.httpRequest.getServiceConfig().useSSL ? "https" : "http";
	}

	public String getServerName() {
		return this.httpRequest.getServiceConfig().serverName;
	}

	public int getServerPort() {
		return this.httpRequest.getConnectionConfig().port;
	}

	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAttribute(String name, Object o) {
		this.requestScopeMap.put(name, o);
	}

	public void removeAttribute(String name) {
		this.requestScopeMap.remove(name);
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("finally")
	public String getRealPath(String path) {
		String realPath = null;
		try {
			realPath = new File(this.httpRequest.getServiceConfig().JNettyBase + path).getCanonicalPath();
		} catch (IOException e) {

		} finally {
			return realPath;
		}
	}

	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getLocalName() {
		return this.httpRequest.getServiceConfig().serverName;
	}

	public String getLocalAddr() {
		return this.httpRequest.getConnectionConfig().ip;
	}

	public int getLocalPort() {
		return this.httpRequest.getConnectionConfig().port;
	}

}
