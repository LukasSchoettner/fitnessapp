package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByNameContainingIgnoreCase (String name);
}
