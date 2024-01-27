package de.othr.fitnessapp.repository;
import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.model.Gym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {

    Page<Gym> findAll(Pageable pageable);

    Optional<Customer> findByName(String name);

    void deleteById(Long id);
}
