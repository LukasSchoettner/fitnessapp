package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Course;
import org.springframework.http.ResponseEntity;

public interface CertServiceI {

    ResponseEntity<byte[]> getCert(Course course);
}
