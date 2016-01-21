package com.jnetty.core.servlet.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public class ApplicationFilterConfig implements IApplicationFilterConfig {
    private String name = null;
    private ServletContext servletContext = null;
    private Map<String, String> params = null;
    private String urlPattern = null;

    public String getFilterName() {

        return name;
    }

    public ServletContext getServletContext() {

        return servletContext;
    }

    public String getInitParameter(String name) {

        return params.get(name);
    }

    public Enumeration getInitParameterNames() {

        return Collections.enumeration(params.keySet());
    }

    public void setFilterName(String name) {
        this.name = name;
    }

    public void setUrlPattern(String pattern) {
        this.urlPattern = pattern;
    }

    public String getUrlPattern() {
        return this.urlPattern;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setInitParameter(Map<String, String> params) {
        this.params = params;
    }

    public String toString() {
        String returnString = "[ApplicationFilterConfig( " + name + ", " + urlPattern + ", " + params;
        returnString += " )]";
        return returnString;
    }
}
