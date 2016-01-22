package com.jnetty.jnetty.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by wanghaiyang on 16/1/23.
 *
 *  暂时代替ContextLoaderListener, 加载一个XmlWebApplicationContext 到ServletContext
 */
public class ContextLoaderFilter implements Filter {
    private FilterConfig filterConfig = null;
    private String rootContextName = "";//WebApplicationContext.class.getName() + ".ROOT";

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //用 BeanUtils.instantiateClass(XmlWebApplicationContext.class)
        //加载一个 ApplicationContext 到 ServletContext的Attribute 里,供 FrameworkServlet 使用
//        ServletContext servletContext = filterConfig.getServletContext();
//        PrintWriter out = response.getWriter();
//        XmlWebApplicationContext webApplicationContext = (XmlWebApplicationContext) servletContext.getAttribute(rootContextName);
//        out.print(webApplicationContext);
//        if (webApplicationContext == null) {
//            ContextLoader contextLoader = new ContextLoader();
//            contextLoader.initWebApplicationContext(filterConfig.getServletContext());
//            out.print("Context initialized.");
//        }
//        out.close();
//        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
