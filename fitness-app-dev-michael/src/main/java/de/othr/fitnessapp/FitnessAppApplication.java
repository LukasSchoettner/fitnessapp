package de.othr.fitnessapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;




@SpringBootApplication
public class FitnessAppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FitnessAppApplication.class, args);
	}

}
