package com.jnetty.jnetty.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
		ServletInputStream sins = req.getInputStream();
		int ch = -1;
		while((ch = sins.read()) > -1) {
			System.out.println(ch);
		}
		
		resp.setContentType("text/html; charset=utf-8"); 
		
		PrintWriter out = resp.getWriter();
		out.write("<head><title>BasicServlet</title></head>");
		out.write("<h3>Every thing is fine.<h3>");
		out.flush();
		out.close();;

		ServletOutputStream sostream = resp.getOutputStream();
		sostream.write("<h3>Every thing works fine.</h3>".getBytes());
	}
}
