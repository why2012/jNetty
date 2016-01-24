package com.jnetty.jnetty.listeners;

import com.jnetty.util.log.JNettyLogger;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

/**
 * Created by wanghaiyang on 16/1/25.
 */
public class RequestAttributeListener implements ServletRequestAttributeListener {

    public void attributeAdded(ServletRequestAttributeEvent srae) {
        JNettyLogger.logI("attributeAdded: " + srae.getName() + " = " + srae.getValue());
    }

    public void attributeRemoved(ServletRequestAttributeEvent srae) {
        JNettyLogger.logI("attributeRemoved: " + srae.getName() + " = " + srae.getValue());
    }

    public void attributeReplaced(ServletRequestAttributeEvent srae) {
        JNettyLogger.logI("attributeReplaced: " + srae.getName() + " = " + srae.getValue());
    }
}
