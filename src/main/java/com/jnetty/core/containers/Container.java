package com.jnetty.core.containers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jnetty.core.IConfiguration;

public interface Container extends IConfiguration {
	
	public void invoke(HttpServletRequest request, HttpServletResponse response);
}
