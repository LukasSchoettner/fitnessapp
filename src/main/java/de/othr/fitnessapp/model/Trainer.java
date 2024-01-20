package de.othr.fitnessapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="trainer")
@Getter
@Setter
public class Trainer extends Baseuser implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	Long id;

	@NotBlank(message = "Last Name is mandatory")
	private String lastName;

	@NotBlank(message = "First Name is mandatory")
	private String firstName;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@NotNull(message = "Date of Birth is mandatory")
	private LocalDate birthDate;
	
	@NotBlank(message = "Phone number is mandatory")
	private String phone;

	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Note> childEntities;

	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
	private Set<Course> courseList = new HashSet<>() ;
}
