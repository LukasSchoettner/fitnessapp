package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Baseuser;
import de.othr.fitnessapp.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Page<Workout> findAll(Pageable pageable);

    Page<Workout> findByDateLessThanOrderByDateDesc(LocalDate date, Pageable pageable);

    Page<Workout> findByDateGreaterThanEqualOrderByDateDesc(LocalDate date, Pageable pageable);

    int countByBaseuserAndDateBetween(Baseuser baseuser, LocalDate startDate, LocalDate endDate);

    List<Workout> findAllByBaseuserAndDateBetween(Baseuser user, LocalDate startDate, LocalDate endDate);

    Page<Workout> findAllByBaseuser(Baseuser baseuser, Pageable pageable);

    @Query("SELECT w FROM Workout w JOIN w.workoutExercises we WHERE we.actualRepetitions = 0 AND w.baseuser = :baseuser")
    List<Workout> findWorkoutsWithZeroActualRepetitionsByBaseuser(Baseuser baseuser);

}
