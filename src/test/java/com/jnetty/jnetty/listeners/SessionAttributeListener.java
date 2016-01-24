package com.jnetty.jnetty.listeners;

import com.jnetty.util.log.JNettyLogger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Created by wanghaiyang on 16/1/25.
 */
public class SessionAttributeListener implements HttpSessionAttributeListener {
    public void attributeAdded(HttpSessionBindingEvent se) {
        JNettyLogger.logI("attributeAdded: " + se.getName() + " = " + se.getValue());
    }

    public void attributeRemoved(HttpSessionBindingEvent se) {
        JNettyLogger.logI("attributeRemoved: " + se.getName() + " = " + se.getValue());
    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
        JNettyLogger.logI("attributeReplaced: " + se.getName() + " = " + se.getValue());
    }
}
