package de.othr.fitnessapp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Message {
	@NotBlank(message = "Email is mandatory")
	private String email;
	@NotBlank(message = "Message is mandatory")
	private String message;
	@NotBlank(message = "Subject should not be empty")
	private String subject;
}
