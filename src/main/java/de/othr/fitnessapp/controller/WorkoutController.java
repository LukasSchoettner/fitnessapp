package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.CustomerServiceI;
import de.othr.fitnessapp.service.TrainerServiceI;
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

@Controller
@Log4j2
@RequestMapping(value = "/workout")
@AllArgsConstructor
public class WorkoutController {
    private WorkoutServiceI workoutService;
    private CourseServiceI courseService;
    private TrainerServiceI trainerService;
    private CustomerServiceI UserService;

    @GetMapping(value = "/add")
    public String showWorkoutAddForm(@RequestParam(required = false) String lang, Model model) {

        Workout workout = new Workout();
        model.addAttribute("workout", workout);
        model.addAttribute("EN", "/workout/add");
        model.addAttribute("DE", "/workout/add?lang=de");

        if (lang != null) {
            model.addAttribute("de", "de");
        }
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
        //TODO: Add Workout to User/Trainer/Gym and save back
        log.info("Saved Workout with ID: {}", savedWorkout.getId());
        redirectAttributes.addFlashAttribute("added", "Workout added!");
        return "redirect:/workout/all";
    }

    @GetMapping(value = "/update/{id}")
    public String showWorkoutUpdateForm(@PathVariable Long id, @RequestParam(required = false) String lang, Model model) {

        Workout workout = workoutService.getWorkoutById(id);
        log.info("Updating Workout with ID: {}", workout.getId());
        model.addAttribute("workout", workout);
        model.addAttribute("EN", "/workout/update/"+id);
        model.addAttribute("DE", "/workout/add/"+id+"?lang=de");

        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "/workout/workout-update-form";
    }

    @PostMapping(value = "/update")
    public String processWorkoutUpdateForm(@ModelAttribute @Valid Workout workout, BindingResult result, RedirectAttributes redirectAttributes) {

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
    public String processWorkoutDeleteForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Workout workout = workoutService.getWorkoutById(id);
        workoutService.deleteWorkoutById(workout.getId());
        //TODO: Remove the Workout from Trainer/Gym/Customer and save back
        log.info("Deleted Workout with ID: {}", id);
        redirectAttributes.addFlashAttribute("deleted", "Workout deleted!");
        return "redirect:/workout/all";
    }

    @GetMapping(value = "/all")
    public String showWorkoutList(Model model,@RequestParam(required = false) String keyword,
                                                @RequestParam(required = false, defaultValue = "1") int page,
                                                @RequestParam(required = false, defaultValue = "3") int size,
                                                @RequestParam(required = false) String lang) {

            Pageable paging = PageRequest.of(page - 1, size);
            //TODO: Change to the personal workouts
            Page<Workout> pageWorkouts = workoutService.getAllWorkouts(paging);
            //model.addAttribute("keyword", keyword);

            fillPaginationView(model, size, pageWorkouts);
            model.addAttribute("EN", "/workout/all");
            model.addAttribute("DE", "/workout/all?lang=de");

        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "/workout/workout-all";
    }

    @GetMapping(value = "/{id}/exercise/add")
    public String showExerciseAddForm(@PathVariable Long id, @RequestParam(required = false) String lang, Model model) {

        Workout workout = workoutService.getWorkoutById(id);
        model.addAttribute("workout", workout);
        model.addAttribute("exercise", new Exercise());
        model.addAttribute("EN", "/workout/"+id+"/exercise/add");
        model.addAttribute("DE", "/workout/"+id+"/exercise/add?lang=de");

        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "/workout/exercise/exercise-add-form";
    }

    @PostMapping(value = "/{id}/exercise/add")
    public String processExerciseAddForm(@PathVariable Long id, @ModelAttribute @Valid Exercise exercise, Model model,
                                         BindingResult result, RedirectAttributes redirectAttributes) {

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

    @GetMapping(value = "/{id}/exercise/delete/{exerciseId}")
    public String processExerciseDeleteForm(@PathVariable Long id, @PathVariable("exerciseId") Long exerciseId,
                                            @RequestParam(required = false) String lang, Model model, RedirectAttributes redirectAttributes ) {

        Workout workout = workoutService.getWorkoutById(id);
        workout.removeExercise(workout.getExerciseById(exerciseId));
        workoutService.updateWorkout(workout);

        log.info("Deleted Exercise with ID {} from Workout with ID {}", exerciseId, id);
        redirectAttributes.addFlashAttribute("deleted", "Exercise deleted!");

        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "redirect:/workout/all";
    }

    @PostMapping(value = "/{id}/exercise/update/{exerciseId}")
    public String showExerciseUpdateForm(@PathVariable Long id, @PathVariable("exerciseId") Long exerciseId,
                                         @ModelAttribute @Valid Exercise exercise, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        Workout workout = workoutService.getWorkoutById(id);

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

    @GetMapping(value = "/{id}/exercise/update/{exerciseId}")
    public String showExerciseUpdateForm(@PathVariable Long id, @PathVariable("exerciseId") Long exerciseId,
                                         @RequestParam(required = false) String lang, Model model){

        Workout workout = workoutService.getWorkoutById(id);
        Exercise exercise = workout.getExerciseById(exerciseId);

        log.info("Updating Exercise with ID {} from Workout with ID: {}", exerciseId, workout.getId());
        model.addAttribute("workout", workout);
        model.addAttribute("exercise", exercise);
        model.addAttribute("EN", "/workout/"+id+"/exercise/update/"+exerciseId);
        model.addAttribute("DE", "/workout/"+id+"/exercise/update/"+exerciseId+"?lang=de");

        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "/workout/exercise/exercise-update-form";
    }

    private void fillPaginationView(Model model, int size, Page<Workout> pageWorkouts) {
        model.addAttribute("workouts", pageWorkouts.getContent());
        model.addAttribute("currentPage", pageWorkouts.getNumber() + 1);
        model.addAttribute("totalItems", pageWorkouts.getTotalElements());
        model.addAttribute("totalPages", pageWorkouts.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("entityContext", "workout/all");
    }
}
