package de.othr.fitnessapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.fitnessapp.model.Baseuser;

public interface UserRepositoryI extends JpaRepository<Baseuser, Long> {

	Optional<Baseuser> findByLoginIgnoreCase(String login);
}
