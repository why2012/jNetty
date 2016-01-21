package com.jnetty.core.container;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public interface ContainerChain {

    public void setNext(Container container);
    public Container getNext();
}
