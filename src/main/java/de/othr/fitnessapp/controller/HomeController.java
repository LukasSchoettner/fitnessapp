package de.othr.fitnessapp.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.CustomerServiceI;
import de.othr.fitnessapp.service.RoleServiceI;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Controller
@Log4j2
@RequestMapping(value = {"/home", "/"})
public class HomeController {

    private CustomerServiceI customerService;
    private RoleServiceI roleService;

    public HomeController(CustomerServiceI customerService, RoleServiceI roleService) {
        super();
        this.customerService = customerService;
        this.roleService = roleService;
    }
    @GetMapping
    public String showHomeForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Customer customer = customerService.findCustomerByLogin(authentication.getName());
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))){
            model.addAttribute("customer", customer);
            return "home";
        }else{
            return "home";
        }
            
    }

    @PostMapping
    public String processHomeForm() {
        return "home";
    }
}
