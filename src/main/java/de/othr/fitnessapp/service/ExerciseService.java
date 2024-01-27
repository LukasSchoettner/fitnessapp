package de.othr.fitnessapp.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.othr.fitnessapp.model.Exercise;
import de.othr.fitnessapp.repository.ExerciseRepository;
import jakarta.annotation.PostConstruct;

@Service
public interface ExerciseService {

    public void fetchAndSaveExercises() throws IOException, InterruptedException;
    List<Exercise> findAll();
    Exercise findById(Long id);
    Exercise findByName(String name);
    Exercise save(Exercise exercise);
    void deleteById(Long id);
    List<Exercise> searchExercises(String query);
    Set<String> getAllMuscles();
}
