package de.othr.fitnessapp.model;

import de.othr.fitnessapp.utils.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="customer")
@Data
public class Customer extends User implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "customer")
    private List<Workout> workouts;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "customer_attend_course",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> attendedCourses;
    
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
	
    @NotNull
    @Size(min = 2, max = 50, message = "{user.firstName.size}")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50, message = "{user.lastName.size}")
    @Column(name = "LAST_NAME")
    private String lastName;
}
