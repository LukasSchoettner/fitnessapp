package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Add custom query methods if needed
    // Example: Find a customer by email
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByLogin(String login);

    //Optional<Customer> findByPhone(String phone);

    Set<Customer> findByCoursesNotContaining(Course course);

    // Other custom queries can be added based on your requirements
}
