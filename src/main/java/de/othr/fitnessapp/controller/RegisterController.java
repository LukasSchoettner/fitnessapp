package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.service.CustomerServiceI;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public String showRegistrationForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "register";
    }

    @PostMapping("/process")
    public String processRegistrationForm(@ModelAttribute @Valid Customer customer, BindingResult result, RedirectAttributes redirectAttributes) {
        
        customerService.saveCustomer(customer);
        redirectAttributes.addAttribute("id", customer.getId());
        // Redirect to a confirmation page or back to home
        return "redirect:/customer/profile/{id}";
    }
}
