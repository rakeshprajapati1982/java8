package com.readtorakesh.java8.constant.interfase;

public class ImplClass implements ConstantInterface {
	String API_VERSION = "1.1";
	
	@Override
	public void sampleMethod() {
		// some code
		System.out.println("InterfaceConstant.API_VERSION = " + ConstantInterface.API_VERSION);
		System.out.println("API_VERSION = " + API_VERSION);
		
		API_VERSION = "1.2";
		System.out.println("API_VERSION = " + API_VERSION);
	}
}
