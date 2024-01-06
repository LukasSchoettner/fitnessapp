package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.WorkoutServiceI;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
@RequestMapping(value = "/workout")
@AllArgsConstructor
public class WorkoutController {
    private WorkoutServiceI workoutService;
    private CourseServiceI courseService;
    //private TrainerServiceI trainerService;
    //private UserServiceI UserService;
    //private GymServiceI GymService;

    @GetMapping(value = "/add")
    public String showWorkoutAddForm(Model model) {
        Workout workout = new Workout();
        model.addAttribute("workout", workout);
        return "/workout/workout-add-form";
    }

    @PostMapping(value = "/add")
    public String processWorkoutAddForm(@ModelAttribute @Valid Workout workout, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/workout/workout-add-form";
        }

        Workout savedWorkout = workoutService.saveWorkout(workout);
        log.info("Saved Workout with ID: {}", savedWorkout.getId());
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

        Workout existingWorkout = workoutService.getWorkoutById(workout.getId());
        existingWorkout.setName(workout.getName());
        existingWorkout.setDate(workout.getDate());
        existingWorkout.setLevel(workout.getLevel());

        workoutService.updateWorkout(existingWorkout);
        log.info("Updated Workout with ID: {}", workout.getId());
        redirectAttributes.addFlashAttribute("updated", "Workout updated!");
        return "redirect:/workout/all";
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
    public String showWorkoutList(Model model,@RequestParam(required = false) String keyword,
                                              @RequestParam(required = false, defaultValue = "1") int page,
                                              @RequestParam(required = false, defaultValue = "3") int size) {

        try {
            Pageable paging = PageRequest.of(page - 1, size);

            Page<Workout> pageWorkouts = workoutService.getAllWorkouts(paging);

            //model.addAttribute("keyword", keyword);

            List<Workout> workouts = pageWorkouts.getContent();

            model.addAttribute("workouts", workouts);
            model.addAttribute("currentPage", pageWorkouts.getNumber() + 1);
            model.addAttribute("totalItems", pageWorkouts.getTotalElements());
            model.addAttribute("totalPages", pageWorkouts.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("entityContext", "workout/all");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "/workout/workout-all";
    }

    @GetMapping(value = "/{id}/exercise/add")
    public String showExerciseAddForm(@PathVariable Long id, Model model) {
        Workout workout = workoutService.getWorkoutById(id);
        model.addAttribute("workout", workout);
        model.addAttribute("exercise", new Exercise());
        return "/workout/exercise/exercise-add-form";
    }

    @PostMapping(value = "/{id}/exercise/add")
    public String processExerciseAddForm(@PathVariable Long id, @ModelAttribute @Valid Exercise exercise, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        Workout workout = workoutService.getWorkoutById(id);

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            model.addAttribute("workout", workout);
            return "/workout/exercise/exercise-add-form";
        }

        workout.addExercise(exercise);
        workoutService.saveWorkout(workout);
        log.info("Saved Exercise on Workout with ID: {}", workout.getId());
        redirectAttributes.addFlashAttribute("added", "Exercise added!");
        return "redirect:/workout/all";
    }

    @GetMapping(value = "/{workoutId}/exercise/{exerciseId}/delete")
    public String processExerciseDeleteForm(@PathVariable("workoutId") Long workoutId, @PathVariable("exerciseId") Long exerciseId, Model model, RedirectAttributes redirectAttributes ) {
        Workout workout = workoutService.getWorkoutById(workoutId);
        workout.removeExercise(workout.getExerciseById(exerciseId));
        workoutService.updateWorkout(workout);

        log.info("Deleted Exercise with ID {} from Workout with ID {}", exerciseId, workoutId);
        redirectAttributes.addFlashAttribute("deleted", "Exercise deleted!");
        return "redirect:/workout/all";
    }

    @PostMapping(value = "/{workoutId}/exercise/{exerciseId}/update")
    public String showExerciseUpdateForm(@PathVariable("workoutId") Long workoutId, @PathVariable("exerciseId") Long exerciseId, @ModelAttribute @Valid Exercise exercise, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        Workout workout = workoutService.getWorkoutById(workoutId);

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            model.addAttribute("workout", workout);
            return "/workout/exercise/exercise-update-form";
        }

        workout.updateExercise(workout.getExerciseById(exerciseId), exercise);
        workoutService.updateWorkout(workout);
        log.info("Updated Exercise with ID {} from Workout with ID: {}", exerciseId, workout.getId());
        redirectAttributes.addFlashAttribute("updated", "Exercise updated!");
        return "redirect:/workout/all";
    }

    @GetMapping(value = "/{workoutId}/exercise/{exerciseId}/update")
    public String showExerciseUpdateForm(@PathVariable("workoutId") Long workoutId, @PathVariable("exerciseId") Long exerciseId, Model model){
        Workout workout = workoutService.getWorkoutById(workoutId);
        Exercise exercise = workout.getExerciseById(exerciseId);

        log.info("Updating Exercise with ID {} from Workout with ID: {}", exerciseId, workout.getId());
        model.addAttribute("workout", workout);
        model.addAttribute("exercise", exercise);
        return "/workout/exercise/exercise-update-form";
    }

    @GetMapping(value = "/history")
    public String showWorkoutHistory(Model model) {
        //model.addAttribute("workouts", workoutService.getAllWorkouts());
        return "workout/workout-all";
    }

    //TODO: list Workout on Courses
    //TODO: delete Workout from Course
    //TODO: add Workout to Course ...
}
