package com.jnetty.util.log;

public class JNettyLogger {
	public static final int TRACE = 5;
	public static final int DEBUG = 4;
	public static final int INFO = 3;
	public static final int WARN = 2;
	public static final int FATAL = 1;
	public static boolean debug = true;
	
	public static void log(Object msg) {
		log(msg, WARN);
	}
	
	public static void logT(Object msg) {
		log(msg, TRACE);
	}
	
	public static void logD(Object msg) {
		log(msg, DEBUG);
	}
	
	public static void logI(Object msg) {
		log(msg, INFO);
	}
	
	public static void logF(Object msg) {
		log(msg, FATAL);
	}
	
	public static void log(Object msg, int level) {
		switch (level) {
			case TRACE :
				_log(msg, "[TRACE] ");
				break;
			case DEBUG :
				_log(msg, "[DEBUG] ");
				break;
			case INFO :
				_log(msg, "[INFO] ");
				break;
			case WARN :
				_log(msg, "[WARN] ");
				break;
			case FATAL :
				_log(msg, "[FATAL] ");
				break;
			default :
				
		}
		if (debug && (msg instanceof Exception)) {
			((Exception)msg).printStackTrace();
		}
	}
	
	private static void _log(Object msg, String head) {
		System.out.println(head + msg);
	}
}
