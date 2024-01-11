package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Workout;

import java.util.List;

public interface WorkoutService {
    Workout saveWorkout(Workout workout);

    Workout updateWorkout(Workout workout);

    Workout getWorkoutById(Long id) throws NullPointerException;

    List<Workout> getAllWorkouts();

    void deleteWorkoutById(Long id);

    long getWorkoutCount();

    void setCustomer(Customer customer);
}
