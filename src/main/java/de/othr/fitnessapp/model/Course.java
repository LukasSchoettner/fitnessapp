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
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class Course implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50, message = "Size must be between {min} and {max}")
    private String name;

    @NotNull(message = "Date is mandatory")
    @Future(message = "Date must be in the future")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    @NotBlank(message = "Trainer is mandatory")
    //@NotNull(message = "Trainer is mandatory")
    //TODO change type to Trainer
    private String trainer;

    //TODO change type to User
    @NotNull
    private Set<String> participants = new HashSet<>();

    //private Set<Workout> workouts = new HashSet<>();
    //private Level level;
    //private Licence licence;
}
