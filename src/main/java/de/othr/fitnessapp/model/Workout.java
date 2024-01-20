package de.othr.fitnessapp.model;

import de.othr.fitnessapp.utils.LevelEnum;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workout")
public class Workout implements Serializable {

    private Baseuser baseuser;

    @Serial
    private static final long serialVersionUID = 1L;

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

    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "workout_id", referencedColumnName = "workout_id")
    @Valid
    private List<Exercise> exercises= new ArrayList<>();

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise) {
        this.exercises.remove(exercise);
    }

    public void updateExercise(Exercise oldExercise, Exercise updatedExercise) {
        this.exercises.set(exercises.indexOf(oldExercise), updatedExercise);
    }

    public Exercise getExerciseById(Long exerciseId) {
        Optional<Exercise> exerciseById = this.exercises.stream()
                                                            .filter(exercise -> exercise.getId().equals(exerciseId))
                                                            .findFirst();

        if (exerciseById.isPresent()) {
            return exerciseById.get();
        } else {
            log.error("Exercise with ID {} does not exist!", exerciseId);
            throw new NullPointerException("Exercise with ID " + exerciseId + " does not exist");
        }
    }
}
