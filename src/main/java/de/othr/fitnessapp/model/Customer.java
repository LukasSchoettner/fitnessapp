package de.othr.fitnessapp.model;

import de.othr.fitnessapp.utils.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="customer")
@Getter
@Setter
public class Customer extends Baseuser implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
    @Column(name = "first_name")
    private String first_name;

    @NotNull
    @Size(min = 2, max = 50, message = "{user.lastName.size}")
    @Column(name = "last_name")
    private String last_name;

}
