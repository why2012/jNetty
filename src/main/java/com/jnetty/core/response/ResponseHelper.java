package com.jnetty.core.response;

import javax.servlet.http.Cookie;

public class ResponseHelper {
	
	public static String getCookieString(Cookie cookie) {
		StringBuilder cookieStr = new StringBuilder(cookie.getName() + "=" + cookie.getValue() + ";");
		int maxAge = cookie.getMaxAge();
		if (cookie.getValue() == null) {
			maxAge = 0;
		}
		if (maxAge >= 0) {
			cookieStr.append("Max-Age=" + maxAge + ";");
		}
		if (cookie.getPath() != null) {
			cookieStr.append("Path=" + cookie.getPath() + ";");
		}
		if (cookie.getSecure()) {
			cookieStr.append("Secure;");
		}
		if (cookie.getDomain() != null) {
			cookieStr.append("Domain=" + cookie.getDomain() + ";");
		}
		return cookieStr.toString();
	}
}
