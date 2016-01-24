package com.jnetty.core.servlet.listener.observer;

import com.jnetty.core.servlet.listener.event.EventUtils;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public class HttpSessionActivationObserver implements IObserver {
    private List<HttpSessionActivationListener> listeners = new ArrayList<HttpSessionActivationListener>();

    public void addEventListener(EventListener eventListener) throws IllegalArgumentException {
        if (eventListener instanceof HttpSessionActivationListener) {
            listeners.add((HttpSessionActivationListener) eventListener);
        } else {
            throw new IllegalArgumentException("eventListener must be instance of HttpSessionActivationListener.");
        }
    }

    public void notify(int eventCode, EventObject eventObject) throws IllegalArgumentException {
        if (eventObject instanceof HttpSessionEvent) {
            switch (eventCode) {
                case EventUtils.SESSION_ACTIVATE :
                    for (HttpSessionActivationListener listener : listeners) {
                        listener.sessionDidActivate((HttpSessionEvent) eventObject);
                    }
                    break;
                case EventUtils.SESSION_PASSIVATE :
                    for (HttpSessionActivationListener listener : listeners) {
                        listener.sessionWillPassivate((HttpSessionEvent) eventObject);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Illegal eventCode.");
            }
        } else {
            throw new IllegalArgumentException("eventObject must be instance of HttpSessionEvent.");
        }
    }

    public String toString() {
        return "[HttpSessionActivationObserver(" +
                listeners +
                ")]";
    }
}
