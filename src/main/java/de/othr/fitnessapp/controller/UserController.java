package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.User;
import de.othr.fitnessapp.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;  // Import from jakarta.validation

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Display a list of all users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    // Display the form for creating a new user
    @GetMapping("/add")
    public String createUserForm(Model model, HttpServletRequest request ) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/create";
    }

    // Handle the form submission for creating a new user
    @PostMapping("/add")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "user/create";
        }

        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("added", "Student added!");
        return "redirect:/user/all";
    }

    // Display the form for updating a user
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    // Handle the form submission for updating a user
    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }

        userService.updateUser(id, user);
        return "redirect:/users";
    }

    // Delete a user
    @GetMapping("/delete/{id}")
    public String deleteUser(@Valid @ModelAttribute("user") User user) {
        userService.deleteUser(user);
        return "redirect:/users";
    }
}
