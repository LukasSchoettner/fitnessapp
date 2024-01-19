package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
