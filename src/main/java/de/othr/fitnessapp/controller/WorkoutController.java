package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.CustomerServiceI;
import de.othr.fitnessapp.service.WorkoutServiceI;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping(value = "/workout")
@AllArgsConstructor
public class WorkoutController {
    private WorkoutServiceI workoutService;
    private CourseServiceI courseService;
    private CustomerServiceI customerService;
    //private TrainerServiceI trainerService;
    //private UserServiceI UserService;
    //private GymServiceI GymService;

    @GetMapping(value = "/add")
    public String showWorkoutAddForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        System.out.println(currentUsername);

        Workout workout = new Workout();
        workout.getExercises().add(new Exercise());
        model.addAttribute("workout", workout);
        return "workout/workout-add-form";
    }

    @PostMapping(value = "/add")
    public String processWorkoutAddForm(@ModelAttribute @Valid Workout workout, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/workout/workout-add-form";
        }

        workoutService.addWorkoutToUser(workout);
        //log.info("Saved Workout with ID: {}", savedWorkout.getId());
        redirectAttributes.addFlashAttribute("added", "Workout added!");
        return "redirect:/workout/all";
    }

    @GetMapping(value = "/update/{id}")
    public String showWorkoutUpdateForm(@PathVariable Long id, Model model){
        Workout workout = workoutService.getWorkoutById(id);
        log.info("Updating Workout with ID: {}", workout.getId());
        model.addAttribute("workout", workout);
        return "/workout/workout-update-form";
    }

    @PostMapping(value = "/update")
    public String processWorkoutUpdateForm( @ModelAttribute @Valid Workout workout, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/workout/workout-update-form";
        }

        //Workout existingWorkout = workoutService.getWorkoutById(workout.getId());
        //existingWorkout.setName(workout.getName());
        //existingWorkout.setDate(workout.getDate());

        workoutService.updateWorkout(workout);
        log.info("Updated Workout with ID: {}", workout.getId());
        redirectAttributes.addFlashAttribute("updated", "Workout updated!");
        return "redirect:/workouts/all";
    }

    @GetMapping(value = "/delete/{id}")
    public String processWorkoutDeleteForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Workout workout = workoutService.getWorkoutById(id);
        workoutService.deleteWorkoutById(workout.getId());
        log.info("Deleted Workout with ID: {}", id);
        redirectAttributes.addFlashAttribute("deleted", "Workout deleted!");
        return "redirect:/workouts/all";
    }

    @GetMapping(value = "/all")
    public String showWorkoutList(Model model) {
        model.addAttribute("workouts", workoutService.getAllWorkouts());
        return "/workout/workout-all";
    }

    @GetMapping(value = "/history")
    public String showWorkoutHistory(Model model) {
        model.addAttribute("workouts", workoutService.getAllWorkouts());
        return "workout/workout-all";
    }

    //TODO: list Workout on Courses
    //TODO: delete Workout from Course
    //TODO: add Workout to Course ...
}
