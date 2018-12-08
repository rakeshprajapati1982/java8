package com.readtorakesh.java8.optional;

import java.util.Optional;

public class MainApp {

	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		
		//Laptop laptop = new Laptop(null, null);
		Laptop laptop = new Laptop(new Processor(7), new Ram(3));
		
		// Create optional from not null object, throw NullPointer exception if given value is null
		Optional<Laptop> optionalLaptop1 = Optional.of(laptop);
		
		// Create optional from nullable object
		/*
		if(earth is flat) {
			laptop = new Laptop(new Processor(7), new Ram(8));
		}
		*/
		Optional<Laptop> optionalLaptop2 = Optional.ofNullable(laptop);

		// Create empty optional
		Optional<Laptop> optionalLaptop3 = Optional.empty();
		
		// Check if optional has value means optional's value is not null 
		System.out.println("optionalLaptop1.isPresent()=" + optionalLaptop1.isPresent());
		System.out.println("optionalLaptop2.isPresent()=" + optionalLaptop2.isPresent());
		System.out.println("optionalLaptop3.isPresent()=" + optionalLaptop3.isPresent());
		
		// Execute logic if optional has value
		optionalLaptop1.ifPresent(
				o -> System.out.println("Laptop Found: Processor = " + o.getProcessor().getGeneration() + ", Ram = " + o.getRam().getSize()));
		
		// Get value from optional if present otherwise get other value
		Laptop finalLaptop1 = optionalLaptop3.orElse(new Laptop(new Processor(5), new Ram(6)));
		System.out.println("finalLaptop: " + finalLaptop1);
		
		// Get value from optional if present otherwise throw Exception
		Laptop finalLaptop2 = optionalLaptop2.orElseThrow(() -> new RuntimeException("Laptop is not present"));
		System.out.println("finalLaptop2: " + finalLaptop2);
		
		// Access object relationship hierarchy without null check
		// Before Java 8 - without Optional
		int size = -1;
		if(laptop != null) {
			Ram ram = laptop.getRam();
			if(ram != null) {
				size = ram.getSize();
			}
		}
		
		// Access object relationship hierarchy without null check
		size = optionalLaptop1.map(o -> o.getRam()).map(o -> o.getSize()).orElse(-1);
		System.out.println(size);
		
		// Print message if laptop has less than 4gb ram
		optionalLaptop1.map(Laptop::getRam).filter(o -> o.getSize() < 4).ifPresent( o -> System.out.println("Laptop less than 4gb Ram"));
	}
}

class Laptop{
	private Processor processor;
	private Ram ram;
	
	public Laptop(Processor processor, Ram ram) {
		super();
		this.processor = processor;
		this.ram = ram;
	}

	public Processor getProcessor() {
		return processor;
	}

	public Ram getRam() {
		return ram;
	}

	@Override
	public String toString() {
		return "Laptop [processor=" + processor + ", ram=" + ram + "]";
	}
}

class Processor{
	private int generation;

	public Processor(int generation) {
		super();
		this.generation = generation;
	}

	public int getGeneration() {
		return generation;
	}

	@Override
	public String toString() {
		return "Processor [generation=" + generation + "]";
	}
}

class Ram{
	private int size; // In GigaByte - GB

	public Ram(int size) {
		super();
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Ram [size=" + size + "]";
	}
}
