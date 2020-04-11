package com.readtorakesh.java8.constant.interfase;

public class MainApp {
	public static void main(String[] args) {
		/*
		System.out.println("InterfaceConstant.API_VERSION = " + ConstantInterface.API_VERSION);
		System.out.println("ImplClass.API_VERSION = " + ImplClass.API_VERSION);
		*/
		
		ConstantInterface interfase = new ImplClass();
		interfase.sampleMethod();
	}
}
