package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.service.CustomerService;
import de.othr.fitnessapp.service.CourseService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;
    private CourseService courseService;

    //Display customer details including workouts and courses
    @GetMapping("/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.findCustomerById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + id));
        model.addAttribute("customer", customer);
        model.addAttribute("workouts", customerService.getWorkoutsForCustomer(id));
        model.addAttribute("courses", customerService.getCoursesForCustomer(id));
        return "customer/view";
    }

    //Display enroll form
    @GetMapping("/enroll-course/{id}")
    public String enrollCourseForm(@PathVariable Long id, Model model) {
    Customer customer = customerService.findCustomerById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + id));
    List<Course> availableCourses = courseService.getAllCourses();  // Assuming you have a method to list all courses

    model.addAttribute("customer", customer);
    model.addAttribute("courses", availableCourses);
    return "customer/enroll-course";
    }

    //Handle enroll form submission
    @PostMapping("/enroll-course/{id}")
    public String enrollCustomerInCourse(@PathVariable Long id, @RequestParam Long courseId, RedirectAttributes redirectAttributes) {
    customerService.enrollCustomerInCourse(id, courseId);
    redirectAttributes.addFlashAttribute("message", "Customer enrolled in course successfully!");
    return "redirect:/customer/view/" + id;
    }

    //Display form for adding a workout
    @GetMapping("/add-workout/{id}")
    public String addWorkoutForm(@PathVariable Long id, Model model) {
    Customer customer = customerService.findCustomerById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + id));
    
    model.addAttribute("customer", customer);
    model.addAttribute("workout", new Workout());
    return "customer/add-workout";
    }

    //Handle form submission for adding a workout
    @PostMapping("/add-workout/{id}")
    public String addWorkoutForCustomer(@PathVariable Long id, @ModelAttribute Workout workout, RedirectAttributes redirectAttributes) {
    customerService.addWorkoutForCustomer(id, workout);
    redirectAttributes.addFlashAttribute("message", "Workout added successfully!");
    return "redirect:/customer/view/" + id;
    }


    // Display a list of all customers
    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAllCustomers());
        return "customer/list";
    }

    // Display the form for creating a new customer
    @GetMapping("/add")
    public String createCustomerForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer/create";
    }

    // Handle the form submission for creating a new customer
    @PostMapping("/add")
    public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "customer/create";
        }

        customerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("added", "Customer added!");
        return "redirect:/customer";
    }

    // Display the form for updating a customer
    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + id));
        model.addAttribute("customer", customer);
        return "customer/edit";
    }

    // Handle the form submission for updating a customer
    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, @Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customer/edit";
        }

        customerService.updateCustomer(customer);
        return "redirect:/customer";
    }

    // Delete a customer
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@Valid @ModelAttribute("customer") Customer customer) {
        customerService.deleteCustomer(customer);
        return "redirect:/customer";
    }
    
    // Additional methods for customer-specific operations can be added here

}
