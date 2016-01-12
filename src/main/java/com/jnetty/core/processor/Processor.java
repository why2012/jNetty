package com.jnetty.core.processor;

import com.jnetty.core.IConfiguration;
import com.jnetty.core.request.Request;
import com.jnetty.core.response.Response;

public interface Processor extends IConfiguration {
	
	public void initialize();
	public void process(Request request, Response response);
}
