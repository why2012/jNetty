package com.jnetty.core.servlet.filter;

import com.jnetty.core.Config;
import com.jnetty.core.container.IFilterContainer;
import com.jnetty.util.url.URLMatch;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public class ApplicationFilterChain implements IApplicationFilterChain {
    private Config.ServiceConfig serviceConfig = null;
    private IFilterContainer filterContainer = null;
    private List<FilterDef> filterDefs = new ArrayList<FilterDef>();
    private ThreadLocal<Integer> curFilterIndex = new ThreadLocal<Integer>();

    public void addFilterDef(FilterDef filterDef) {
        filterDefs.add(filterDef);
    }

    /**
     * invoke by filters
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        int index = curFilterIndex.get();
        String pathInfo = ((HttpServletRequest)request).getPathInfo();
        FilterDef filterDef = null;
        int size = filterDefs.size();
        do {
            curFilterIndex.set(index + 1);
            if (index >= size) {
                filterContainer.invokeLast();
                return;
            }
            filterDef = filterDefs.get(index);
            if (filterDef == null) {
                filterContainer.invokeLast();
                return;
            }
            if (URLMatch.match(pathInfo, filterDef.getConfig().getUrlPattern())) {
                break;
            }
            index++;
        } while (true);

        filterDef.getFilter().doFilter(request, response, this);
    }

    public Config.ServiceConfig getConfig() {
        return serviceConfig;
    }

    public void setConfig(Config.ServiceConfig config) {
        serviceConfig = config;
    }

    /**
     * invoke by filterContainer
     */
    public void reset() {
        curFilterIndex.set(0);
    }

    public void setFilterContainer(IFilterContainer filterContainer) {
        this.filterContainer = filterContainer;
    }

    public void destroy() {
        for (FilterDef filterDef : filterDefs) {
            filterDef.destroy();
        }
    }

    public String toString() {
        String returnString = "[ApplicationFilterChain( \n";
        for (FilterDef filterDef : filterDefs) {
            returnString += filterDef + "\n";
        }
        returnString += " )]";
        return returnString;
    }
}
