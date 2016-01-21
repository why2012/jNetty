package com.jnetty.util.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JNettyLogger {
	public static final int TRACE = 5;
	public static final int DEBUG = 4;
	public static final int INFO = 3;
	public static final int WARN = 2;
	public static final int FATAL = 1;
	public static boolean printStackTrace = false;
	public static boolean trace = true;
	public static boolean debug = true;
	public static boolean info = true;
	public static boolean warn = true;
	public static boolean fatal = true;

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
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
				if(trace) _log(msg, "[TRACE] ");
				break;
			case DEBUG :
				if(debug) _log(msg, "[DEBUG] ");
				break;
			case INFO :
				if(info) _log(msg, "[INFO] ");
				break;
			case WARN :
				if(warn) _log(msg, "[WARN] ");
				break;
			case FATAL :
				if(fatal) _log(msg, "[FATAL] ");
				break;
			default :
				
		}
	}
	
	public static void logWithMsg(String message, Object msg, int level) {
		switch (level) {
			case TRACE :
				if(trace) _log(msg, "[TRACE: " + message + "] ");
				break;
			case DEBUG :
				if(debug) _log(msg, "[DEBUG " + message + "] ");
				break;
			case INFO :
				if(info) _log(msg, "[INFO " + message + "] ");
				break;
			case WARN :
				if(warn) _log(msg, "[WARN " + message + "] ");
				break;
			case FATAL :
				if(fatal) _log(msg, "[FATAL " + message + "] ");
				break;
			default :
				
		}
	}
	
	private static void _log(Object msg, String head) {
		System.out.println("[" + dateFormat.format(new Date()) + "] " + head + msg);
		if (printStackTrace && (msg instanceof Exception)) {
			((Exception)msg).printStackTrace();
		}
	}
}
