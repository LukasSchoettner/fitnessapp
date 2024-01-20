package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Workout;

import java.util.List;
import java.util.Set;

public interface CustomerServiceI {

    public List<Customer> findAllCustomers();

    public Customer findCustomerByLogin(String login);

    // Method to find a customer by ID
    public Customer findCustomerById(Long id);

    // Method to enroll a customer in a course
    public void enrollCustomerInCourse(Long customerId, Long courseId);

    // Method to add a workout for a customer
    public void addWorkoutForCustomer(Long customerId, Workout workout);

    // Method to get all workouts for a customer
    public List<Workout> getWorkoutsForCustomer(Long customerId);

    // Method to get all courses a customer is enrolled in
    public Set<Course> getCoursesForCustomer(Long customerId);

    public void saveCustomer(Customer customer);

    public void updateCustomer(Long id, Customer customer);

    public void deleteCustomer(Customer customer);

}
