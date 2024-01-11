package de.othr.fitnessapp.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping(value = "/home")
public class HomeController {
    @GetMapping
    public String showHomeForm(){
        return "home";
    }

    @PostMapping
    public String processHomeForm() {
        return "home";
    }
}
