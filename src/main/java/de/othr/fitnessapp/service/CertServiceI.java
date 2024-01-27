package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import org.springframework.http.ResponseEntity;

public interface CertServiceI {

    ResponseEntity<byte[]> getCourseCert(Customer customer, Course course);
}
