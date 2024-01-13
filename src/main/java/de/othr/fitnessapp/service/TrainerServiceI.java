package de.othr.fitnessapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.othr.fitnessapp.model.Address;
import de.othr.fitnessapp.model.Trainer;

public interface TrainerServiceI {
	
	Page<Trainer> getAllTrainers(String lastName, Pageable pageable);
	
	List<Trainer> getAllTrainers();
	
	List<Trainer> findTrainersBylastName(String lastName);
	
	Trainer saveTrainer(Trainer trainer);
	
	Trainer getTrainerById(Long id);
	
	void delete(Trainer trainer);

	Trainer updateTrainerAndAddress(Long trainerId, Trainer updatedTrainer, Address updatedAddress);
}
