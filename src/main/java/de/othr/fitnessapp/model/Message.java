package de.othr.fitnessapp.model;

import jakarta.validation.constraints.NotBlank;

public class Message {
	
	@NotBlank(message = "Email is mandatory")
	private String email;
	@NotBlank(message = "Message is mandatory")
	private String message;
	@NotBlank(message = "Subject should not be empty")
	private String subject;
	
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
