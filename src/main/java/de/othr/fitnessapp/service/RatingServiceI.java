package de.othr.fitnessapp.service;

import de.othr.fitnessapp.model.Gym;
import de.othr.fitnessapp.model.Rating;

import java.util.ArrayList;
import java.util.List;

public interface RatingServiceI {


    Rating saveRating(Rating rating);

    Rating getRating(Long id);

    Rating updateRating(Rating rating);

    void deleteRating(Long id);
}
