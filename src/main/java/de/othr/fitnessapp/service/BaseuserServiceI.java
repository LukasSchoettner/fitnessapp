package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Baseuser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BaseuserServiceI {


	Page<Baseuser> getAllBaseusers(String username, Pageable pageable);

	Baseuser getBaseuserById(Long id);

	Baseuser saveBaseuser(Baseuser baseuser);
}
