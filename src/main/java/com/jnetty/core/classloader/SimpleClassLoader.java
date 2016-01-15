package com.jnetty.core.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * 
 * @author wanghaiyang
 *
 * 加载servlet和lib, 每个Service一个ClassLoader, 对应Config中的servletClassLoader
 */
public class SimpleClassLoader extends URLClassLoader {
	private URL[] urls = null;

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
	
	public String toString() {
		String str = "[SimpleClassLoader(" + super.toString() + ")\n(";
		for (URL url : urls) {
			str += "\n" + url;
		}
		return str + ")]";
	}
}
