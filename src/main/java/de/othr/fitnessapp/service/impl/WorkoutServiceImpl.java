package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.repository.WorkoutRepositoryI;
import de.othr.fitnessapp.service.WorkoutServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return workoutRepository.findById(id).orElseThrow(() -> {
            log.error("Workout with ID {} does not exist!", id);
            return new NullPointerException("Workout with ID " + id + " does not exist");
        });

    }

    @Override
    public Page<Workout> getAllWorkouts(Pageable pageable) {
        return workoutRepository.findAll(pageable);
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
    public void deleteWorkout(Workout workout) {
        workoutRepository.delete(workout);
    }

    @Override
    public void deleteAllWorkouts() {
        workoutRepository.deleteAll();
    }

    @Override
    public long getWorkoutCount() {
        return workoutRepository.count();
    }
}
