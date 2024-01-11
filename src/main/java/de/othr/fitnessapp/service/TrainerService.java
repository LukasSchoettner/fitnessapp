package de.othr.fitnessapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.othr.fitnessapp.model.Trainer;

public interface TrainerService {
	
	Page<Trainer> getAllTrainers(String lastName, Pageable pageable);
	
	List<Trainer> findTrainersBylastName(String lastName);
	
	Trainer saveTrainer(Trainer trainer);
	
	Trainer getTrainerById(Long id);
	
	Trainer updateTrainer(Trainer trainer);
	
	void delete(Trainer trainer);
}
