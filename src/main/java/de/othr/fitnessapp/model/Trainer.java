package de.othr.fitnessapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
public class Trainer extends User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	Long id;
	
	
	//now imported via user
	/*@NotBlank(message = "Email is mandatory")
	private String email;
	
	@NotBlank(message = "password is mandatory")
	private String password;*/
	
	@NotBlank(message = "Last Name is mandatory")
	private String lastName;

	@NotBlank(message = "First Name is mandatory")
	private String firstName;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@NotNull(message = "Date of Birth is mandatory")
	private LocalDate birthDate;
	
	@NotBlank(message = "Phone number is mandatory")
	private String phone;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id", updatable=false)
	private Address address;
	
	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Note> childEntities;

	//@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
	//private List<Course> childEntities;
	
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
