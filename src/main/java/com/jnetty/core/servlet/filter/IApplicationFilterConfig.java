package com.jnetty.core.servlet.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public interface IApplicationFilterConfig extends FilterConfig {

    public void setFilterName(String name);
    public void setServletContext(ServletContext servletContext);
    public void setInitParameter(Map<String, String> params);
    public void setUrlPattern(String pattern);
    public String getUrlPattern();
}
