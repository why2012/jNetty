package com.jnetty.core.servlet.session;

import com.jnetty.core.LifeCycle;
import com.jnetty.core.servlet.context.ServletContextConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * Created by wanghaiyang on 16/1/20.
 */
public interface ISessionManager extends LifeCycle, Runnable {

    public HttpSession getSession(String sessionid, boolean create);
    public boolean isRequestedSessionIdValid(String sessionId);
    public void setServletContextConfig(ServletContextConfig servletContextConfig);
    public ServletContextConfig getServletContextConfig();
}
