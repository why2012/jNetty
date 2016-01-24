package com.jnetty.core.servlet.listener.observer;

import com.jnetty.core.servlet.listener.event.EventUtils;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public class ServletRequestAttributeObserver implements IObserver {
    private List<ServletRequestAttributeListener> listeners = new ArrayList<ServletRequestAttributeListener>();

    public void addEventListener(EventListener eventListener) throws IllegalArgumentException {
        if (eventListener instanceof ServletRequestAttributeListener) {
            listeners.add((ServletRequestAttributeListener) eventListener);
        } else {
            throw new IllegalArgumentException("eventListener must be instance of ServletRequestAttributeListener.");
        }
    }

    public void notify(int eventCode, EventObject eventObject) throws IllegalArgumentException {
        if (eventObject instanceof ServletRequestAttributeEvent) {
            switch (eventCode) {
                case EventUtils.REQUEST_ATTRIBUTE_ADDED :
                    for (ServletRequestAttributeListener listener : listeners) {
                        listener.attributeAdded((ServletRequestAttributeEvent) eventObject);
                    }
                    break;
                case EventUtils.REQUEST_ATTRIBUTE_REMOVED :
                    for (ServletRequestAttributeListener listener : listeners) {
                        listener.attributeRemoved((ServletRequestAttributeEvent) eventObject);
                    }
                    break;
                case EventUtils.REQUEST_ATTRIBUTE_REPLACED :
                    for (ServletRequestAttributeListener listener : listeners) {
                        listener.attributeReplaced((ServletRequestAttributeEvent) eventObject);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Illegal eventCode.");
            }
        } else {
            throw new IllegalArgumentException("eventObject must be instance of ServletRequestAttributeEvent.");
        }
    }

    public String toString() {
        return "[ServletRequestAttributeObserver(" +
                listeners +
                ")]";
    }
}
