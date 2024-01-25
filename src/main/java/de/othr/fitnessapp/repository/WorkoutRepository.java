package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Baseuser;
import de.othr.fitnessapp.model.Workout;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    int countByBaseuserAndDateBetween(Baseuser baseuser, LocalDate startDate, LocalDate endDate);

    List<Workout> findAllByBaseuserAndDateBetween(Baseuser user, LocalDate startDate, LocalDate endDate);

    List<Workout> findAllByBaseuser(Baseuser baseuser);

    @Query("SELECT w FROM Workout w JOIN w.workoutExercises we WHERE we.actualRepetitions = 0 AND w.baseuser = :baseuser")
    List<Workout> findWorkoutsWithZeroActualRepetitionsByBaseuser(Baseuser baseuser);

}
