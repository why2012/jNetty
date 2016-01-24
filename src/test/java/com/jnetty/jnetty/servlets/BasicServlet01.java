package com.jnetty.jnetty.servlets;

import com.jnetty.util.log.JNettyLogger;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicServlet01 extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		JNettyLogger.logI("INIT BasicServlet.");
	}
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setAttribute("Attr", "123");
		req.setAttribute("Attr", "1234");
		req.removeAttribute("Attr");
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

		ClassLoader classLoader = this.getClass().getClassLoader();

		out.print(classLoader + "</br>");

		String srcPath = req.getParameter("url");
		if(srcPath != null) {
			URL url = classLoader.getResource(srcPath);
			out.print("Path: " +srcPath + "; URL: " + url + "</br>");
			InputStream ins = classLoader.getResourceAsStream(srcPath);
			BufferedReader bufIns = new BufferedReader(new InputStreamReader(ins));
			String line = null;
			while((line = bufIns.readLine()) != null) {
				out.print(line + "<br>");
			}
		}

		out.close();
	}
}
