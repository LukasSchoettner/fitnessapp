package de.othr.fitnessapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//Exclude Spring Security AutoConfiguration for development phase
@SpringBootApplication //(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class, org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class})
//@SpringBootApplication
@EnableJpaRepositories(basePackages = "de.othr.fitnessapp.repository")

public class FitnessAppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FitnessAppApplication.class, args);
	}

}
