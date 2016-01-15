package com.jnetty.core.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 
 * @author wanghaiyang
 *
 * 加载servlet和lib, 每个Service一个ClassLoader, 对应Config中的servletClassLoader
 */
public class SimpleClassLoader extends URLClassLoader {

	public SimpleClassLoader(URL[] urls) {
		super(urls);
		
	}

}
