package de.othr.fitnessapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workout")
public class Workout implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Transient
    private List<Long> exerciseIds = new ArrayList<>();

    @Transient
    private List<Integer> repetitions = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50, message = "Size must be between {min} and {max}")
    private String name;

    @NotNull(message = "Date is mandatory")
    @Future(message = "Date must be in the future")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkoutExercise> workoutExercises = new HashSet<>();

    // Other fields and methods...

    // Assuming Baseuser is another entity related to Workout
    @ManyToOne
    @JoinColumn(name = "baseuser_id")
    private Baseuser baseuser;
}
