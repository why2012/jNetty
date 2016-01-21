package com.jnetty.core.servlet.filter;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Map;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public class FilterDef {
    private Filter filter = null;
    private String filterClass = null;
    private IApplicationFilterConfig applicationFilterConfig = null;
    private ClassLoader classLoader = null;

    public FilterDef(ClassLoader classLoader, String filterClass) throws Exception {
        this.classLoader = classLoader;
        if (this.classLoader == null) {
            this.classLoader = this.getClass().getClassLoader();
        }
        this.filterClass = filterClass;
        initFilter();
    }

    public void initConfig(String filterName, ServletContext servletContext, Map<String, String> initParams) throws ServletException {
        applicationFilterConfig = new ApplicationFilterConfig();
        applicationFilterConfig.setFilterName(filterName);
        applicationFilterConfig.setServletContext(servletContext);
        applicationFilterConfig.setInitParameter(initParams);
        this.filter.init(applicationFilterConfig);
    }

    public IApplicationFilterConfig getConfig() {
        return applicationFilterConfig;
    }

    private void initFilter() throws Exception {
        this.filter = (Filter) classLoader.loadClass(filterClass).newInstance();
    }

    public void destroy() {
        this.filter.destroy();
    }

    public Filter getFilter() {
        return filter;
    }

    public String toString() {
        String returnString = "[FilterDef( " + filterClass + ", " + applicationFilterConfig + " )]";
        return returnString;
    }
}
