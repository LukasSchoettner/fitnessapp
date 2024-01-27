package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.service.GymServiceI;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Gym;
import de.othr.fitnessapp.repository.GymRepository;


@Service
@Log4j2

public class GymServiceImpl implements GymServiceI {

    private GymRepository  gymRepository;

    public GymServiceImpl(){

    }

    @Override
    public Gym saveGym(Gym gym) {
        return gymRepository.save(gym);
    }

    @Override
    public Gym getGym(Long id) {
        return gymRepository.findById(id).get();
    }

    @Override
    public Gym updateGym(Gym gym) {
        return gymRepository.save(gym);
    }

    @Override
    public void deleteGym(Long id) {
        gymRepository.deleteById(id);
    }

}
