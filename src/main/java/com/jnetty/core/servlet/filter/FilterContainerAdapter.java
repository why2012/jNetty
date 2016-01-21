package com.jnetty.core.servlet.filter;

import com.jnetty.core.container.IFilterContainer;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public interface FilterContainerAdapter {

    public void reset();
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException;
    public void setFilterContainer(IFilterContainer filterContainer);
    public void destroy();
}
