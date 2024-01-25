package de.othr.fitnessapp.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.model.WorkoutExercise;
import de.othr.fitnessapp.repository.WorkoutExerciseRepository;
import de.othr.fitnessapp.service.WorkoutExerciseService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WorkoutExerciseServiceImpl implements WorkoutExerciseService {

    private WorkoutExerciseRepository workoutExerciseRepository;

    public void updateActualRepetitions(Long workoutExerciseId, int actualRepetitions) {
        
        Optional<WorkoutExercise> exerciseOptional = workoutExerciseRepository.findById(workoutExerciseId);

        if (exerciseOptional.isPresent()) {
            WorkoutExercise exercise = exerciseOptional.get();
            exercise.setActualRepetitions(actualRepetitions);
            workoutExerciseRepository.save(exercise);
        }

    // Handle the case where the exercise is not found
    }

    public Long getWorkoutIdbyWorkoutExerciseId(Long id) {
        Long workoutId = workoutExerciseRepository.getReferenceById(id).getWorkout().getId();
        System.out.println(workoutId);
        return workoutId;
    }

    
}
