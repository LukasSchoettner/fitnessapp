package de.othr.fitnessapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.fitnessapp.model.User;





public interface UserRepositoryI extends JpaRepository<User, Long> {
	
	
	Optional<User> findByLoginIgnoreCase(String login);

}
