package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.model.WorkoutExercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {

    List<WorkoutExercise> findByWorkout(Workout workout);

}