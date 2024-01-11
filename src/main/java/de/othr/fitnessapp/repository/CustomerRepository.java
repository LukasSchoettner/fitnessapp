package de.othr.fitnessapp.repository;

import de.othr.fitnessapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Add custom query methods if needed
    // Example: Find a customer by email
    Optional<Customer> findByEmail(String email);

    List<Customer> findByAttendedCourses_Id(Long courseId);

    Optional<Customer> findByPhone(String phone);

    // Other custom queries can be added based on your requirements
}
