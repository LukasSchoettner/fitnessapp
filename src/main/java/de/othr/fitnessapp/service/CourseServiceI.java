package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Trainer;

import java.util.List;

public interface CourseServiceI {
    Course saveCourse(Course course);

    Course updateCourse(Course course);

    Course getCourseById(Long id) throws NullPointerException;

    List<Course> getAllCourses();

    void deleteCourseById(Long id);

    long getCourseCount();

    List<Course> findCoursesByName(String Name);
}
