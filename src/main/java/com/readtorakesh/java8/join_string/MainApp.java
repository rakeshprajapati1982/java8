package com.readtorakesh.java8.join_string;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class MainApp {

	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.method1();
		mainApp.method2();
		mainApp.method3();
		mainApp.method4();
		mainApp.method5();
	}
	
	private void method1() {
		String[] quote = {"Java8", "is", "awesome"};
		StringBuffer combined = new StringBuffer();
		for(int i=0;i<quote.length;i++) {
			combined.append(quote[i]);
			if(i < quote.length - 1) {
				combined.append("|");
			}
		}
		System.out.println("Output: " + combined.toString());
	}
	
	private void method2() {
		String[] quote = {"Java8", "is", "awesome"};
		String combined = String.join("|", quote);
		System.out.println("Output: " + combined);
	}

	private void method3() {
		List<String> quote = Arrays.asList(new String[] {"Java8", "is", "awesome"});
		String combined = String.join("|", quote);
		System.out.println("Output: " + combined);
	}

	private void method4() {
		List<String> quote = Arrays.asList(new String[] {"Java8", "is", "awesome"});
		
		StringJoiner stringJoiner = new StringJoiner("|");
		for(String item: quote) {
			stringJoiner.add(item);
		}
		
		String combined = stringJoiner.toString();
		System.out.println("Output: " + combined);
	}

	private void method5() {
		List<String> quote = Arrays.asList(new String[] {"Java8", "is", "awesome"});
		
		StringJoiner stringJoiner = new StringJoiner("|", "[", "]");
		for(String item: quote) {
			stringJoiner.add(item);
		}
		
		String combined = stringJoiner.toString();
		System.out.println("Output: " + combined);
	}
}
