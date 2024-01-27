package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByTrainerIdAndDateLessThanOrderByDateDesc(Long trainerId, LocalDate date, Pageable pageable);

    Page<Course> findByTrainerIdAndDateGreaterThanEqualOrderByDateAsc(Long trainerId, LocalDate date, Pageable pageable);

    Page<Course> findByParticipantsNotContaining(Customer customer, Pageable pageable);

    Page<Course> findByParticipantsContaining(Customer customer, Pageable pageable);

    Page<Course> findByParticipantsContainingAndDateLessThanOrderByDateDesc(Customer customer, LocalDate date, Pageable pageable);

    List<Course> findByNameContainingIgnoreCase (String name);
}