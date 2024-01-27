package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Address;
import de.othr.fitnessapp.model.Baseuser;
import de.othr.fitnessapp.model.Trainer;
import de.othr.fitnessapp.repository.TrainerRepository;
import de.othr.fitnessapp.repository.UserRepository;
import de.othr.fitnessapp.service.BaseuserService;
import de.othr.fitnessapp.service.TrainerServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BaseuserServiceImpl implements BaseuserService {

	private UserRepository baseuserRepository;
	// tbd private CourseRepositoryI  courseRepository;

	public BaseuserServiceImpl(UserRepository baseuserRepository) {
		super();
		this.baseuserRepository = baseuserRepository;
	} 
	
	@Override
	public Page<Baseuser> getAllBaseusers(String username, Pageable pageable) {

		Page <Baseuser> pageBaseusers;
		if (username == null) {
	        pageBaseusers = baseuserRepository.findAll(pageable);
	      } else {
	        pageBaseusers = baseuserRepository.findByLoginContainingIgnoreCase(username, pageable);
	        
	      }				
		return  pageBaseusers;
	}

	@Override
	public Baseuser getBaseuserById(Long id){
		return baseuserRepository.findById(id).get();
	}

	@Override
	public Baseuser saveBaseuser(Baseuser baseuser) {

		return baseuserRepository.save(baseuser);
	}

	@Override
	public Baseuser findByLoginIgnoreCase(String login){
		return baseuserRepository.findByLoginIgnoreCase(login).get();
	}
}
