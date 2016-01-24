package com.jnetty.core.servlet.session;

import com.jnetty.core.Config;
import com.jnetty.core.servlet.context.ServletContextConfig;
import com.jnetty.core.servlet.listener.event.EventUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wanghaiyang on 16/1/20.
 */
public class SessionBase implements ISession {

    private String sessionId = null;
    private boolean isNew = false;
    private boolean isValid = false;
    private long createTime = -1;
    private int maxInactiveInterval = 36000;//seconds
    private long lastAccessedTime = -1;
    private ConcurrentHashMap<String, Object> attribute = new ConcurrentHashMap<String, Object>();

    private ServletContext servletContext = null;
    private Config.ServiceConfig serviceConfig = null;

    /* ISession */

    public void setId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setNew(boolean _new) {
        isNew = _new;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setCreationTime(long time) {
        createTime = time;
    }

    public void setLastAccessedTime(long time) {
        lastAccessedTime = time;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.serviceConfig = ((ServletContextConfig)this.servletContext).getServiceConfig();
    }
    /* HttpSession */

    public long getCreationTime() {
        return createTime;
    }

    public String getId() {
        return sessionId;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setMaxInactiveInterval(int interval) {
        maxInactiveInterval = interval;
    }

    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public HttpSessionContext getSessionContext() {
        return null;
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public Object getValue(String name) {
        return attribute.get(name);
    }

    public Enumeration getAttributeNames() {
        return attribute.keys();
    }

    public String[] getValueNames() {
        String[] names = new String[attribute.size()];
        attribute.keySet().toArray(names);
        return names;
    }

    public void setAttribute(String name, Object value) {
        Object oldValue = attribute.put(name, value);

        if (oldValue == null) {
            this.serviceConfig.listenerManager.fireEvent(EventUtils.SESSION_ATTRIBUTE_ADDED,
                    this.serviceConfig.listenerManager.getEventUtils().buildHttpSessionBindingEvent(this, name, value));
        } else {
            this.serviceConfig.listenerManager.fireEvent(EventUtils.SESSION_ATTRIBUTE_REPLACED,
                    this.serviceConfig.listenerManager.getEventUtils().buildHttpSessionBindingEvent(this, name, oldValue));
        }

        if (value instanceof HttpSessionBindingListener) {
            this.serviceConfig.listenerManager.fireSessionValueEvent(EventUtils.SESSION_VALUE_BOUND,
                    (HttpSessionBindingListener)value,
                    this.serviceConfig.listenerManager.getEventUtils().buildHttpSessionBindingEvent(this, name, value));
        }
    }

    public void putValue(String name, Object value) {

        this.setAttribute(name, value);
    }

    public void removeAttribute(String name) {
        Object value = attribute.remove(name);

        this.serviceConfig.listenerManager.fireEvent(EventUtils.SESSION_ATTRIBUTE_REMOVED,
                this.serviceConfig.listenerManager.getEventUtils().buildHttpSessionBindingEvent(this, name, value));

        if (value instanceof HttpSessionBindingListener) {
            this.serviceConfig.listenerManager.fireSessionValueEvent(EventUtils.SESSION_VALUE_UNBOUND,
                    (HttpSessionBindingListener)value,
                    this.serviceConfig.listenerManager.getEventUtils().buildHttpSessionBindingEvent(this, name, value));
        }
    }

    public void removeValue(String name) {

        this.removeAttribute(name);
    }

    public void invalidate() {
        setValid(false);
    }

    public boolean isNew() {
        return isNew;
    }
}
