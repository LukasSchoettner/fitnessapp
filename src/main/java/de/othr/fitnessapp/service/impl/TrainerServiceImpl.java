package de.othr.fitnessapp.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Trainer;
import de.othr.fitnessapp.repository.TrainerRepository;
import de.othr.fitnessapp.service.TrainerService;


@Service
public class TrainerServiceImpl implements TrainerService{

	private TrainerRepository  trainerRepository;
	// tbd private CourseRepositoryI  courseRepository;
	
	public TrainerServiceImpl(TrainerRepository trainerRepository/* tbd , CourseRepositoryI  courseRepository*/) {
		super();
		this.trainerRepository = trainerRepository;
		// tbd this.courseRepository = courseRepository;
	} 
	
	@Override
	public Page<Trainer> getAllTrainers(String lastName, Pageable pageable) {
		// TODO Auto-generated method stub
		Page <Trainer> pageTrainers;
		if (lastName == null) {
	        pageTrainers = trainerRepository.findAll(pageable);
	      } else {
	        pageTrainers = trainerRepository.findBylastNameContainingIgnoreCase(lastName, pageable);
	        
	      }				
		return  pageTrainers;
	}

	@Override
	public Trainer saveTrainer(Trainer trainer) {
		// TODO Auto-generated method stub
		
		return trainerRepository.save(trainer);
	}

	@Override
	public Trainer getTrainerById(Long id) {
		// TODO Auto-generated method stub
		return trainerRepository.findById(id).get();
	}

	@Override
	public Trainer updateTrainer(Trainer trainer) {
		// TODO Auto-generated method stub
		return trainerRepository.save(trainer);
	}

	@Override
	public void delete(Trainer trainer) {
		// TODO Auto-generated method stub
		trainerRepository.delete(trainer);	
	}

	@Override
	public List<Trainer> findTrainersBylastName(String lastName) {
		// TODO Auto-generated method stub
		return trainerRepository.findBylastNameContainingIgnoreCase(lastName);
	}
}
