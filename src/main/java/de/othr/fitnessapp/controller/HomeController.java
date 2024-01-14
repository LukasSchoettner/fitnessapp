package de.othr.fitnessapp.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    @GetMapping
    public String showHome(@RequestParam(required = false) String lang, Model model){

        model.addAttribute("EN", "/home");
        model.addAttribute("DE", "/home?lang=de");
        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "home";
    }

    @PostMapping
    public String processHome() {
        return "home";
    }
}
