package de.othr.fitnessapp.service;

import org.springframework.stereotype.Service;

@Service
public interface WorkoutExerciseService {

    void updateActualRepetitions(Long workoutExerciseId, int actualRepetitions);

    Long getWorkoutIdbyWorkoutExerciseId(Long id);

}