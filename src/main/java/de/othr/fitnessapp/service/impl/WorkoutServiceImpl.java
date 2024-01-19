package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.repository.WorkoutRepository;
import de.othr.fitnessapp.service.WorkoutServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class WorkoutServiceImpl implements WorkoutServiceI {
    private WorkoutRepository workoutRepository;

    @Override
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
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
}