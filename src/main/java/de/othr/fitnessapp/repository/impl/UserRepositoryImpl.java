package de.othr.fitnessapp.repository.impl;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.othr.fitnessapp.model.User;
import de.othr.fitnessapp.repository.UserRepositoryI;

@Repository
public interface UserRepositoryImpl extends UserRepositoryI, CrudRepository<User, Long>{

	
	
	
}
