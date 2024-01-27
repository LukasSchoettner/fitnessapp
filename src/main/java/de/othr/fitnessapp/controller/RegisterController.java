package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.CustomerServiceI;
import de.othr.fitnessapp.service.RoleServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private CustomerServiceI customerService;
    private RoleServiceI roleService;

    public RegisterController(CustomerServiceI customerService, RoleServiceI roleService){
        super();
        this.customerService = customerService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showRegistrationForm(Model model, HttpServletRequest request) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        request.getSession().setAttribute("customerSession", customer);
        return "register";
    }

    @PostMapping("/process")
    public String processRegistrationForm(@ModelAttribute @Valid Customer customer, BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        customer.setPassword("{bcrypt}"+passwordEncoder.encode(customer.getPassword()));

        List<Role> roles = customer.getRoles();
        roles.add(roleService.findRoleByDescription("CUSTOMER"));
        customer.setRoles(roles);

        customerService.addCustomer(customer);
        redirectAttributes.addFlashAttribute("added", "Customer added!");
        // Redirect to a confirmation page or back to home
        return "redirect:/customer/profile/{id}";
    }
}
