package com.jnetty.core.servlet.listener.event;

import javax.servlet.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public class EventUtils {
    public static final int CONTEXT_INITIALIZED = 1;
    public static final int CONTEXT_DESTROYED = 2;
    public static final int CONTEXT_ATTRIBUTE_ADDED = 3;
    public static final int CONTEXT_ATTRIBUTE_REMOVED = 4;
    public static final int CONTEXT_ATTRIBUTE_REPLACED = 5;
    public static final int REQUEST_INITIALIZED = 6;
    public static final int REQUEST_DESTROYED = 7;
    public static final int REQUEST_ATTRIBUTE_ADDED = 8;
    public static final int REQUEST_ATTRIBUTE_REMOVED = 9;
    public static final int REQUEST_ATTRIBUTE_REPLACED = 10;
    public static final int SESSION_CREATED = 11;
    public static final int SESSION_DESTROYED = 12;
    public static final int SESSION_PASSIVATE = 13;
    public static final int SESSION_ACTIVATE = 14;
    public static final int SESSION_ATTRIBUTE_ADDED = 15;
    public static final int SESSION_ATTRIBUTE_REMOVED = 16;
    public static final int SESSION_ATTRIBUTE_REPLACED = 17;
    public static final int SESSION_VALUE_BOUND = 18;
    public static final int SESSION_VALUE_UNBOUND = 19;

    private ServletContext servletContext = null;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public ServletContextEvent buildServletContextEvent(){
        return new ServletContextEvent(servletContext);
    }

    public ServletContextAttributeEvent buildServletContextAttributeEvent(String name, Object value) {
        return new ServletContextAttributeEvent(servletContext, name, value);
    }

    public ServletRequestEvent buildServletRequestEvent(ServletRequest servletRequest) {
        return new ServletRequestEvent(servletContext, servletRequest);
    }

    public ServletRequestAttributeEvent buildServletRequestAttributeEvent(ServletRequest servletRequest, String name, Object value) {
        return new ServletRequestAttributeEvent(servletContext, servletRequest, name, value);
    }

    public HttpSessionEvent buildHttpSessionEvent(HttpSession source) {
        return new HttpSessionEvent(source);
    }

    public HttpSessionBindingEvent buildHttpSessionBindingEvent(HttpSession source, String name) {
        return new HttpSessionBindingEvent(source, name);
    }

    public HttpSessionBindingEvent buildHttpSessionBindingEvent(HttpSession source, String name, Object value) {
        return new HttpSessionBindingEvent(source, name, value);
    }
}
