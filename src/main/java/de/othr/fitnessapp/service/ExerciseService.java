package de.othr.fitnessapp.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.repository.ExerciseRepository;
import jakarta.annotation.PostConstruct;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ExerciseService(ExerciseRepository exerciseRepository, ObjectMapper objectMapper) {
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
            processApiResponse(response.body());
        }
    }

    private void processApiResponse(String responseBody) {
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
