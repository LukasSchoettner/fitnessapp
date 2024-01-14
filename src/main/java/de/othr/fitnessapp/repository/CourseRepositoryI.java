package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

public interface CourseRepositoryI extends JpaRepository<Course, Long> {

    Page<Course> findByDateLessThanOrderByDateDesc(LocalDate date, Pageable pageable);

    Page<Course> findByDateGreaterThanEqualOrderByDateDesc(LocalDate date, Pageable pageable);

    Page <Course> findByNameContainingIgnoreCase (String name, Pageable pageable);
}