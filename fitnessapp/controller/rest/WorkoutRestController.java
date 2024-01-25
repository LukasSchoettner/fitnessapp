package de.othr.fitnessapp.controller.rest;

import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.service.WorkoutServiceI;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
@Log4j2
@AllArgsConstructor
public class WorkoutRestController {
    private WorkoutServiceI workoutService;

    @GetMapping("/users/{userId}/workouts/all")
    public ResponseEntity <CollectionModel<EntityModel<Workout>>> getAllWorkouts(@PathVariable(name = "userId") Long userId) {

        //TODO: Get users workouts
        List<Workout> workouts;
        try {
            workouts = workoutService.getAllWorkouts();
        } catch (NullPointerException e) {
            log.error("User with ID {} does not exist!", userId);
            return ResponseEntity.notFound().build();
        }

        if (workouts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Workout>> workoutModels = new ArrayList<>();
        workouts.forEach(workout -> {
            EntityModel<Workout> entityModel = EntityModel.of(workout);
            Link workoutLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WorkoutRestController.class)
                                                                            .getWorkout(userId, workout.getId())).withSelfRel();

            entityModel.add(workoutLink);
            workoutModels.add(entityModel);
        });

        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WorkoutRestController.class)
                                                                .getAllWorkouts(userId)).withSelfRel();

        return new ResponseEntity<>(CollectionModel.of(workoutModels, link), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/workouts/{workoutId}")
    ResponseEntity<EntityModel<Workout>> getWorkout(@PathVariable(name = "userId") Long userId,
                                                    @PathVariable(name = "workoutId") long workoutId) {

        //TODO: Get user workout
        Workout workout;
        try {
            workout  = workoutService.getWorkoutById(workoutId);
        } catch (NullPointerException e) {
            log.error("User with ID {} or Workout with ID {} does not exist!", userId, workoutId);
            return ResponseEntity.noContent().build();
        }

        EntityModel<Workout> entityModel = EntityModel.of(workout);

        Link workoutLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WorkoutRestController.class)
                                                                        .getWorkout(userId, workout.getId())).withSelfRel();

        entityModel.add(workoutLink);

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/workouts/{workoutId}")
    public ResponseEntity<EntityModel<Workout>> updateWorkout(@PathVariable(name = "userId") Long userId,
                                                              @PathVariable(name = "workoutId") Long workoutId,
                                                              @Valid @RequestBody Workout workoutRequest,
                                                              BindingResult result) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return ResponseEntity.badRequest().build();
        }

        //TODO: Update user workout
        Workout existingWorkout;
        try {
            existingWorkout = workoutService.getWorkoutById(workoutId);
        } catch (NullPointerException e) {
            log.error("User with ID {} or Workout with ID {} does not exist!", userId, workoutId);
            return ResponseEntity.notFound().build();
        }

        Workout workout = workoutService.updateWorkout(workoutRequest);
        EntityModel<Workout> entityModel = EntityModel.of(workout);

        Link workoutLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WorkoutRestController.class)
                                                                        .getWorkout(userId, workout.getId())).withSelfRel();

        entityModel.add(workoutLink);

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/workouts")
    public ResponseEntity<EntityModel<Workout>> createWorkout(@PathVariable(name = "userId") Long userId,
                                                              @Valid @RequestBody Workout workoutRequest, BindingResult result) {

        //TODO: Create user workout
        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return ResponseEntity.badRequest().build();
        }

        Workout workout = workoutService.saveWorkout(workoutRequest);
        EntityModel<Workout> entityModel = EntityModel.of(workout);

        Link workoutLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WorkoutRestController.class)
                                                                        .getWorkout(userId, workout.getId())).withSelfRel();

        entityModel.add(workoutLink);

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{userId}/workouts/{workoutId}")
    ResponseEntity<?> deleteWorkout(@PathVariable(name = "userId") Long userId,
                                    @PathVariable(name = "workoutId") Long workoutId) {

        //TODO: Delete user workout
        try {
            workoutService.deleteWorkout(workoutService.getWorkoutById(workoutId));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{userId}/workouts")
    ResponseEntity<?> deleteAllWorkouts(@PathVariable(name = "userId") Long userId) {

        //TODO: Delete all user workouts
        try {
            workoutService.deleteAllWorkouts();
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}
