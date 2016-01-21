package com.jnetty.core.container;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public interface IFilterContainer {

    /**
     * invoke next container
     */
    public void invokeLast();
}
