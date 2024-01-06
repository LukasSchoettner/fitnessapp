package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepositoryI extends JpaRepository<Workout, Long> {
    Page<Workout> findAll(Pageable pageable);
}
