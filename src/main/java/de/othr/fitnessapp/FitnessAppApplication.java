package de.othr.fitnessapp;

<<<<<<< HEAD
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "de.othr.fitnessapp.repository")
public class FitnessAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitnessAppApplication.class, args);
    }
=======

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;




@SpringBootApplication
public class FitnessAppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FitnessAppApplication.class, args);
	}
>>>>>>> fe5157e (Trainer and Note Cruds + User + Spring Security)

}
