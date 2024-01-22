package de.othr.fitnessapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="trainer")
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


	public Gym getGym() {
		return gym;
	}

	public void setGym(Gym gym) {
		this.gym = gym;
	}

	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
	private List<Course> courseList;

	@ManyToOne
	@JoinColumn(name = "gym_id")
	private Gym gym;
	
	public Trainer() {
		this.setId((long) -1);
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void offerCourse(Course course) {
		if (courseList == null) {
			courseList = new ArrayList<>();
		}
		courseList.add(course);
		course.setTrainer(this);
	}


}
