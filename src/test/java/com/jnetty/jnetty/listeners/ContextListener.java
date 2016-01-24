package com.jnetty.jnetty.listeners;

import com.jnetty.util.log.JNettyLogger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by wanghaiyang on 16/1/25.
 */
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        JNettyLogger.logI("contextInitialized.");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        JNettyLogger.logI("contextDestroyed.");
    }
}
