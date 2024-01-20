package de.othr.fitnessapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "baseuser")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
public class Baseuser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Login is mandatory")
	private String login;

	@NotBlank(message = "Password is mandatory")
	private String password;

	@NotBlank(message = "Email is mandatory")
	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id", updatable = false)
	private Address address;

	private boolean active = true;

	@JsonIgnore
	@OneToMany(mappedBy = "baseuser", cascade = CascadeType.ALL)
    private List<Workout> workouts = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinTable(name = "userrole", joinColumns = @JoinColumn(name = "iduser"), inverseJoinColumns = @JoinColumn(name = "idrole"))
	private List<Role> roles = new ArrayList<>();
}
