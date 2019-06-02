package com.readtorakesh.java8.interface_default_method;

public class ClassicCalculator implements Calculator {

	@Override
	public int add(int a, int b) {
		return (a + b);
	}

	@Override
	public int subtract(int a, int b) {
		return (a - b);
	}
}
