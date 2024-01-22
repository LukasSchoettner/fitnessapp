package de.othr.fitnessapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="gym")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gym implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<Trainer> trainerList;

    public void addTrainer(Trainer trainer) {
        if (trainerList == null) {
            trainerList = new ArrayList<>();
        }
        trainerList.add(trainer);
        trainer.setGym(this); // Setze das Gym für den Trainer
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


}