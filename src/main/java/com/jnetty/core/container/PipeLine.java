package com.jnetty.core.container;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.LifeCycle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PipeLine implements LifeCycle {
	private Container head = null;
	private Container tail = null;
	private ServiceConfig serviceConfig = null;
	
	public void addContainer(Container container) {
		container.setConfig(serviceConfig);
		container.initialize();
		if (head == null) {
			head = tail = container;
		} else {
			tail.setNext(container);
			tail = container;
		}
	}

	/**
	 *
	 * @return head of the container chain
     */
	public Container getContainer() {
		return head;
	}

	public ServiceConfig getConfig() {
		return this.serviceConfig;
	}

	public void setConfig(ServiceConfig config) {
		this.serviceConfig = config;
	}

	public void invoke(HttpServletRequest request, HttpServletResponse response) {
		if (head == null) {
			return;
		}
		head.invoke(request, response);
	}

	public void initialize() {

	}

	public void start() {
		Container tHead = head;
		while(tHead != null) {
			tHead.start();
			tHead = tHead.getNext();
		}
	}

	public void stop() {
		Container tHead = head;
		while(tHead != null) {
			tHead.stop();
			tHead = tHead.getNext();
		}
	}
}
