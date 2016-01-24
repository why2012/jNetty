package com.jnetty.core.servlet.listener.observer;

import com.jnetty.core.servlet.listener.event.EventUtils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public class HttpSessionObserver implements IObserver {
    private List<HttpSessionListener> listeners = new ArrayList<HttpSessionListener>();

    public void addEventListener(EventListener eventListener) throws IllegalArgumentException {
        if (eventListener instanceof HttpSessionListener) {
            listeners.add((HttpSessionListener) eventListener);
        } else {
            throw new IllegalArgumentException("eventListener must be instance of HttpSessionListener.");
        }
    }

    public void notify(int eventCode, EventObject eventObject) throws IllegalArgumentException {
        if (eventObject instanceof HttpSessionEvent) {
            switch (eventCode) {
                case EventUtils.SESSION_CREATED :
                    for (HttpSessionListener listener : listeners) {
                        listener.sessionCreated((HttpSessionEvent) eventObject);
                    }
                    break;
                case EventUtils.SESSION_DESTROYED :
                    for (HttpSessionListener listener : listeners) {
                        listener.sessionDestroyed((HttpSessionEvent) eventObject);
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
        return "[HttpSessionObserver(" +
                listeners +
                ")]";
    }
}
