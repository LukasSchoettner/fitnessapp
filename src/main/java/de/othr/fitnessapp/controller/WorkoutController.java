package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Baseuser;
import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.model.WorkoutExercise;
import de.othr.fitnessapp.repository.UserRepository;
import de.othr.fitnessapp.service.BaseuserService;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.CustomerServiceI;
import de.othr.fitnessapp.service.ExerciseService;
import de.othr.fitnessapp.service.WorkoutServiceI;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    private BaseuserService baseuserService;
    // private GymServiceI GymService;

    @GetMapping(value = "/add")
    public String showWorkoutAddForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Baseuser user = baseuserService.findByLoginIgnoreCase(currentUsername);

        LocalDate endDate = LocalDate.now();
        LocalDate oneWeek = endDate.minusWeeks(1);

        List<Workout> workoutsLastWeek = workoutService.findAllByUserAndDateBetween(user, oneWeek, endDate);
        List<Exercise> recommendedExercises = workoutService.getRecommendedExercises(workoutsLastWeek);
        
        Workout workout = new Workout();
        model.addAttribute("workout", workout);
        model.addAttribute("recommendedExercises", recommendedExercises);

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))) {
            return "customer/customer-add-workout";
        } else {
            return "workout/workout-add-form";
        }
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
                Integer weight = workout.getWeight().get(i);
                
                System.out.println(exerciseId);
                System.out.println(repetition);

                WorkoutExercise exercise = new WorkoutExercise();
                exercise.setExercise(exerciseService.findById(exerciseId)); // Find the Exercise entity by ID
                exercise.setRecommendedRepetitions(repetition);
                exercise.setWeight(weight);
                exercise.setWorkout(workout);
                workout.getWorkoutExercises().add(exercise);
            }
        }

        workoutService.addWorkoutToUser(workout);
        redirectAttributes.addFlashAttribute("added", "Workout added!");
        return "redirect:/workout/all";
    }

    @GetMapping(value = "/note")
    public String showWorkoutNoteForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Workout workout = new Workout();
        model.addAttribute("workout", workout);

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))) {
            return "customer/customer-workout-notes";
        } else {
            return "workout/workout-add-form";
        }
    }

    @PostMapping(value = "/note")
    public String processWorkoutNoteForm(@ModelAttribute @Valid Workout workout, BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/customer/customer-workout-notes";
        }

        if (workout.getExerciseIds() != null && workout.getRepetitions() != null) {
            for (int i = 0; i < workout.getExerciseIds().size(); i++) {
                Long exerciseId = workout.getExerciseIds().get(i);
                Integer repetition = workout.getRepetitions().get(i);
                Integer weight = workout.getWeight().get(i);

                WorkoutExercise exercise = new WorkoutExercise();
                exercise.setExercise(exerciseService.findById(exerciseId)); // Find the Exercise entity by ID
                exercise.setActualRepetitions(repetition);
                exercise.setWeight(weight);
                exercise.setWorkout(workout);
                workout.getWorkoutExercises().add(exercise);
            }
        }

        workoutService.addWorkoutToUser(workout);
        redirectAttributes.addFlashAttribute("added", "Workout added!");
        return "redirect:/workout/all";
    }

    @GetMapping(value = "/search-exercises")
    public ResponseEntity<?> searchExercises(@RequestParam String query) {
        // Perform search based on query (name, primaryMuscles, type, etc.)
        List<Exercise> exercises = exerciseService.searchExercises(query);

        // Return partial HTML or JSON
        return ResponseEntity.ok(exercises);
    }

    @GetMapping(value = "/attempt")
    public String attemptWorkout(Model model) {
        return "workout/workout-attempt";
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
        return "redirect:/workout/all";
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

    @GetMapping(value = "/statistics")
    public String showWorkoutStatistics(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Baseuser user = baseuserService.findByLoginIgnoreCase(currentUsername);

        LocalDate endDate = LocalDate.now();
        LocalDate oneWeek = endDate.minusWeeks(1);
        LocalDate oneMonth = endDate.minusMonths(1);

        // Assuming you have a method in your service to get workouts for a date range
        List<Workout> workoutsLastWeek = workoutService.findAllByUserAndDateBetween(user, oneWeek, endDate);
        List<Workout> workoutsLastMonth = workoutService.findAllByUserAndDateBetween(user, oneMonth, endDate);

        // Calculate total weight lifted for each workout
        Map<String, Object> muscleUseLastWeek = workoutService.getMuscleUse(workoutsLastWeek);
        Map<String, Object> muscleUseLastMonth = workoutService.getMuscleUse(workoutsLastMonth);

        model.addAttribute("muscleUseLastWeek", muscleUseLastWeek);
        model.addAttribute("muscleUseLastMonth", muscleUseLastMonth);
        return "workout/workout-view-statistics";
    }

    @GetMapping("/recommendations")
    public String showExerciseRecommendations(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Baseuser user = baseuserService.findByLoginIgnoreCase(currentUsername);

        LocalDate endDate = LocalDate.now();
        LocalDate oneWeek = endDate.minusWeeks(1);

        List<Workout> workoutsLastWeek = workoutService.findAllByUserAndDateBetween(user, oneWeek, endDate);
        List<Exercise> recommendedExercises = workoutService.getRecommendedExercises(workoutsLastWeek);

        model.addAttribute("recommendedExercises", recommendedExercises);
        return "workout/workout-get-recommendations";
    }

    // TODO: list Workout on Courses
    // TODO: delete Workout from Course
    // TODO: add Workout to Course ...
}
