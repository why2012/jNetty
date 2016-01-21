package com.jnetty.core.container;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jnetty.core.IConfiguration;
import com.jnetty.core.LifeCycle;

public interface Container extends IConfiguration, ContainerChain, LifeCycle {
	
	public void invoke(HttpServletRequest request, HttpServletResponse response);
	public void initialize();
}
