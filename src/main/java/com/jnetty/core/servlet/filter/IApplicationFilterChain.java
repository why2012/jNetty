package com.jnetty.core.servlet.filter;

import com.jnetty.core.IConfiguration;

import javax.servlet.FilterChain;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public interface IApplicationFilterChain extends FilterChain, IConfiguration, FilterContainerAdapter {

    public void addFilterDef(FilterDef filterDef);
}
