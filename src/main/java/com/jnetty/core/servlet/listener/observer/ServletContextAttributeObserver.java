package com.jnetty.core.servlet.listener.observer;

import com.jnetty.core.servlet.listener.event.EventUtils;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public class ServletContextAttributeObserver implements IObserver {
    private List<ServletContextAttributeListener> listeners = new ArrayList<ServletContextAttributeListener>();

    public void addEventListener(EventListener eventListener) throws IllegalArgumentException {
        if (eventListener instanceof ServletContextAttributeListener) {
            listeners.add((ServletContextAttributeListener)eventListener);
        } else {
            throw new IllegalArgumentException("eventListener must be instance of ServletContextAttributeListener.");
        }
    }

    public void notify(int eventCode, EventObject eventObject) throws IllegalArgumentException {
        if (eventObject instanceof ServletContextAttributeEvent) {
            switch (eventCode) {
                case EventUtils.CONTEXT_ATTRIBUTE_ADDED :
                    for (ServletContextAttributeListener listener : listeners) {
                        listener.attributeAdded((ServletContextAttributeEvent)eventObject);
                    }
                    break;
                case EventUtils.CONTEXT_ATTRIBUTE_REMOVED :
                    for (ServletContextAttributeListener listener : listeners) {
                        listener.attributeRemoved((ServletContextAttributeEvent)eventObject);
                    }
                    break;
                case EventUtils.CONTEXT_ATTRIBUTE_REPLACED :
                    for (ServletContextAttributeListener listener : listeners) {
                        listener.attributeReplaced((ServletContextAttributeEvent)eventObject);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Illegal eventCode.");
            }
        } else {
            throw new IllegalArgumentException("eventObject must be instance of ServletContextAttributeEvent.");
        }
    }

    public String toString() {
        return "[ServletContextAttributeObserver(" +
                listeners +
                ")]";
    }
}
