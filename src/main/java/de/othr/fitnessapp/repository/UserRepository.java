package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Additional query methods can be defined here
    void delete(Long Id);
}
