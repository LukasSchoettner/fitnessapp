package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Role;

import java.util.List;


public interface RoleServiceI {
	

	Role findRoleByDescription(String description);

	Role getRoleById(Long id);

	List<Role> findAll();
}
