package de.othr.fitnessapp.repository.impl;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import de.othr.fitnessapp.model.Trainer;
import de.othr.fitnessapp.repository.TrainerRepositoryI;

@Repository
public interface TrainerRepositoryImp extends  TrainerRepositoryI, PagingAndSortingRepository<Trainer, Long>{

	List<Trainer> findBylastNameContainingIgnoreCase (String lastName);
	Page<Trainer>  findAll(Pageable pageable);
	Page <Trainer> findBylastNameContainingIgnoreCase (String lastName, Pageable pageable);
}
