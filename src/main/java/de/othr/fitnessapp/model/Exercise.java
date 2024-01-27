package de.othr.fitnessapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exercise implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<WorkoutExercise> workoutExercises = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String equipment;
    private String movement;

    @Column(length = 10000)
    private String instructions;

    private String force;
    
    @ElementCollection
    private List<String> primaryMuscleGroups;

    @ElementCollection
    private List<String> primaryMuscles;

    @ElementCollection
    private List<String> secondaryMuscleGroups;

    @ElementCollection
    private List<String> secondaryMuscles;

    @ElementCollection
    private List<String> tags;

    private String type;

}
