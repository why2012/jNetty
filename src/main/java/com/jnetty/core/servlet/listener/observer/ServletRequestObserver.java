package com.jnetty.core.servlet.listener.observer;

import com.jnetty.core.servlet.listener.event.EventUtils;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public class ServletRequestObserver implements IObserver {
    private List<ServletRequestListener> listeners = new ArrayList<ServletRequestListener>();

    public void addEventListener(EventListener eventListener) throws IllegalArgumentException {
        if (eventListener instanceof ServletRequestListener) {
            listeners.add((ServletRequestListener) eventListener);
        } else {
            throw new IllegalArgumentException("eventListener must be instance of ServletRequestListener.");
        }
    }

    public void notify(int eventCode, EventObject eventObject) throws IllegalArgumentException {
        if (eventObject instanceof ServletRequestEvent) {
            switch (eventCode) {
                case EventUtils.REQUEST_INITIALIZED :
                    for (ServletRequestListener listener : listeners) {
                        listener.requestInitialized((ServletRequestEvent) eventObject);
                    }
                    break;
                case EventUtils.REQUEST_DESTROYED :
                    for (ServletRequestListener listener : listeners) {
                        listener.requestDestroyed((ServletRequestEvent) eventObject);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Illegal eventCode.");
            }
        } else {
            throw new IllegalArgumentException("eventObject must be instance of ServletRequestEvent.");
        }
    }

    public String toString() {
        return "[ServletRequestObserver(" +
                listeners +
                ")]";
    }
}
