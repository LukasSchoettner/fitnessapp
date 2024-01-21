package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.model.WorkoutExercise;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.CustomerServiceI;
import de.othr.fitnessapp.service.ExerciseService;
import de.othr.fitnessapp.service.WorkoutServiceI;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.http.ResponseEntity;
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
    private ExerciseService exerciseService;
    // private TrainerServiceI trainerService;
    // private UserServiceI UserService;
    // private GymServiceI GymService;

    @GetMapping(value = "/add")
    public String showWorkoutAddForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        System.out.println(currentUsername);

        Workout workout = new Workout();
        model.addAttribute("workout", workout);

        List<Exercise> availableExercises = exerciseService.findAll(); // Fetch all exercises
        model.addAttribute("availableExercises", availableExercises);
        return "workout/workout-add-form";
    }

    @PostMapping(value = "/add")
    public String processWorkoutAddForm(@ModelAttribute @Valid Workout workout, BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/workout/workout-add-form";
        }

        if (workout.getExerciseIds() != null && workout.getRepetitions() != null) {
            for (int i = 0; i < workout.getExerciseIds().size(); i++) {
                Long exerciseId = workout.getExerciseIds().get(i);
                Integer repetition = workout.getRepetitions().get(i);

                WorkoutExercise exercise = new WorkoutExercise();
                exercise.setExercise(exerciseService.findById(exerciseId)); // Find the Exercise entity by ID
                exercise.setRecommendedRepetitions(repetition);
                exercise.setWorkout(workout);
                workout.getWorkoutExercises().add(exercise);
            }
        }

        workoutService.addWorkoutToUser(workout);
        redirectAttributes.addFlashAttribute("added", "Workout added!");
        return "redirect:/workout/all";
    }

    @GetMapping("/search-exercises")
    public ResponseEntity<?> searchExercises(@RequestParam String query) {
        // Perform search based on query (name, primaryMuscles, type, etc.)
        List<Exercise> exercises = exerciseService.searchExercises(query);

        // Return partial HTML or JSON
        return ResponseEntity.ok(exercises); // Adjust as needed
    }

    @GetMapping(value = "/update/{id}")
    public String showWorkoutUpdateForm(@PathVariable Long id, Model model) {
        Workout workout = workoutService.getWorkoutById(id);
        log.info("Updating Workout with ID: {}", workout.getId());
        model.addAttribute("workout", workout);
        return "/workout/workout-update-form";
    }

    @PostMapping(value = "/update")
    public String processWorkoutUpdateForm(@ModelAttribute @Valid Workout workout, BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/workout/workout-update-form";
        }

        // Workout existingWorkout = workoutService.getWorkoutById(workout.getId());
        // existingWorkout.setName(workout.getName());
        // existingWorkout.setDate(workout.getDate());

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

    // TODO: list Workout on Courses
    // TODO: delete Workout from Course
    // TODO: add Workout to Course ...
}
