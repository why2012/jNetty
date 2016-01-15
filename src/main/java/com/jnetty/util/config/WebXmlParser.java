package com.jnetty.util.config;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jnetty.core.Config.MappingData;
import com.jnetty.core.Config.ServiceConfig;

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
		Document dom = xmlReader.read(webXmlFile);
		Element rootElement = dom.getRootElement();
		servletsMapping = new HashMap<String, Integer>();
		
		//parse servlet
		Iterator<Element> servletIte = rootElement.elementIterator("servlet");
		int servletCount = this.serviceConfig.servletMapping.size();
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
			MappingData mappingData = new MappingData(servletName, servletClass);
			servletsMapping.put(servletName, servletCount);
			serviceConfig.servletMapping.add(mappingData);
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
			serviceConfig.servletMapping.get(index).urlPattern = servletUrlPattern;
		}
	}
}
