package com.jnetty.core.servlet.listener;

import com.jnetty.core.servlet.listener.event.EventUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.EventListener;
import java.util.EventObject;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public interface IListenerManager {
    public void addListener(EventListener eventListener);
    public void fireEvent(int eventCode, EventObject eventObject) throws IllegalArgumentException;
    public void fireSessionValueEvent(int eventCode, HttpSessionBindingListener listener, HttpSessionBindingEvent eventObject) throws IllegalArgumentException;
    public void initEventUtils(ServletContext servletContext);
    public EventUtils getEventUtils();
}
