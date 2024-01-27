package de.othr.fitnessapp.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
	@Nullable
	private String street; 
	
	@Nullable
	private String houseNumber;
	
	@Nullable
	private String city;
	
	@Nullable
	private String ZIP ="00000";
}
