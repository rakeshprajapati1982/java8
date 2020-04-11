package com.readtorakesh.java8.constant.clazz;

public final class ConstantClass {
	private ConstantClass() {}
	
	//public static final String API_VERSION = "1.0";
	
	public static final String API_VERSION;
	
	static {
		API_VERSION = "1.1";
	}
	
}
