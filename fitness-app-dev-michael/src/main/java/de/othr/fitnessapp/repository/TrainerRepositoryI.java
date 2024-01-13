package de.othr.fitnessapp.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.othr.fitnessapp.model.Trainer;



public interface TrainerRepositoryI extends MyBaseRepository<Trainer, Long>{

	List<Trainer> findBylastNameContainingIgnoreCase (String lastName);
	Page <Trainer> findAll(Pageable pageable);
	Page <Trainer> findBylastNameContainingIgnoreCase (String lastName, Pageable pageable);
}
