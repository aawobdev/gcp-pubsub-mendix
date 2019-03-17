package gcp.impl;

import com.mendix.core.Core;

public class Logger {
	
	private static final String LogNode = "GoogleCloudPlatform";
	
	public static void info(String message) {
		Core.getLogger(LogNode).info(message);
	}
	
	public static void warn(String message) {
		Core.getLogger(LogNode).warn(message);
	}
	
	public static void error(String message) {
		Core.getLogger(LogNode).error(message);
	}
	
	public static void trace(String message) {
		Core.getLogger(LogNode).trace(message);
	}
	
	public static void debug(String message) {
		Core.getLogger(LogNode).debug(message);
	}

}
