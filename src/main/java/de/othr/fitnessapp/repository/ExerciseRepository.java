package de.othr.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.othr.fitnessapp.model.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
    boolean existsByName(String name);
}
