package com.readtorakesh.java8.interface_default_method;

public interface Calculator {
	int add(int a, int b);
	int subtract(int a, int b);
	
	default int multiply(int a, int b) {
		 throw new RuntimeException("Operation not supported. Upgrade to UltimateCalculator");
	}
	
	static void display(String value) {
		System.out.println(value);
	}
}
