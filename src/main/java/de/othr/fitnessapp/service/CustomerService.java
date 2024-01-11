package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Workout;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface CustomerService {

    public List<Customer> findAllCustomers();

    // Method to find a customer by ID
    public Optional<Customer> findCustomerById(Long id);

    // Method to enroll a customer in a course
    public void enrollCustomerInCourse(Long customerId, Long courseId);

    // Method to add a workout for a customer
    public void addWorkoutForCustomer(Long customerId, Workout workout);

    // Method to get all workouts for a customer
    public List<Workout> getWorkoutsForCustomer(Long customerId);

    // Method to get all courses a customer is enrolled in
    public Set<Course> getCoursesForCustomer(Long customerId);

    public void saveCustomer(Customer customer);

    public void updateCustomer(Customer customer);

    public void deleteCustomer(Customer customer);

}
