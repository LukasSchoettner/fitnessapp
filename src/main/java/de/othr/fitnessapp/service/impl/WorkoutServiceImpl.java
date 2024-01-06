package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.repository.WorkoutRepositoryI;
import de.othr.fitnessapp.service.WorkoutServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class WorkoutServiceImpl implements WorkoutServiceI {
    private WorkoutRepositoryI workoutRepository;

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
    public Page<Workout> getAllWorkouts(Pageable pageable) {
        return workoutRepository.findAll(pageable);
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
