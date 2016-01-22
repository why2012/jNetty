package com.jnetty.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.jnetty.core.Config.ServiceConfig;
import com.jnetty.core.classloader.SimpleClassLoader;
import com.jnetty.util.config.WebXmlParser;

/**
 * 
 * @author wanghaiyang
 * 
 *  根路径为JNettyBase
 *	探测Webapp，解压缩WAR文件（JARFile），初始化ClassLoader，配置ServiceConfig
 */
public class AppDetector {
	private ServiceConfig serviceConfig = null;
	private String absoluteStaticResourcePath = null;
	private String webAppPath = null;
	private WebXmlParser webXmlParser = null;
	private List<URL> urlList = null;
	
	public AppDetector(ServiceConfig config) {
		this.serviceConfig = config;
		init();
	}
	
	public void scan() throws Exception {
		File webappFile = new File(this.webAppPath);
		
		if (webappFile.isDirectory()) {
			if (!webappFile.exists()) {
				throw new FileNotFoundException(webappFile.getAbsolutePath());
			} 
			//webapp is a directory
		} else {
			//webapp maybe a war file
			webappFile = new File(this.webAppPath + ".war");
			if (!webappFile.exists()) {
				//webapp is not a war file
				throw new FileNotFoundException("Unsupport file type[must be a directory or a war file.]: " + webappFile.getAbsolutePath());
			}
			//webapp is a war file
			this.extractWarFile(webappFile);
		}
		//create and init servlet class loader
		this.createClassLoader();

		//parse web.xml
		webXmlParser = new WebXmlParser(serviceConfig, new File(webAppPath + "/WEB-INF/web.xml"));
		webXmlParser.parse();
	}
	
	private void init() {
		this.absoluteStaticResourcePath = this.getAbsoluteStaticResourcePath();
		this.serviceConfig.staticResourceLoc = this.absoluteStaticResourcePath;
		urlList = new ArrayList<URL>();
	}
	
	private void extractWarFile(File warFile) throws Exception {
		//make a dir for webapp
		File dir = new File(this.webAppPath);
		dir.mkdir();
		JarFile jarFile = new JarFile(warFile);
		Enumeration<JarEntry> entries = jarFile.entries();
		byte[] buffer = new byte[1024];
		//copy file to dir
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				File dirFile = new File(webAppPath + "/" + entry.getName());
				if (!dirFile.exists()) {
					dirFile.mkdir();
				}
			} else {
				File newFile = new File(webAppPath + "/" + entry.getName());
				newFile.createNewFile();
				BufferedInputStream bufferedIns = new BufferedInputStream(jarFile.getInputStream(entry));
				BufferedOutputStream bufferedOus = new BufferedOutputStream(new FileOutputStream(newFile));
				int count = -1;
				while((count = bufferedIns.read(buffer)) > 0) {
					bufferedOus.write(buffer, 0, count);
				}
				bufferedOus.close();
				bufferedIns.close();
			}
		}
		jarFile.close();
	}
	
	private String getAbsoluteStaticResourcePath() {
		String path = "";
		
		serviceConfig.WebAppName.replaceAll("/", "");
		if (serviceConfig.JNettyBase.endsWith("/")) {
			path = serviceConfig.JNettyBase + serviceConfig.WebAppName;
		} else {
			path = serviceConfig.JNettyBase + "/" + serviceConfig.WebAppName;
		}
		
		this.webAppPath = path;
		
		if (serviceConfig.staticResourceLoc.startsWith("/")) {
			path = path + serviceConfig.staticResourceLoc;
		} else {
			path = path + "/" +serviceConfig.staticResourceLoc;
		}
		
		//remove the last /
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		
		return path;
	}
	
	private void createClassLoader() throws Exception {
		//record jar file url
		File libFile = new File(webAppPath + "/WEB-INF/lib/");
		if (libFile.exists()) {
			File[] jarFiles = libFile.listFiles();
			for (File jarFile : jarFiles) {
				if (jarFile.isFile() && jarFile.getName().endsWith(".jar")) {
					urlList.add(jarFile.toURI().toURL());
				}
			}
		}
		urlList.add(new File(webAppPath + "/WEB-INF/classes/").toURI().toURL());
		URL[] urls = new URL[urlList.size()];
		urlList.toArray(urls);
		SimpleClassLoader classloader = new SimpleClassLoader(urls, Thread.currentThread().getContextClassLoader());
		classloader.setServiceConfig(serviceConfig);
		this.serviceConfig.servletClassLoader = classloader;
	}
}
