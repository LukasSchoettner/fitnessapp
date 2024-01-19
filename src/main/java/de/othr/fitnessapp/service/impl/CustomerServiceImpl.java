package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.repository.CustomerRepository;
import de.othr.fitnessapp.repository.CourseRepository;
import de.othr.fitnessapp.repository.WorkoutRepository;
import de.othr.fitnessapp.service.CustomerServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerServiceI{

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private WorkoutRepository workoutRepository;

    // Method to find a customer by ID
    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }

    public Customer findCustomerByLogin(String login) {
        return customerRepository.findByLogin(login).get();
    }

    // Method to enroll a customer in a course
    public void enrollCustomerInCourse(Long customerId, Long courseId) {
        Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        Course course = courseRepository.findById(courseId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));

        customer.getAttendedCourses().add(course);
        customerRepository.save(customer);
    }

    // Method to add a workout for a customer
    public void addWorkoutForCustomer(Long customerId, Workout workout) {
        Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        workout.setCustomer(customer);
        workoutRepository.save(workout);
    }

    // Method to get all workouts for a customer
    public List<Workout> getWorkoutsForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        
        return customer.getWorkouts();
    }

    // Method to get all courses a customer is enrolled in
    public Set<Course> getCoursesForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        
        return customer.getAttendedCourses();
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public void updateCustomer(Long id, Customer customerNew){
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        
        if (existingCustomer != null) {
            existingCustomer.setEmail(customerNew.getEmail());
            existingCustomer.setPassword(customerNew.getPassword());
            existingCustomer.setLast_name(customerNew.getLast_name());
            existingCustomer.setFirst_name(customerNew.getFirst_name());
        customerRepository.save(existingCustomer);
        }
    }

    public void deleteCustomer(Customer customer){
        customerRepository.delete(customer);
    }

}
