package com.jnetty.jnetty.listeners;

import com.jnetty.util.log.JNettyLogger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Created by wanghaiyang on 16/1/25.
 */
public class RequestListener implements ServletRequestListener {

    public void requestDestroyed(ServletRequestEvent sre) {
        JNettyLogger.logI("requestDestroyed.");
    }

    public void requestInitialized(ServletRequestEvent sre) {
        JNettyLogger.logI("requestInitialized.");
    }
}
