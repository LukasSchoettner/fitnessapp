package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface WorkoutRepositoryI extends JpaRepository<Workout, Long> {
    Page<Workout> findAll(Pageable pageable);

    Page<Workout> findByDateLessThanOrderByDateDesc(LocalDate date, Pageable pageable);

    Page<Workout> findByDateGreaterThanEqualOrderByDateDesc(LocalDate date, Pageable pageable);

}
