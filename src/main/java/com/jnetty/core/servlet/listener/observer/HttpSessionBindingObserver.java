package com.jnetty.core.servlet.listener.observer;

import com.jnetty.core.servlet.listener.event.EventUtils;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public class HttpSessionBindingObserver {

    public void notify(int eventCode, HttpSessionBindingListener listener, HttpSessionBindingEvent eventObject) throws IllegalArgumentException {
        switch (eventCode) {
            case EventUtils.SESSION_VALUE_BOUND :
                listener.valueBound(eventObject);
                break;
            case EventUtils.SESSION_VALUE_UNBOUND :
                listener.valueUnbound(eventObject);
                break;
            default:
                throw new IllegalArgumentException("Illegal eventCode.");
        }
    }
}
