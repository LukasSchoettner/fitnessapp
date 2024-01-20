package de.othr.fitnessapp.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Address;
import de.othr.fitnessapp.model.Trainer;
import de.othr.fitnessapp.repository.TrainerRepository;
import de.othr.fitnessapp.service.TrainerServiceI;


@Service
public class TrainerServiceImpl implements TrainerServiceI{

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
	public List<Trainer> getAllTrainers() {
				
		return  (List<Trainer>) trainerRepository.findAll();
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
	public Trainer updateTrainerAndAddress(Long trainerId, Trainer updatedTrainer, Address updatedAddress) {
        Trainer existingTrainer = trainerRepository.findById(trainerId).orElse(null);

        if (existingTrainer != null) {
            existingTrainer.setLogin(updatedTrainer.getLogin());
            existingTrainer.setEmail(updatedTrainer.getEmail());
            existingTrainer.setPassword(updatedTrainer.getPassword());
            existingTrainer.setLastName(updatedTrainer.getLastName());
            existingTrainer.setFirstName(updatedTrainer.getFirstName());
            existingTrainer.setPhone(updatedTrainer.getPhone());
            
            if (existingTrainer.getAddress() != null && updatedAddress != null) {
                Address existingAddress = existingTrainer.getAddress();
                existingAddress.setStreet(updatedAddress.getStreet());
                existingAddress.setCity(updatedAddress.getCity());
                existingAddress.setHouseNumber(updatedAddress.getHouseNumber());
                existingAddress.setZIP(updatedAddress.getZIP());
                
            } else if (updatedAddress != null) {
                existingTrainer.setAddress(updatedAddress);
            } 
        }
        return trainerRepository.save(existingTrainer);
    }
        
            

	@Override
	public void delete(Trainer trainer) {

		trainerRepository.delete(trainer);	
	}

	@Override
	public List<Trainer> findTrainersBylastName(String lastName) {

		return trainerRepository.findBylastNameContainingIgnoreCase(lastName);
	}

	
}
