package de.othr.fitnessapp.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Baseuser;
import de.othr.fitnessapp.repository.UserRepository;
import de.othr.fitnessapp.service.BaseuserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class BaseuserServiceImpl implements BaseuserService{

    private UserRepository userRepository;

    public Baseuser findByLoginIgnoreCase(String login){

        Optional<Baseuser> user = userRepository.findByLoginIgnoreCase(login);
        return user.get();
    };
}
