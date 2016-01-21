package com.jnetty.util.config;

import com.jnetty.core.Config.MappingData;
import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.servlet.config.DefaultServletConfig;
import com.jnetty.core.servlet.context.DefaultServletContext;
import com.jnetty.core.servlet.context.ServletContextConfig;
import com.jnetty.core.servlet.filter.ApplicationFilterChain;
import com.jnetty.core.servlet.filter.FilterDef;
import com.jnetty.core.servlet.session.ISessionManager;
import com.jnetty.core.servlet.session.SessionManager;
import com.jnetty.util.log.JNettyLogger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WebXmlParser {
	private ServiceConfig serviceConfig = null;
	private File webXmlFile = null;
	private Map<String, Integer> servletsMapping = null;
	private Map<String, FilterDef> filtersMapping = null;
	public static final String CONTEXT_PARAM = "context-param";
	public static final String SERVLET = "servlet";
	public static final String SERVLET_NAME = "servlet-name";
	public static final String SERVLET_CLASS = "servlet-class";
	public static final String SERVLET_MAPPING = "servlet-mapping";
	public static final String URL_PATTERN = "url-pattern";
	public static final String INIT_PARAM = "init-param";
	public static final String PARAM_NAME = "param-name";
	public static final String PARAM_VALUE = "param-value";
	public static final String FILTER = "filter";
	public static final String FILTER_NAME = "filter-name";
	public static final String FILTER_CLASS = "filter-class";
	public static final String FILTER_MAPPING = "filter-mapping";
	
	public WebXmlParser(ServiceConfig config, File webXmlFile) {
		this.serviceConfig = config;
		this.webXmlFile = webXmlFile;
	}
	
	@SuppressWarnings("unchecked")
	public void parse() throws Exception {
		if (!webXmlFile.exists() || webXmlFile.isDirectory()) {
			throw new Exception(webXmlFile.getAbsolutePath() + " doesn't exits or is a directory.");
		}

		SAXReader xmlReader = new SAXReader();
		//DOCTYPE 中出现不可访问的url资源,read会被阻塞
		Document dom = xmlReader.read(webXmlFile);
		Element rootElement = dom.getRootElement();
		servletsMapping = new HashMap<String, Integer>();
		filtersMapping = new HashMap<String, FilterDef>();

		ServletContextConfig servletContextConfig = parseContext(rootElement);
		
		parseServlet(rootElement, servletContextConfig);

		parseFilter(rootElement, servletContextConfig);
	}

	private ServletContextConfig parseContext(Element rootElement) throws Exception {
		//ServletContext, parse
		ServletContextConfig servletContext = new DefaultServletContext();
		servletContext.setServiceConfig(serviceConfig);
		servletContext.setContextPath("/" + this.serviceConfig.WebAppName);
		serviceConfig.servletContextConfig = servletContext;

		//context init params, parse
		Map<String, String> contextParams = new HashMap<String, String>();
		Iterator<Element> contextParamIte = rootElement.elementIterator(CONTEXT_PARAM);
		parseInitParam(contextParamIte, contextParams);
		servletContext.setInitParams(contextParams);

		//SessionManager
		ISessionManager sessionManager = new SessionManager(serviceConfig);
		sessionManager.setServletContextConfig(servletContext);
		servletContext.setSessionManager(sessionManager);

		return servletContext;
	}

	private void parseServlet(Element rootElement, ServletContextConfig servletContext) throws  Exception {
		//parse servlet
		Iterator<Element> servletIte = rootElement.elementIterator(SERVLET);
		int servletCount = this.serviceConfig.servletList.size();
		while(servletIte.hasNext()) {
			Element serlvetElement = servletIte.next();
			Iterator<Element> servletNameIte = serlvetElement.elementIterator(SERVLET_NAME);
			Iterator<Element> servletClassIte = serlvetElement.elementIterator(SERVLET_CLASS);
			String servletName = "";
			String servletClass = "";
			if (servletNameIte.hasNext()) {
				servletName = servletNameIte.next().getTextTrim();
			}
			if (servletClassIte.hasNext()) {
				servletClass = servletClassIte.next().getTextTrim();
			}
			//ServletConfig, parse
			DefaultServletConfig servletConfig = new DefaultServletConfig();

			//servlet init params, parse
			Map<String, String> params = new HashMap<String, String>();
			Iterator<Element> initParamIte = serlvetElement.elementIterator(INIT_PARAM);
			parseInitParam(initParamIte, params);

			//init servletMappingData
			MappingData mappingData = new MappingData(servletName, servletClass);
			mappingData.servletConfig = servletConfig;
			mappingData.servletContextConfig = servletContext;
			servletConfig.setInitParams(params);
			servletConfig.setServletContextConfig(servletContext);
			servletConfig.setServletName(servletName);

			servletsMapping.put(servletName, servletCount);//for parse servlet-mapping node
			serviceConfig.servletList.add(mappingData);
			servletCount++;
		}
		//parse servlet-mapping
		Iterator<Element> servletMappingIte = rootElement.elementIterator(SERVLET_MAPPING);
		while(servletMappingIte.hasNext()) {
			Element servletMappingElement = servletMappingIte.next();
			Iterator<Element> servletNameIte = servletMappingElement.elementIterator(SERVLET_NAME);
			Iterator<Element> urlPatternIte = servletMappingElement.elementIterator(URL_PATTERN);
			String servletName = "";
			String servletUrlPattern = "";
			if (servletNameIte.hasNext()) {
				servletName = servletNameIte.next().getTextTrim();
			}
			if (urlPatternIte.hasNext()) {
				servletUrlPattern = urlPatternIte.next().getTextTrim();
			}
			int index = servletsMapping.get(servletName);
			MappingData mappingData = serviceConfig.servletList.get(index);
			if (mappingData != null) {
				mappingData.urlPattern = servletUrlPattern;
			}
		}
	}

	private void parseFilter(Element rootElement, ServletContextConfig servletContext) throws  Exception {
		serviceConfig.applicationFilterChain = new ApplicationFilterChain();
		//parse filter
		Iterator<Element> filterIte = rootElement.elementIterator(FILTER);
		while(filterIte.hasNext()) {
			Element filterElement = filterIte.next();
			Iterator<Element> filterNameIte = filterElement.elementIterator(FILTER_NAME);
			Iterator<Element> filterClassIte = filterElement.elementIterator(FILTER_CLASS);
			String filterName = "";
			String filterClass = "";
			if (filterNameIte.hasNext()) {
				filterName = filterNameIte.next().getTextTrim();
			}
			if (filterClassIte.hasNext()) {
				filterClass = filterClassIte.next().getTextTrim();
			}

			//filterDef parse
			FilterDef filterDef = new FilterDef(serviceConfig.servletClassLoader, filterClass);

			//filter init params, parse
			Map<String, String> params = new HashMap<String, String>();
			Iterator<Element> initParamIte = filterElement.elementIterator(INIT_PARAM);
			parseInitParam(initParamIte, params);

			filterDef.initConfig(filterName, servletContext.getInstance(), params);

			serviceConfig.applicationFilterChain.addFilterDef(filterDef);
			filtersMapping.put(filterName, filterDef);
		}
		//parse filter-mapping
		Iterator<Element> filterMappingIte = rootElement.elementIterator(FILTER_MAPPING);
		while(filterMappingIte.hasNext()) {
			Element filterMappingElement = filterMappingIte.next();
			Iterator<Element> filterNameIte = filterMappingElement.elementIterator(FILTER_NAME);
			Iterator<Element> urlPatternIte = filterMappingElement.elementIterator(URL_PATTERN);
			String filterName = "";
			String filterUrlPattern = "";
			if (filterNameIte.hasNext()) {
				filterName = filterNameIte.next().getTextTrim();
			}
			if (urlPatternIte.hasNext()) {
				filterUrlPattern = urlPatternIte.next().getTextTrim();
			}
			FilterDef filterDef = filtersMapping.get(filterName);
			if (filterDef != null) {
				filterDef.getConfig().setUrlPattern(filterUrlPattern);
			}
		}
	}
	/**
	 *  get name and value from <param-name/> and <param-value/> tags
	 */
	private void parseInitParam(Iterator<Element> paramIte, Map<String, String> params) {
		while (paramIte.hasNext()) {
			Element initParamElement = paramIte.next();
			Iterator<Element> paramNameIte = initParamElement.elementIterator(PARAM_NAME);
			Iterator<Element> paramValueIte = initParamElement.elementIterator(PARAM_VALUE);
			String paramName = "";
			String paramValue = "";
			if (paramNameIte.hasNext()) {
				paramName = paramNameIte.next().getTextTrim();
			}
			if (paramValueIte.hasNext()) {
				paramValue = paramValueIte.next().getTextTrim();
			}
			params.put(paramName, paramValue);
		}
	}
}
