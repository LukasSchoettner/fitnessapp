package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Baseuser;
import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.model.WorkoutExercise;
import de.othr.fitnessapp.repository.UserRepository;
import de.othr.fitnessapp.repository.WorkoutExerciseRepository;
import de.othr.fitnessapp.repository.WorkoutRepository;
import de.othr.fitnessapp.service.ExerciseService;
import de.othr.fitnessapp.service.WorkoutServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class WorkoutServiceImpl implements WorkoutServiceI {
    private WorkoutRepository workoutRepository;
    private UserRepository userRepository;
    private WorkoutExerciseRepository workoutExerciseRepository;
    private ExerciseService exerciseService;

    @Override
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public void addWorkoutToUser(Workout workout) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Baseuser user = userRepository.findByLoginIgnoreCase(currentUsername).orElse(null);

        workout.setBaseuser(user);
        workoutRepository.save(workout);
    }

    @Override
    public Workout updateWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public Workout getWorkoutById(Long id) throws NullPointerException {
        Optional<Workout> workout = workoutRepository.findById(id);
        if (workout.isPresent()) {
            return workout.get();
        } else {
            log.error("Workout with ID {} does not exist!", id);
            throw new NullPointerException("Workout with ID " + id + " does not exist");
        }
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public void deleteWorkoutById(Long id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public long getWorkoutCount() {
        return workoutRepository.count();
    }

    @Override
    public int calculateTotalVolume(Long workoutId) {
        List<WorkoutExercise> exercises = workoutExerciseRepository.findByWorkoutId(workoutId);
        return exercises.stream()
                .mapToInt(exercise -> exercise.getActualRepetitions() * exercise.getWeight())
                .sum();
    }

    @Override
    public int getWeeklyWorkoutCount(Baseuser baseuser, LocalDate startDate, LocalDate endDate) {
        return workoutRepository.countByBaseuserAndDateBetween(baseuser, startDate, endDate);
    }

    @Override
    public List<Workout> findAllByUserAndDateBetween(Baseuser user, LocalDate startDate, LocalDate endDate) {
        return workoutRepository.findAllByBaseuserAndDateBetween(user, startDate, endDate);
    }

    @Override
    public Map<String, Object> getMuscleUse(List<Workout> workouts) {

        Map<String, Integer> muscleUsage = new HashMap<>();
        Set<String> usedMuscles = new HashSet<>();

        for (Workout workout : workouts) {
            for (WorkoutExercise we : workout.getWorkoutExercises()) {
                Exercise exercise = we.getExercise();
                exercise.getPrimaryMuscles()
                        .forEach(muscle -> muscleUsage.put(muscle, muscleUsage.getOrDefault(muscle, 0) + 1));
                exercise.getSecondaryMuscles()
                        .forEach(muscle -> muscleUsage.put(muscle, muscleUsage.getOrDefault(muscle, 0) + 1));
            }
        }

        // Extract all used muscles
        Set<String> allMuscles = exerciseService.getAllMuscles();
        Set<String> unusedMuscles = allMuscles;
        unusedMuscles.removeAll(usedMuscles);

        List<String> mostUsedMuscles = muscleUsage.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Find the most used muscles
        Map<String, Object> results = new HashMap<>();
        results.put("mostUsedMuscles", mostUsedMuscles);
        results.put("unusedMuscles", unusedMuscles);
        results.put("allMuscles", allMuscles);

        // Return both most used and unused muscles
        return results;

    }

    @Override
    public List<Exercise> getRecommendedExercises(List<Workout> workouts) {
        Map<String, Object> muscleUseStats = getMuscleUse(workouts);
        Set<String> underutilizedMuscles = (Set<String>) muscleUseStats.get("unusedMuscles");
        List<Exercise> allExercises = exerciseService.findAll();

        Map<Exercise, Integer> exerciseRanking = new HashMap<>();
        for (Exercise exercise : allExercises) {
            int rank = calculateRank(exercise, underutilizedMuscles);
            exerciseRanking.put(exercise, rank);
        }

        return exerciseRanking.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private int calculateRank(Exercise exercise, Set<String> underutilizedMuscles) {
        int score = 0;
    
        // Score primary muscles
        for (String muscle : exercise.getPrimaryMuscles()) {
            if (underutilizedMuscles.contains(muscle)) {
                score++;
            }
        }
    
        // Score secondary muscles
        for (String muscle : exercise.getSecondaryMuscles()) {
            if (underutilizedMuscles.contains(muscle)) {
                score++;
            }
        }
    
        // Invert the score as a lower score is better
        // This is because a lower score means fewer used muscles are involved
        return -score;
    }

}
