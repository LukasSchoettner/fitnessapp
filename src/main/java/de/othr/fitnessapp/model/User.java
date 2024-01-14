package de.othr.fitnessapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.JoinColumn;

import lombok.Data;

@Entity
@Table(name = "baseuser")
@Inheritance(strategy=InheritanceType.JOINED)
@Data // Add this Lombok annotation to generate getters and setters
public class User implements Serializable {
	
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

	@NotBlank(message = "Phone number is mandatory")
	private String phone;
	
	private boolean active = true;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinTable(
			name="userrole",
			joinColumns = @JoinColumn(name="iduser"),
			inverseJoinColumns = @JoinColumn(name="idrole")
			)	
	private List<Role> roles = new ArrayList<Role>();
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	// Other methods and fields remain unchanged
}
