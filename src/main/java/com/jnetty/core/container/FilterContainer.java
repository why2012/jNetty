package com.jnetty.core.container;

import com.jnetty.core.Config;
import com.jnetty.core.servlet.filter.IApplicationFilterChain;
import com.jnetty.util.log.JNettyLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public class FilterContainer implements Container, IFilterContainer {
    private Config.ServiceConfig serviceConfig = null;
    private Container next = null;
    private HttpServletRequest httpServletRequest = null;
    private HttpServletResponse httpServletResponse = null;
    private IApplicationFilterChain applicationFilterChain = null;

    public void invoke(HttpServletRequest request, HttpServletResponse response) {
        this.httpServletRequest = request;
        this.httpServletResponse = response;
        applicationFilterChain.reset();
        try {
            applicationFilterChain.doFilter(request, response);
        } catch (Exception e) {
            JNettyLogger.log(e);
        }
    }

    public void initialize() {
        applicationFilterChain = serviceConfig.applicationFilterChain;
        applicationFilterChain.setFilterContainer(this);
    }

    public void setNext(Container container) {
        next = container;
    }

    public Container getNext() {
        return next;
    }

    public Config.ServiceConfig getConfig() {
        return serviceConfig;
    }

    public void setConfig(Config.ServiceConfig config) {
        serviceConfig = config;
    }

    public void invokeLast() {
        next.invoke(httpServletRequest, httpServletResponse);
    }

    public void start() {

    }

    public void stop() {
        this.applicationFilterChain.destroy();
    }
}
