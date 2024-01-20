package de.othr.fitnessapp.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.repository.RoleRepository;
import de.othr.fitnessapp.service.RoleServiceI;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleServiceI{
	private RoleRepository  roleRepository;

	@Override
	public Role findRoleByDescription(String description) {
		return roleRepository.findByDescription(description);
	}
}
