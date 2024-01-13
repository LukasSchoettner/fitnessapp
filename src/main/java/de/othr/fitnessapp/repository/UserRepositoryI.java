package de.othr.fitnessapp.repository;

import java.util.Optional;

import de.othr.fitnessapp.model.User;





public interface UserRepositoryI extends MyBaseRepository<User, Long> {
	
	
	Optional<User> findByLoginIgnoreCase(String login);

}
