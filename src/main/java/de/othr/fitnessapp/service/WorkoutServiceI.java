package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Baseuser;
import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.model.Workout;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WorkoutServiceI {
    Workout saveWorkout(Workout workout);

    Workout updateWorkout(Workout workout);

    Workout getWorkoutById(Long id) throws NullPointerException;

    List<Workout> getAllWorkouts();

    void deleteWorkoutById(Long id);

    long getWorkoutCount();

    void addWorkoutToUser(Workout workout);

    int calculateTotalVolume(Long workoutId);

    int getWeeklyWorkoutCount(Baseuser baseuser, LocalDate startDate, LocalDate endDate);

    List<Workout> findAllByUserAndDateBetween(Baseuser user, LocalDate startDate, LocalDate endDate);

    Map<String, Object> getMuscleUse(List<Workout> workouts);

    List<Exercise> getRecommendedExercises(List<Workout> workouts);

}
