package com.jnetty.core.servlet.listener.observer;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Created by wanghaiyang on 16/1/24.
 */
public interface IObserver {

    public void addEventListener(EventListener eventListener) throws IllegalArgumentException;
    public void notify(int eventCode, EventObject eventObject) throws IllegalArgumentException;
}
