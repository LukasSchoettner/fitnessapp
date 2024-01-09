package de.othr.fitnessapp.repository;

import java.util.Optional;
import de.othr.fitnessapp.model.User;

public interface UserRepository extends MyBaseRepository<User, Long> {
    // Additional query methods can be defined here
    Optional<User> findByLoginIgnoreCase(String login);
}
