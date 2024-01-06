package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.repository.CourseRepositoryI;
import de.othr.fitnessapp.service.CourseServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class CourseServiceImpl implements CourseServiceI {
    private CourseRepositoryI courseRepository;

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(Long id) throws NullPointerException {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            log.error("Course with ID {} does not exist!", id);
            throw new NullPointerException("Course with ID " + id + " does not exist");
        }
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public long getCourseCount() {
        return courseRepository.count();
    }
}
