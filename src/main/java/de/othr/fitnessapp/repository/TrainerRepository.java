package de.othr.fitnessapp.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.othr.fitnessapp.model.Trainer;

@Repository
public interface TrainerRepository extends  JpaRepository<Trainer, Long>{

	List<Trainer> findBylastNameContainingIgnoreCase (String lastName);
	Page<Trainer>  findAll(Pageable pageable);
	Page <Trainer> findBylastNameContainingIgnoreCase (String lastName, Pageable pageable);
}
