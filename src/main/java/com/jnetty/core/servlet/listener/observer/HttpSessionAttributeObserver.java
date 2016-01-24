package com.jnetty.core.servlet.listener.observer;

import com.jnetty.core.servlet.listener.event.EventUtils;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public class HttpSessionAttributeObserver implements IObserver {
    private List<HttpSessionAttributeListener> listeners = new ArrayList<HttpSessionAttributeListener>();

    public void addEventListener(EventListener eventListener) throws IllegalArgumentException {
        if (eventListener instanceof HttpSessionAttributeListener) {
            listeners.add((HttpSessionAttributeListener) eventListener);
        } else {
            throw new IllegalArgumentException("eventListener must be instance of HttpSessionAttributeListener.");
        }
    }

    public void notify(int eventCode, EventObject eventObject) throws IllegalArgumentException {
        if (eventObject instanceof HttpSessionBindingEvent) {
            switch (eventCode) {
                case EventUtils.SESSION_ATTRIBUTE_ADDED :
                    for (HttpSessionAttributeListener listener : listeners) {
                        listener.attributeAdded((HttpSessionBindingEvent) eventObject);
                    }
                    break;
                case EventUtils.SESSION_ATTRIBUTE_REMOVED :
                    for (HttpSessionAttributeListener listener : listeners) {
                        listener.attributeRemoved((HttpSessionBindingEvent) eventObject);
                    }
                    break;
                case EventUtils.SESSION_ATTRIBUTE_REPLACED :
                    for (HttpSessionAttributeListener listener : listeners) {
                        listener.attributeReplaced((HttpSessionBindingEvent) eventObject);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Illegal eventCode.");
            }
        } else {
            throw new IllegalArgumentException("eventObject must be instance of HttpSessionBindingEvent.");
        }
    }

    public String toString() {
        return "[HttpSessionAttributeObserver(" +
                listeners +
                ")]";
    }
}
