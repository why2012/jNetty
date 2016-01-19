package com.jnetty.core.service;

import com.jnetty.core.IConfiguration;
import com.jnetty.core.LifeCycle;
import com.jnetty.core.processor.Processor;

public interface Service extends IConfiguration, LifeCycle {
	
	public void initialize() throws Exception;
	public void start();
	public void stop();
	public Processor getStaticResourceProcessor();
	public Processor getServletProcessor();
}
