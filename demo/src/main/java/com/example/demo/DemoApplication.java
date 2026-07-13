package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		Comet hailey = new Comet("Hailey's", 100.00);

		System.out.print("The distance is " + hailey.getDistanceFromEarth() + 
		", and the name is " + hailey.getOfficialName());
	}

}
