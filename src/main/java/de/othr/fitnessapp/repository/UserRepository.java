package de.othr.fitnessapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.fitnessapp.model.Baseuser;





public interface UserRepository extends JpaRepository<Baseuser, Long> {
	
	
	Optional<Baseuser> findByLoginIgnoreCase(String login);

	Page<Baseuser> findByLoginContainingIgnoreCase(String username, Pageable pageable);

	Page<Baseuser>  findAll(Pageable pageable);
}
