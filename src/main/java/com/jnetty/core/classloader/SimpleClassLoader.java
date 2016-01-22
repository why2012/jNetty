package com.jnetty.core.classloader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
	private ClassLoader parent = null;
	private ClassLoader system = null;
	private List<JarFileEntry> jarFileList = new ArrayList<JarFileEntry>();
	private ConcurrentHashMap<String, Class<?>> classBuffer = new ConcurrentHashMap<String, Class<?>>();
	
	public SimpleClassLoader(URL[] urls) {
		this(urls, null);
	}

	public SimpleClassLoader(URL[] urls, ClassLoader parent)  {
		super(urls, parent);
		this.urls = urls;
		this.parent = parent;
		this.system = getSystemClassLoader();
		for (URL url : urls) {
			String path = url.getPath();
			if (path.endsWith(".jar")) {
				try {
					JarFileEntry jarFileEntry = new JarFileEntry();
					jarFileEntry.jarFile = new JarFile(path);
					jarFileEntry.originPath = path;
					jarFileList.add(jarFileEntry);
				} catch (IOException e) {
					JNettyLogger.log(e);
				}
			}
		}
	}

	@Override
	public Class<?> findClass(String name) {
		//try to find class in /WEB-INF/classes/ and /lib/
		String originName = name;
		name = name.replace(".", "/") + ".class";
		Class<?> _class = null;
		try {
			String classPathName = this.getFullPath("WEB-INF/classes/" + name);
			File classFile = new File(classPathName);
			if (classFile.exists()) {
				byte[] classData = getByteArrayFromStream(new FileInputStream(classFile));
				_class = this.defineClass(originName, classData, 0, classData.length);
				classBuffer.put(originName, _class);
			}

			if (_class == null) {
				for (JarFileEntry jarFileEntry : jarFileList) {
					JarFile jarFile = jarFileEntry.jarFile;
					JarEntry jarEntry = jarFile.getJarEntry(name);
					if (jarEntry != null) {
						byte[] classData = getByteArrayFromStream(jarFile.getInputStream(jarEntry));
						_class = this.defineClass(originName, classData, 0, classData.length);
						classBuffer.put(originName, _class);
						break;
					}
				}
			}

			if (_class == null) {
				_class = super.findClass(originName);
			}
		} catch(IOException e) {
			JNettyLogger.logD(e);
			return _class;
		} catch (ClassNotFoundException e) {
			JNettyLogger.logD(e);
			return _class;
		}

		return _class;
	}

	private byte[] getByteArrayFromStream(InputStream ins) throws IOException {
		BufferedInputStream bufIns = new BufferedInputStream(ins);
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int count = -1;
		while ((count = bufIns.read(buffer)) != -1) {
			byteOut.write(buffer, 0, count);
		}
		bufIns.close();
		return byteOut.toByteArray();
	}

	public Class<?> findLoadedClass0(String name) {
		return classBuffer.get(name);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		JNettyLogger.logD("loadClass: " + name);
		//本地缓存
		Class<?> _class = findLoadedClass0(name);
		if (_class != null) {
			return _class;
		}

		//上级缓存
		_class = findLoadedClass(name);
		if (_class != null) {
			return _class;
		}

		try {
			_class = system.loadClass(name);
			if (_class != null) {
				return _class;
			}
		} catch (ClassNotFoundException e) {

		}

		try {
			_class = parent != null ? parent.loadClass(name) : null;
			if (_class != null) {
				return _class;
			}
		} catch (ClassNotFoundException e) {

		}

		_class = findClass(name);
		if (_class != null) {
			return _class;
		}

		throw new ClassNotFoundException(name);
	}
	
	public void setServiceConfig(ServiceConfig serviceConfig) {
		this.serviceConfig = serviceConfig;
	}

	private String getFullPath(String url) {
		return serviceConfig.JNettyBase + "/" + serviceConfig.WebAppName + "/" + url;
	}
	
	@Override
	public URL getResource(String name) {
		JNettyLogger.logD("getResource: " + name);
		URL url = super.getResource(name);
		if (url != null) {
			return url;
		}
		url = findResource(name);
		return url;
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		JNettyLogger.logD("getResourceAsStream: " + name);
		InputStream ins = super.getResourceAsStream(name);
		if (ins != null) {
			return ins;
		}
		File file = findFile(name);
		if (file != null) {
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				return fileInputStream;
			} catch (IOException e) {
				JNettyLogger.log(e);
			}
		}
		JarFileEntry jarFileEntry = findJarFileEntry(name);
		if (jarFileEntry != null) {
			try {
				InputStream inputStream = jarFileEntry.jarFile.getInputStream(jarFileEntry.curJarEntry);
				return inputStream;
			} catch (IOException e) {
				JNettyLogger.log(e);
			}
		}

		return null;
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		JNettyLogger.logD("getResources: " + name);
		Enumeration<URL> urls = super.getResources(name);
		if (urls != null) {
			return urls;
		}
		return findResources(name);
	}

	@Override
	public URL findResource(String name) {
		JNettyLogger.logD("findResource: " + name);
		URL url = null;

		File file = findFile(name);
		if (file != null) {
			try {
				url = file.toURI().toURL();
			} catch (MalformedURLException e) {
				JNettyLogger.log(e);
			}
			return url;
		}


		JarFileEntry jarFileEntry = findJarFileEntry(name);
		if (jarFileEntry != null) {
			JarEntry jarEntry = jarFileEntry.curJarEntry;
			String originPath = jarFileEntry.originPath;
			try {
				url = new URL("jar", null , originPath + "!/" + jarEntry.getName());
			} catch (MalformedURLException e) {
				JNettyLogger.log(e);
			}
			return url;
		}

		return null;
	}

	@Override
	public Enumeration<URL> findResources(String name) throws IOException {
		return super.findResources(name);
	}

	private File findFile(String name) {
		if (name.endsWith(".class")) {
			name = "WEB-INF/classes/" + name;
		}
		String path = this.getFullPath(name);
		File file = new File(path);
		if (file.exists()) {
			return file;
		}
		return null;
	}

	private JarFileEntry findJarFileEntry(String name) {
		for (JarFileEntry jarFileEntry : jarFileList) {
			JarFile jarFile = jarFileEntry.jarFile;
			JarEntry jarEntry = jarFile.getJarEntry(name);
			if (jarEntry != null) {
				JarFileEntry jarFileEntryReturn = new JarFileEntry();
				jarFileEntryReturn.jarFile = jarFile;
				jarFileEntryReturn.curJarEntry = jarEntry;
				jarFileEntryReturn.originPath = jarFileEntry.originPath;
				return jarFileEntryReturn;
			}
		}
		return null;
	}

	@Override
	public Package getPackage(String name) {
		JNettyLogger.logD("getPackage: " + name);
		return super.getPackage(name);
	}

	@Override
	public Package[] getPackages() {
		Package[] packs = super.getPackages();
		for (int i = 0 ; i < packs.length ; i++) {
			JNettyLogger.logI(packs[i].getName());
		}
		return packs;
	}
	
	public String toString() {
		String str = "[SimpleClassLoader(" + super.toString() + ")\n(";
		for (URL url : urls) {
			str += "\n" + url;
		}
		return str + ")]";
	}

	private class JarFileEntry {
		public JarFile jarFile = null;
		public JarEntry curJarEntry = null;
		public String originPath = "";
	}
}
