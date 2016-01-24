package com.jnetty.jnetty.listeners;

import com.jnetty.util.log.JNettyLogger;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * Created by wanghaiyang on 16/1/25.
 */
public class SessionBindingListener implements HttpSessionBindingListener {

    public void valueBound(HttpSessionBindingEvent event) {
        JNettyLogger.logI("valueBound: " + event.getName() + " = " +event.getValue());
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        JNettyLogger.logI("valueUnbound: " + event.getName() + " = " +event.getValue());
    }
}
