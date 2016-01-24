package com.jnetty.jnetty.listeners;

import com.jnetty.util.log.JNettyLogger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by wanghaiyang on 16/1/25.
 */
public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se) {
        JNettyLogger.logI("sessionCreated: " + se.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        JNettyLogger.logI("sessionDestroyed: " + se.getSession());
    }
}
