package de.othr.fitnessapp.service;

import java.util.List;

import de.othr.fitnessapp.model.Gym;

public interface GymServiceI {

    Gym saveGym(Gym gym);

    Gym getGym(Long id);

    Gym updateGym(Gym gym);

    void deleteGym(Long id);

}
