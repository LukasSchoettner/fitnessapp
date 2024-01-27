package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Baseuser;
import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import de.othr.fitnessapp.model.WorkoutExercise;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WorkoutServiceI {
    Workout saveWorkout(Workout workout);

    @Transactional
    Workout updateWorkout(Workout workout);

    Workout getWorkoutById(Long id) throws NullPointerException;

    Page<Workout> getAllWorkouts(Pageable pageable);

    List<Workout> getAllWorkouts();

    Page<Workout> getAllWorkoutsOfUser(Baseuser baseuser, Pageable pageable);

    List<Workout> getAllPlannedWorkoutsOfUser(Baseuser baseuser);

    void deleteWorkoutById(Long id);

    void deleteWorkout(Workout workout);

    void deleteAllWorkouts();

    long getWorkoutCount();

    void addWorkoutToUser(Workout workout);

    int calculateTotalVolume(Workout workout);

    int getWeeklyWorkoutCount(Baseuser baseuser, LocalDate startDate, LocalDate endDate);

    List<Workout> findAllByUserAndDateBetween(Baseuser user, LocalDate startDate, LocalDate endDate);

    Map<String, Object> getMuscleUse(List<Workout> workouts);

    List<Exercise> getRecommendedExercises(List<Workout> workouts);

    WorkoutExercise getNextExercise(Long workoutId);

}
