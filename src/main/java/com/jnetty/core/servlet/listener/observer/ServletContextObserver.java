package com.jnetty.core.servlet.listener.observer;

import com.jnetty.core.servlet.listener.event.EventUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public class ServletContextObserver implements IObserver {
    private List<ServletContextListener> listeners = new ArrayList<ServletContextListener>();

    public void addEventListener(EventListener eventListener) throws IllegalArgumentException {
        if (eventListener instanceof ServletContextListener) {
            listeners.add((ServletContextListener)eventListener);
        } else {
            throw new IllegalArgumentException("eventListener must be instance of ServletContextListener.");
        }
    }

    public void notify(int eventCode, EventObject eventObject) throws IllegalArgumentException {
        if (eventObject instanceof ServletContextEvent) {
            switch (eventCode) {
                case EventUtils.CONTEXT_INITIALIZED :
                    for (ServletContextListener listener : listeners) {
                        listener.contextInitialized((ServletContextEvent)eventObject);
                    }
                    break;
                case EventUtils.CONTEXT_DESTROYED :
                    for (ServletContextListener listener : listeners) {
                        listener.contextDestroyed((ServletContextEvent)eventObject);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Illegal eventCode.");
            }
        } else {
            throw new IllegalArgumentException("eventObject must be instance of ServletContextEvent.");
        }
    }

    public String toString() {
        return "[ServletContextObserver(" +
                listeners +
                ")]";
    }
}
