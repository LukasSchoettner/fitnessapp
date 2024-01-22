package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.repository.CourseRepository;
import de.othr.fitnessapp.service.CourseServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class CourseServiceImpl implements CourseServiceI {
    private CourseRepository courseRepository;

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
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findByDateGreaterThanEqualOrderByDateDesc(LocalDate.now(), pageable);
    }

    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }

    @Override
    public Page<Course> getPastCourses(Pageable pageable) {
        return courseRepository.findByDateLessThanOrderByDateDesc(LocalDate.now(), pageable);
    }

    @Override
    public long getCourseCount() {
        return courseRepository.count();
    }

    @Override
    public List<Course> findCoursesByName(String name) {
        return courseRepository.findByNameContainingIgnoreCase(name);
    }
}
