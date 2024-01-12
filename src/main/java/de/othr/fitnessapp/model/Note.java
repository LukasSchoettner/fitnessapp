package de.othr.fitnessapp.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="note")
public class Note {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "trainer_id", referencedColumnName = "id")
	private Trainer trainer;
	
	@NotBlank(message = "Title is mandatory")
	String title;
	
	@NotBlank(message = "Message is mandatory")
	String message;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@NotNull(message = "Date is mandatory")
	private LocalDate Date;
	
	public Note() {
		this.setId((long) -1);
		
		Trainer trainer = new Trainer();
		this.setTrainer(trainer);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getDate() {
		return Date;
	}

	public void setDate(LocalDate date) {
		Date = date;
	}
	
	/*@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "course_id", referencedColumnName = "id")
	private Course course;*/
}
