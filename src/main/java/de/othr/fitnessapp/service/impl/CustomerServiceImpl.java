package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.repository.CustomerRepository;
import de.othr.fitnessapp.repository.CourseRepository;
import de.othr.fitnessapp.repository.WorkoutRepository;
import de.othr.fitnessapp.service.CustomerServiceI;
import de.othr.fitnessapp.service.RoleServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerServiceI{


    private CustomerRepository customerRepository;
    private CourseRepository courseRepository;
    private WorkoutRepository workoutRepository;
    private RoleServiceI roleService;

    public CustomerServiceImpl(CustomerRepository customerRepository, CourseRepository courseRepository, WorkoutRepository workoutRepository, RoleServiceI roleService){
        this.customerRepository = customerRepository;
        this.courseRepository = courseRepository;
        this.workoutRepository = workoutRepository;
        this.roleService = roleService;
    }

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

        customer.getCourses().add(course);
        customerRepository.save(customer);
    }

    // Method to add a workout for a customer
    public void addWorkoutForCustomer(Long customerId, Workout workout) {
        Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        //todo: add customer to workout
        //workout.setCustomer(customer);
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
        
        return customer.getCourses();
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        customer.setPassword("{bcrypt}" + passwordEncoder.encode(customer.getPassword()));

        List<Role> roles = customer.getRoles();
        roles.add(roleService.findRoleByDescription("CUSTOMER"));
        customer.setRoles(roles);
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customerNew){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Customer existingCustomer = customerRepository.findByLogin(currentUsername).orElse(null);
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        
        if (existingCustomer != null) {
            existingCustomer.setEmail(customerNew.getEmail());
            existingCustomer.setPassword("{bcrypt}" + passwordEncoder.encode(customerNew.getPassword()));
            existingCustomer.setLast_name(customerNew.getLast_name());
            existingCustomer.setFirst_name(customerNew.getFirst_name());
        
        customerRepository.save(existingCustomer);
        }
    }

    public void deleteCustomer(Customer customer){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionOwner = authentication.getName();
        Customer existingCustomer = customerRepository.findByLogin(sessionOwner).orElse(null);
        
        if (existingCustomer.equals(customer)) {
            customerRepository.delete(customer);
        }else{
            System.out.println("Not authorized for this action");
        }
        
    }

    @Override
    public Set<Customer> getCustomersNotInCourse(Course course) {
        return customerRepository.findByCoursesNotContaining(course);
    }
}
