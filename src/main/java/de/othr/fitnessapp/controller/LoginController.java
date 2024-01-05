package de.othr.fitnessapp.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequestMapping(value = "/login")
public class LoginController {

    @GetMapping
    public String showLoginForm() {

        return "login";
    }

    @PostMapping
    public String processLoginForm(@RequestParam String login,
                               @RequestParam (name = "pass") String password) {

        System.out.println("Username: " + login);
        System.out.println("Password: " + password);

        return "home";
    }
}
