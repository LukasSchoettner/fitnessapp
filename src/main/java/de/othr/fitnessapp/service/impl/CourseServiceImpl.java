package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.repository.CourseRepository;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.CustomerServiceI;
import de.othr.fitnessapp.service.TrainerServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Log4j2
@AllArgsConstructor
public class CourseServiceImpl implements CourseServiceI {
    private CourseRepository courseRepository;
    private CustomerServiceI customerService;

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
    public Page<Course> getAllCoursesByTrainerId(Pageable pageable, Long trainerId) {
        return courseRepository.findByTrainerIdAndDateGreaterThanEqualOrderByDateAsc(trainerId, LocalDate.now(), pageable);
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
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
    public Page<Course> getPastCoursesByTrainerId(Pageable pageable, Long trainerId) {
        return courseRepository.findByTrainerIdAndDateLessThanOrderByDateDesc(trainerId, LocalDate.now(), pageable);
    }

    @Override
    public Page<Course> getCoursesWhereCustomerIsNotRegistered(Pageable pageable, Customer customer) {
        return courseRepository.findByParticipantsNotContaining(customer, pageable);
    }

    @Override
    public Page<Course> getCoursesWhereCustomerIsRegistered(Pageable pageable, Customer customer) {
        return courseRepository.findByParticipantsContaining(customer, pageable);
    }

    @Override
    public Page<Course> getPastCoursesWhereCustomerIsRegistered(Pageable pageable, Customer customer) {
        return courseRepository.findByParticipantsContainingAndDateLessThanOrderByDateDesc(customer, LocalDate.now(), pageable);
    }

    @Override
    public boolean trainerOwnsCourse(Long courseId, Long trainerId) {
        return getCourseById(courseId).getTrainer().getId().equals(trainerId);
    }

    @Override
    public boolean customerIsParticipant(Long courseId, Long customerId) {
        return getCourseById(courseId).getParticipants().stream().map(Customer::getId).anyMatch(id -> id.equals(customerId));
    }

    @Override
    public void registerParticipant(Long courseId, Long customerId) {
        Course course = getCourseById(courseId);
        course.getParticipants().add(customerService.findCustomerById(customerId));
        updateCourse(course);
    }

    @Override
    public void deregisterParticipant(Long courseId, Long customerId) {
        Course course = getCourseById(courseId);
        course.getParticipants().remove(customerService.findCustomerById(customerId));
        updateCourse(course);
    }

    @Override
    public Set<Customer> getParticipantsofCourse(Long courseId) {
        return getCourseById(courseId).getParticipants();
    }

    @Override
    public List<Course> findCoursesByName(String name) {
        return courseRepository.findByNameContainingIgnoreCase(name);
    }
}
