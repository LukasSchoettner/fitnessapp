package de.othr.fitnessapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
	private List<Course> courses;

	@ManyToOne
	@JoinColumn(name = "gym_id")
	private Gym gym;

	public Trainer() {
		this.setId((long) -1);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
