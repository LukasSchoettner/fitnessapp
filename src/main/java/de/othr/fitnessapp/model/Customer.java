package de.othr.fitnessapp.model;

import de.othr.fitnessapp.utils.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="customer")
@Getter
@Setter
public class Customer extends Baseuser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
	private Long id;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
	
    @NotNull
    @Size(min = 2, max = 50, message = "{user.firstName.size}")
    @Column(name = "first_name")
    private String first_name;

    @NotNull
    @Size(min = 2, max = 50, message = "{user.lastName.size}")
    @Column(name = "last_name")
    private String last_name;

    @ManyToMany(mappedBy = "participants")
    private Set<Course> courses = new HashSet<>();
}
