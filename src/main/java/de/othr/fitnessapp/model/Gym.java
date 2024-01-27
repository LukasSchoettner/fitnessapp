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
public class Gym extends Baseuser implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gym_id")
    Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<Trainer> trainerList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", updatable = false)
    private Address address;

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


    public static long getSerialversionuid() {
        return serialVersionUID;
    }


}