package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.CustomerServiceI;
import de.othr.fitnessapp.service.RoleServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

        model.addAttribute("customer", customer);
        request.getSession().setAttribute("customerSession", customer);
        return "customer/create";
    }

    // Handle the form submission for creating a new customer
    @PostMapping("/add")
    public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "customer/register";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        customer.setPassword("{bcrypt}" + passwordEncoder.encode(customer.getPassword()));

        List<Role> roles = customer.getRoles();
        roles.add(roleService.findRoleByDescription("CUSTOMER"));
        customer.setRoles(roles);

        customerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("added", "Customer added!");
        return "redirect:/customer/view";
    }

    // Display the form for updating a customer
    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, Model model, HttpServletRequest request) {

        Customer customer = customerService.findCustomerById(id);
        model.addAttribute("customer", customer);
        request.getSession().setAttribute("customerSession", customer);

        return "customer/customer-edit-profile";
    }

    // Handle the form submission for updating a customer
    @PostMapping("/edit/process")
    public String editCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customer/customer-edit-profile";
        }

        customerService.updateCustomer(customer.getId(), customer);
        return "redirect:/customer/view";
    }

    // Delete a customer
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@Valid @ModelAttribute("customer") Customer customer) {
        customerService.deleteCustomer(customer);
        return "redirect:/customer";
    }

    // Display customer details including workouts and courses
    @GetMapping("/profile/{id}")
    public String viewCustomer(@PathVariable Long id, Model model, HttpServletRequest request) {

        // Retrieve the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        System.out.println("current user name = " + currentUsername);
        // Optional: Check if the user has specific authority
        // boolean hasAuthority = authentication.getAuthorities().contains(new
        // SimpleGrantedAuthority("ROLE_ADMIN"));

        // Get the Customer object
        Customer customer = customerService.findCustomerById(id);
        System.out.println("user name = " + customer.getLogin());
        // Security check: Ensure the authenticated user is allowed to view this profile
        // This could be a check against roles or specific business logic
        if (currentUsername.equals(customer.getLogin())) {
            model.addAttribute("customer", customer);
            model.addAttribute("workouts", customerService.getWorkoutsForCustomer(id));
            model.addAttribute("courses", customerService.getCoursesForCustomer(id));
            return "customer/customer-view";
        } else {
            // Handle unauthorized access, could redirect to a 'denied' page or similar
            return "error/access-denied";
        }
    }

    // Display a list of all customers
    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAllCustomers());
        return "customer/list";
    }

    // Additional methods for customer-specific operations can be added here
}
