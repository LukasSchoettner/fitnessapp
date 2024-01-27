package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.config.MyUserDetails;
import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.CustomerServiceI;
import de.othr.fitnessapp.service.RoleServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import de.othr.fitnessapp.service.impl.MyUserDetailsServiceImpl;
import de.othr.fitnessapp.service.CourseServiceI;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private CustomerServiceI customerService;
    private RoleServiceI roleService;

    public CustomerController(CustomerServiceI customerService, CourseServiceI courseService,
            RoleServiceI roleService) {
        super();
        this.customerService = customerService;
        this.roleService = roleService;
    }

    // Display the form for creating a new customer
    @GetMapping("/add")
    public String createCustomerForm(Model model, HttpServletRequest request) {
        Customer customer = new Customer();

        List<Role> roles = customer.getRoles();
        roles.add(roleService.findRoleByDescription("CUSTOMER"));
        customer.setRoles(roles);

        model.addAttribute("customer", customer);
        request.getSession().setAttribute("customerSession", customer);
        
        return "customer/customer-add";
    }

    // Handle the form submission for creating a new customer
    @PostMapping("/add")
    public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "customer/customer-add";
        }

        customerService.addCustomer(customer);
        redirectAttributes.addFlashAttribute("added", "Customer added!");

        return "redirect:/customer/profile";
    }

    // Display the form for updating a customer
    @GetMapping("/edit")
    public String editCustomerForm(Model model, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Customer customer = customerService.findCustomerByLogin(currentUsername);

        model.addAttribute("customer", customer);
        //request.getSession().setAttribute("customerSession", customer);

        return "customer/customer-edit-profile";
    }

    // Handle the form submission for updating a customer
    @PostMapping("/edit")
    public String editCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "customer/customer-edit-profile";
        }

        customerService.updateCustomer(customer);
        return "redirect:/customer/profile";
    
    }

    // Delete a customer
    @PostMapping("/delete")
    public String deleteCustomer() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Customer customer = customerService.findCustomerByLogin(currentUsername);

        customerService.deleteCustomer(customer);
        return "redirect:/register";
    }

    // Display customer details including workouts and courses
    @GetMapping("/profile")
    public String viewCustomer(Model model, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Customer customer = customerService.findCustomerByLogin(currentUsername);

        // Security check: Ensure the authenticated user is allowed to view this profile
        // This could be a check against roles or specific business logic

        model.addAttribute("customer", customer);
        model.addAttribute("workouts", customerService.getWorkoutsForCustomer(customer.getId()));
        model.addAttribute("courses", customerService.getCoursesForCustomer(customer.getId()));
        return "customer/customer-view";

    }

    // Display a list of all customers
    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAllCustomers());
        return "customer/list";
    }

    // Additional methods for customer-specific operations can be added here
}
