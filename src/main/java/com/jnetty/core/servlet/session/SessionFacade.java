package com.jnetty.core.servlet.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

/**
 * Created by wanghaiyang on 16/1/20.
 */
public class SessionFacade implements HttpSession {
    private ISession sessionBase = null;

    public SessionFacade(ISession sessionBase) {

        this.sessionBase = sessionBase;
    }

    public long getCreationTime() {

        return sessionBase.getCreationTime();
    }

    public String getId() {

        return sessionBase.getId();
    }

    public long getLastAccessedTime() {

        return sessionBase.getLastAccessedTime();
    }

    public ServletContext getServletContext() {

        return sessionBase.getServletContext();
    }

    public void setMaxInactiveInterval(int interval) {
        sessionBase.setMaxInactiveInterval(interval);
    }

    public int getMaxInactiveInterval() {

        return sessionBase.getMaxInactiveInterval();
    }

    public HttpSessionContext getSessionContext() {

        return sessionBase.getSessionContext();
    }

    public Object getAttribute(String name) {

        return  sessionBase.getAttribute(name);
    }

    public Object getValue(String name) {

        return sessionBase.getValue(name);
    }

    public Enumeration getAttributeNames() {

        return sessionBase.getAttributeNames();
    }

    public String[] getValueNames() {

        return sessionBase.getValueNames();
    }

    public void setAttribute(String name, Object value) {
        sessionBase.setAttribute(name, value);
    }

    public void putValue(String name, Object value) {
        sessionBase.putValue(name, value);
    }

    public void removeAttribute(String name) {
        sessionBase.removeAttribute(name);
    }

    public void removeValue(String name) {
        sessionBase.removeValue(name);
    }

    public void invalidate() {
        sessionBase.invalidate();
    }

    public boolean isNew() {

        return sessionBase.isNew();
    }
}
