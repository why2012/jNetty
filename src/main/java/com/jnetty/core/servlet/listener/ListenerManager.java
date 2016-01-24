package com.jnetty.core.servlet.listener;

import com.jnetty.core.servlet.listener.event.EventUtils;
import com.jnetty.core.servlet.listener.observer.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.EventListener;
import java.util.EventObject;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public class ListenerManager implements IListenerManager {
    private ServletContextObserver servletContextObserver = new ServletContextObserver();
    private ServletContextAttributeObserver servletContextAttributeObserver = new ServletContextAttributeObserver();
    private ServletRequestObserver servletRequestObserver = new ServletRequestObserver();
    private ServletRequestAttributeObserver servletRequestAttributeObserver = new ServletRequestAttributeObserver();
    private HttpSessionObserver httpSessionObserver = new HttpSessionObserver();
    private HttpSessionAttributeObserver httpSessionAttributeObserver = new HttpSessionAttributeObserver();
    //the events of HttpSessionActivationObserver is not supported at this version
    private HttpSessionActivationObserver httpSessionActivationObserver = new HttpSessionActivationObserver();
    private HttpSessionBindingObserver httpSessionBindingObserver = new HttpSessionBindingObserver();

    private EventUtils eventUtils = null;

    public void initEventUtils(ServletContext servletContext) {
        this.eventUtils = new EventUtils();
        this.eventUtils.setServletContext(servletContext);
    }

    public EventUtils getEventUtils() {
        return eventUtils;
    }

    public void addListener(EventListener eventListener) {
        if (eventListener instanceof ServletContextListener) {
            servletContextObserver.addEventListener(eventListener);
            return;
        }
        if (eventListener instanceof ServletContextAttributeListener) {
            servletContextAttributeObserver.addEventListener(eventListener);
            return;
        }
        if (eventListener instanceof ServletRequestListener) {
            servletRequestObserver.addEventListener(eventListener);
            return;
        }
        if (eventListener instanceof ServletRequestAttributeListener) {
            servletRequestAttributeObserver.addEventListener(eventListener);
            return;
        }
        if (eventListener instanceof HttpSessionListener) {
            httpSessionObserver.addEventListener(eventListener);
            return;
        }
        if (eventListener instanceof HttpSessionAttributeListener) {
            httpSessionAttributeObserver.addEventListener(eventListener);
            return;
        }
        if (eventListener instanceof HttpSessionActivationListener) {
            httpSessionActivationObserver.addEventListener(eventListener);
            return;
        }
    }

    public void fireEvent(int eventCode, EventObject eventObject) throws IllegalArgumentException {
        switch (eventCode) {
            case EventUtils.CONTEXT_INITIALIZED :
            case EventUtils.CONTEXT_DESTROYED :
                servletContextObserver.notify(eventCode, eventObject);
                break;
            case EventUtils.CONTEXT_ATTRIBUTE_ADDED :
            case EventUtils.CONTEXT_ATTRIBUTE_REMOVED :
            case EventUtils.CONTEXT_ATTRIBUTE_REPLACED :
                servletContextAttributeObserver.notify(eventCode, eventObject);
                break;
            case EventUtils.REQUEST_INITIALIZED :
            case EventUtils.REQUEST_DESTROYED :
                servletRequestObserver.notify(eventCode, eventObject);
                break;
            case EventUtils.REQUEST_ATTRIBUTE_ADDED :
            case EventUtils.REQUEST_ATTRIBUTE_REMOVED :
            case EventUtils.REQUEST_ATTRIBUTE_REPLACED :
                servletRequestAttributeObserver.notify(eventCode, eventObject);
                break;
            case EventUtils.SESSION_CREATED :
            case EventUtils.SESSION_DESTROYED :
                httpSessionObserver.notify(eventCode, eventObject);
                break;
            case EventUtils.SESSION_ATTRIBUTE_ADDED :
            case EventUtils.SESSION_ATTRIBUTE_REMOVED :
            case EventUtils.SESSION_ATTRIBUTE_REPLACED :
                httpSessionAttributeObserver.notify(eventCode, eventObject);
                break;
            case EventUtils.SESSION_ACTIVATE :
            case EventUtils.SESSION_PASSIVATE :
                httpSessionActivationObserver.notify(eventCode, eventObject);
                break;
            default:
                throw new IllegalArgumentException("Illegal eventCode.");
        }
    }

    public void fireSessionValueEvent(int eventCode, HttpSessionBindingListener listener, HttpSessionBindingEvent eventObject) throws IllegalArgumentException {
        httpSessionBindingObserver.notify(eventCode, listener, eventObject);
    }

    public String toString() {
        return "[ListenerManager(" +
                "\n" + servletContextObserver +
                "\n" + servletContextAttributeObserver +
                "\n" + servletRequestObserver +
                "\n" + servletRequestAttributeObserver +
                "\n" + httpSessionObserver +
                "\n" + httpSessionAttributeObserver +
                "\n" + httpSessionActivationObserver +
                "\n)]";
    }
}
