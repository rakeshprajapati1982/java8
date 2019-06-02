package com.readtorakesh.java8.interface_default_method;

public class MainApp {

	public static void main(String[] args) {
		new SampleImplClass().showMethod();
	}
}

/* 
--- Output ---
SampleInterface1.showMethod()
*/

interface SampleInterface1{
	default void showMethod() {
		System.out.println("SampleInterface1.showMethod()");
	}
}

interface SampleInterface2{
	default void showMethod() {
		System.out.println("SampleInterface2.showMethod()");
	}
}

class SampleImplClass implements SampleInterface1, SampleInterface2 {

	@Override
	public void showMethod() {
		//call default method of SampleInterface1
		SampleInterface1.super.showMethod();
	}
	
}