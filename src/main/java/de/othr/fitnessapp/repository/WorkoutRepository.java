package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
