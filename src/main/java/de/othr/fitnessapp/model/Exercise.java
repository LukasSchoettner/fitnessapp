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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exercise implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50, message = "Size must be between {min} and {max}")
    private String name;

    @NotBlank(message = "Primary muscle is mandatory")
    @Size(min = 3, max = 50, message = "Size must be between {min} and {max}")
    @Column(name = "primary_muscle")
    private String primaryMuscle;

    @NotBlank(message = "Secondary muscle is mandatory")
    @Size(min = 3, max = 50, message = "Size must be between {min} and {max}")
    @Column(name = "secondary_muscle")
    private String secondaryMuscle;

    @NotBlank(message = "Instruction is mandatory")
    @Size(min = 3, max = 200, message = "Size must be between {min} and {max}")
    private String instruction;

    @Size(max = 50, message = "Size can be a maximum of {max}")
    private String equipment;

    //private Type type;
    //private Difficulty difficulty;
}
