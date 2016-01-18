package com.jnetty.util.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.jnetty.util.log.JNettyLogger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jnetty.core.Config.MappingData;
import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.servlet.config.DefaultServletConfig;
import com.jnetty.core.servlet.context.DefaultServletContext;
import com.jnetty.core.servlet.context.ServletContextConfig;

public class WebXmlParser {
	private ServiceConfig serviceConfig = null;
	private File webXmlFile = null;
	private Map<String, Integer> servletsMapping = null;
	
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
		
		//parse servlet
		Iterator<Element> servletIte = rootElement.elementIterator("servlet");
		int servletCount = this.serviceConfig.servletList.size();
		while(servletIte.hasNext()) {
			Element serlvetElement = servletIte.next();
			Iterator<Element> servletNameIte = serlvetElement.elementIterator("servlet-name");
			Iterator<Element> servletClassIte = serlvetElement.elementIterator("servlet-class");
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
			Iterator<Element> initParamIte = serlvetElement.elementIterator("init-param");
			while (initParamIte.hasNext()) {
				Element initParamElement = initParamIte.next();
				Iterator<Element> paramNameIte = initParamElement.elementIterator("param-name");
				Iterator<Element> paramValueIte = initParamElement.elementIterator("param-value");
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
			
			//ServletContext, parse
			ServletContextConfig servletContext = new DefaultServletContext();
			servletContext.setServiceConfig(serviceConfig);
			servletContext.setContextPath("/" + this.serviceConfig.WebAppName);
			
			//context init params, parse
			Map<String, String> contextParams = new HashMap<String, String>();
			Iterator<Element> contextParamIte = rootElement.elementIterator("context-param");
			while(contextParamIte.hasNext()) {
				Element initParamElement = contextParamIte.next();
				Iterator<Element> paramNameIte = initParamElement.elementIterator("param-name");
				Iterator<Element> paramValueIte = initParamElement.elementIterator("param-value");
				String paramName = "";
				String paramValue = "";
				if (paramNameIte.hasNext()) {
					paramName = paramNameIte.next().getTextTrim();
				}
				if (paramValueIte.hasNext()) {
					paramValue = paramValueIte.next().getTextTrim();
				}
				contextParams.put(paramName, paramValue);
			}
			
			//init servletMappingData
			MappingData mappingData = new MappingData(servletName, servletClass);
			mappingData.servletConfig = servletConfig;
			mappingData.servletContextConfig = servletContext;
			servletConfig.setInitParams(params);
			servletConfig.setServletContextConfig(servletContext);
			servletConfig.setServletName(servletName);
			servletContext.setInitParams(contextParams);
			
			servletsMapping.put(servletName, servletCount);//for parse servlet-mapping node
			serviceConfig.servletList.add(mappingData);
			servletCount++;
		}
		//parse servlet-mapping
		Iterator<Element> servletMappingIte = rootElement.elementIterator("servlet-mapping");
		while(servletMappingIte.hasNext()) {
			Element servletMappingElement = servletMappingIte.next();
			Iterator<Element> servletNameIte = servletMappingElement.elementIterator("servlet-name");
			Iterator<Element> urlPatternIte = servletMappingElement.elementIterator("url-pattern");
			String servletName = "";
			String servletUrlPattern = "";
			if (servletNameIte.hasNext()) {
				servletName = servletNameIte.next().getTextTrim();
			}
			if (urlPatternIte.hasNext()) {
				servletUrlPattern = urlPatternIte.next().getTextTrim();
			}
			int index = servletsMapping.get(servletName);
			serviceConfig.servletList.get(index).urlPattern = servletUrlPattern;
		}
	}
}
