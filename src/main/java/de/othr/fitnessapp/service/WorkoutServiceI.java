package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkoutServiceI {
    Workout saveWorkout(Workout workout);

    Workout updateWorkout(Workout workout);

    Workout getWorkoutById(Long id) throws NullPointerException;

    Page<Workout> getAllWorkouts(Pageable pageable);
    List<Workout> getAllWorkouts();

    void deleteWorkoutById(Long id);

    void deleteWorkout(Workout workout);

    void deleteAllWorkouts();

    long getWorkoutCount();
}
