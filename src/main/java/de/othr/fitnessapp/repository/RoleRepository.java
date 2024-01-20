package de.othr.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.othr.fitnessapp.model.Role;

@Repository
public interface RoleRepository extends  JpaRepository<Role, Long>{

	Role findByDescription (String description);
}
