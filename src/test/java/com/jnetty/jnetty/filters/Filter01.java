package com.jnetty.jnetty.filters;

import com.jnetty.util.log.JNettyLogger;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public class Filter01 implements Filter {

    private FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        JNettyLogger.logD("Filter01");
        PrintWriter out = response.getWriter();
        out.print("<h3>Filter01 in.<h3>");
        out.print("<h3>" + filterConfig.getInitParameter("count") + "</h3>");
        out.close();
        chain.doFilter(request, response);
        out = response.getWriter();
        out.print("<h3>Filter01 out.<h3>");
        out.close();
    }

    public void destroy() {

    }
}
