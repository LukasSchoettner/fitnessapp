package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import de.othr.fitnessapp.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByDateLessThanOrderByDateDesc(LocalDate date, Pageable pageable);

    Page<Course> findByDateGreaterThanEqualOrderByDateDesc(LocalDate date, Pageable pageable);

    List<Course> findByNameContainingIgnoreCase (String name);
}

    Page <Course> findByNameContainingIgnoreCase (String name, Pageable pageable);
}