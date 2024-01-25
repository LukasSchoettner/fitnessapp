package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.service.CustomerServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@AllArgsConstructor
@RequestMapping(value = {"/home", "/"})
public class HomeController {
    @GetMapping
    public String showHomeForm(Model model){
        //No authentication checks necessary as the route can only be called up if the logged-in user has the corresponding role in the security config
        //User Ids can be checked and the corresponding data rendered in the corresponding controller, which handles the routes/method of the respective sub-items of the sidebar
        return "home";
    }

    @PostMapping
    public String processHomeForm() {
        return "home";
    }
}
