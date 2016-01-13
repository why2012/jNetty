package com.jnetty.jnetty.servlets;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) {
		Cookie[] cookies = req.getCookies();
		req.getPathInfo();
		System.out.println(req.getMethod() + " ; " + req.getRequestURI() + " ; " +req.getQueryString());
		System.out.println("URL: " + req.getRequestURL());
		System.out.println("ContextPath: " + req.getContextPath());
		System.out.println("PathInfo: " + req.getPathInfo());
		System.out.println("Protocol: " + req.getProtocol());
		System.out.println("RealPath(/): " + req.getRealPath("/"));
		Enumeration paramNames = req.getParameterNames();
		while(paramNames.hasMoreElements()) {
			String key = (String) paramNames.nextElement();
			System.out.println(key + "=" + req.getParameter(key));
		}
		Enumeration enumr = req.getHeaderNames();
		while(enumr.hasMoreElements()) {
			String name = (String) enumr.nextElement();
			System.out.println(name + " : " + req.getHeader(name));
		}
		if (cookies == null) {
			resp.addCookie(new Cookie("username", "why"));
		} else {
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			}
		}
		Cookie cookie = new Cookie("pwd", "123");
		cookie.setMaxAge(10);
		resp.addCookie(cookie);
	}
}
