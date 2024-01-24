package de.othr.fitnessapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Baseuser;

@Service
public interface BaseuserService {

    Baseuser findByLoginIgnoreCase(String login);
}
