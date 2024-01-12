package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Role;


public interface RoleServiceI {
	

	Role findRoleByDescription(String description);
	
}
