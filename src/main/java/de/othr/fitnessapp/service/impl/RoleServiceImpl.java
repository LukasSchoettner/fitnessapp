package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Trainer;
import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.repository.RoleRepository;
import de.othr.fitnessapp.service.RoleServiceI;

import java.util.List;


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

	@Override
	public Role getRoleById(Long id) {

		return roleRepository.findById(id).get();
	}

	@Override
	public List<Role> findAll(){
		return roleRepository.findAll();
	}
	
}
