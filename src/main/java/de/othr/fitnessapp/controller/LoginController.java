package de.othr.fitnessapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @GetMapping
    public String showLoginForm() {

        return "login";
    }

    @PostMapping(value = "/process")
    public String processLoginForm(@RequestParam String username, @RequestParam String password, @RequestParam String role) {

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("role: " + role);

        if (role == "1") {
            return "redirect:/customer-view";
        }
        else {return "redirect:/home";}

        
    }
}
