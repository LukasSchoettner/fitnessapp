package de.othr.fitnessapp.service.impl;

import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.repository.RoleRepository;
import de.othr.fitnessapp.service.RoleServiceI;





@Service
public class RoleServiceImpl implements RoleServiceI{

	private RoleRepository  roleRepository;
	
	
	public RoleServiceImpl(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
		
	}


	@Override
	public Role findRoleByDescription(String description) {
	
		return roleRepository.findByDescription(description);
	} 
	
	
}
