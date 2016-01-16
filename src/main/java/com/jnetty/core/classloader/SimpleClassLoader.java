package com.jnetty.core.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.util.log.JNettyLogger;

/**
 * 
 * @author wanghaiyang
 *
 * 加载servlet和lib, 每个Service一个ClassLoader, 对应Config中的servletClassLoader
 */
public class SimpleClassLoader extends URLClassLoader {
	private URL[] urls = null;
	private ServiceConfig serviceConfig = null;
	
	public SimpleClassLoader(URL[] urls) {
		super(urls);
		this.urls = urls;
	}

	public SimpleClassLoader(URL[] urls, ClassLoader parent)  {
		super(urls, parent);
		this.urls = urls;
	}
	
	public SimpleClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
		super(urls, parent, factory);
		this.urls = urls;
	}
	
	public void setServiceConfig(ServiceConfig serviceConfig) {
		this.serviceConfig = serviceConfig;
	}
	/*
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		JNettyLogger.log(name);
		Class<?> _class = super.loadClass(name);
		//org.springframework.beans.factory.xml.BeanDefinitionParserDelegate.parseCustomElement
		JNettyLogger.logD(_class);
		return _class;
	}*/
	
	@Override
	public URL getResource(String url) {
		URL uri = null;
		File file = new File(this.getFullPath(url));
		if (file.exists()) {
			try {
				uri = file.toURI().toURL();
			} catch (MalformedURLException e) {
				JNettyLogger.log(e);
			}
		} else {
			uri = super.getResource(url);
		}
		JNettyLogger.logD("getResource: " + url + " , " + uri);
		return uri;
	}
	
	@Override
	public InputStream getResourceAsStream(String url) {
		InputStream ins = null;
		File file = new File(this.getFullPath(url));
		if (file.exists()) {
			try {
				ins = new FileInputStream(file);
				url = file.getAbsolutePath();
			} catch (FileNotFoundException e) {
				JNettyLogger.log(e);
			}
		} else {
			ins = super.getResourceAsStream(url);
		}
		JNettyLogger.logD("getResourceAsStream: " + url);
		return ins;
	} 
	
	@Override
	public Enumeration<URL> getResources(String url) throws IOException {
		File file = new File(this.getFullPath(url));
		Enumeration<URL> _enum = null;
		if (file.exists()) {
			List<URL> urlList = new ArrayList<URL>();
			URL _url = file.toURI().toURL();
			urlList.add(_url);
			url = _url.toString();
			_enum = Collections.enumeration(urlList);
		} else {
			_enum = super.getResources(url);
		}
		JNettyLogger.logD("getResources: " + url);
		return _enum;
	} 
	
	private String getFullPath(String url) {
		return serviceConfig.JNettyBase + "/" + serviceConfig.WebAppName + "/" + url;
	}
	
	public String toString() {
		String str = "[SimpleClassLoader(" + super.toString() + ")\n(";
		for (URL url : urls) {
			str += "\n" + url;
		}
		return str + ")]";
	}
}
