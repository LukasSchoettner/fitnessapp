package de.othr.fitnessapp.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Baseuser;

@Service
public interface BaseuserService {
    
    Page<Baseuser> getAllBaseusers(String username, Pageable pageable);

    Baseuser getBaseuserById(Long id);

    Baseuser saveBaseuser(Baseuser baseuser);

    Baseuser findByLoginIgnoreCase(String login);
}
