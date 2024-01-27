package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CourseServiceI {
    Course saveCourse(Course course);

    Course updateCourse(Course course);

    Course getCourseById(Long id) throws NullPointerException;

    Page<Course> getAllCoursesByTrainerId(Pageable pageable, Long trainerId);

    Page<Course> getAllCourses(Pageable pageable);

    void deleteCourseById(Long id);

    void deleteCourse(Course course);

    Page<Course> getPastCoursesByTrainerId(Pageable pageable, Long trainerId);

    Page<Course> getCoursesWhereCustomerIsNotRegistered(Pageable pageable, Customer customer);

    Page<Course> getCoursesWhereCustomerIsRegistered(Pageable pageable, Customer customer);

    Page<Course> getPastCoursesWhereCustomerIsRegistered(Pageable pageable, Customer customer);

    boolean trainerOwnsCourse(Long courseId, Long trainerId);

    boolean customerIsParticipant(Long courseId, Long customerId);

    void registerParticipant(Long courseId, Long customerId);

    void deregisterParticipant(Long courseId, Long customerId);

    Set<Customer> getParticipantsofCourse(Long courseId);

    List<Course> findCoursesByName(String Name);
}
