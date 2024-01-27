package de.othr.fitnessapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="trainer")
@Getter
@Setter
public class Trainer extends Baseuser implements Serializable {

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
	private List<Course> courses = new ArrayList<>();

	public Trainer() {
		this.setId((long) -1);
	}
}
