package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseServiceI {
    Course saveCourse(Course course);

    Course updateCourse(Course course);

    Course getCourseById(Long id) throws NullPointerException;

    Page<Course> getAllCourses(Pageable pageable);

    void deleteCourseById(Long id);

    void deleteCourse(Course course);

    Page<Course> getPastCourses(Pageable pageable);

    long getCourseCount();

    List<Course> findCoursesByName(String Name);
}
