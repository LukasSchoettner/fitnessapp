package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Workout;

import java.util.List;

public interface WorkoutServiceI {
    Workout saveWorkout(Workout workout);

    Workout updateWorkout(Workout workout);

    Workout getWorkoutById(Long id) throws NullPointerException;

    List<Workout> getAllWorkouts();

    void deleteWorkoutById(Long id);

    long getWorkoutCount();
}
