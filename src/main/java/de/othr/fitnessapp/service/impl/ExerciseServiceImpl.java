package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.service.ExerciseService;
import jakarta.annotation.PostConstruct;
import de.othr.fitnessapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private ExerciseRepository exerciseRepository;
    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ObjectMapper objectMapper) {
        this.exerciseRepository = exerciseRepository;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper; // Assuming ObjectMapper is a Bean
    }

    @PostConstruct
    public void init() {
        try {
            fetchAndSaveExercises();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Proper error handling
        }
    }

    public void fetchAndSaveExercises() throws IOException, InterruptedException {
        
        long count = exerciseRepository.count();
        if(count < 50){
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://advanced-exercise-finder.p.rapidapi.com/"))
                .header("x-rapidapi-key", "e8f6cb427cmsh62bdf8138d60c4ep1e2cb7jsn6990a8fb16b4")
                .header("x-rapidapi-host", "advanced-exercise-finder.p.rapidapi.com")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"query\":\"query MyQuery {\\n  allExercises {\\n    name\\n    equipment\\n    movement\\n    instructions\\n    force\\n    primaryMuscleGroups\\n    primaryMuscles\\n    secondaryMuscleGroups\\n    secondaryMuscles\\n    tags\\n    type\\n  }\\n}\"}"))
                .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            try {
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode allExercisesNode = root.path("data").path("allExercises");
        
                if (allExercisesNode.isArray()) {
                    for (JsonNode node : allExercisesNode) {
                        Exercise exercise = objectMapper.treeToValue(node, Exercise.class);
                        // Check if an exercise with the same name already exists
                        if (!exerciseRepository.existsByName(exercise.getName())) {
                            exerciseRepository.save(exercise);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // Or handle the exception as per your application's needs
            }
        }
    }

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @Override
    public Exercise findById(Long id) {
        Optional<Exercise> result = exerciseRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public Exercise findByName(String name){
        Optional<Exercise> result = exerciseRepository.findByName(name);
        return result.get();
    }

    @Override
    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public void deleteById(Long id) {
        exerciseRepository.deleteById(id);
    }
    
    @Override
    public List<Exercise> searchExercises(String query) {
        // Implement the search logic here
        // For example, search by name, primary muscles, or type
        return exerciseRepository.findByNameContainingIgnoreCaseOrPrimaryMusclesContainingIgnoreCaseOrTypeContainingIgnoreCase(query, query, query);
    }

    @Override
    public Set<String> getAllMuscles() {

        List<Exercise> exercises = exerciseRepository.findAll();
        Set<String> allMuscles = new HashSet<>();

        for (Exercise exercise : exercises) {
            allMuscles.addAll(exercise.getPrimaryMuscles());
            allMuscles.addAll(exercise.getSecondaryMuscles());
            // If you want to include muscle groups as well
            allMuscles.addAll(exercise.getPrimaryMuscleGroups());
            allMuscles.addAll(exercise.getSecondaryMuscleGroups());
        }

        return allMuscles;
    }
}
