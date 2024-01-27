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

//    @NotNull(message = "Trainer is mandatory")
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
//    private Trainer trainer;
    private String trainer;

//    //TODO change type to Costumer
//    @NotNull
//    @ManyToMany
//    private Set<Customer> participants = new HashSet<>();
    private Set<String> participants = new HashSet<>();

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "workout_id", referencedColumnName = "workout_id")
//    private Set<Workout> workouts = new HashSet<>();
}
