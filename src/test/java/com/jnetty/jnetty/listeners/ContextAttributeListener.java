package com.jnetty.jnetty.listeners;

import com.jnetty.util.log.JNettyLogger;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 * Created by wanghaiyang on 16/1/25.
 */
public class ContextAttributeListener implements ServletContextAttributeListener {

    public void attributeAdded(ServletContextAttributeEvent scab) {
        JNettyLogger.logI("attributeAdded: " + scab.getName() + " = " + scab.getValue());
    }

    public void attributeRemoved(ServletContextAttributeEvent scab) {
        JNettyLogger.logI("attributeRemoved: " + scab.getName() + " = " + scab.getValue());
    }

    public void attributeReplaced(ServletContextAttributeEvent scab) {
        JNettyLogger.logI("attributeReplaced: " + scab.getName() + " = " + scab.getValue());
    }
}
