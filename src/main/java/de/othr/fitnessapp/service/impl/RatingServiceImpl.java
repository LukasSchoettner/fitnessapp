package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.model.Rating;
import de.othr.fitnessapp.service.RatingServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class RatingServiceImpl implements RatingServiceI {

    private List<Rating> ratings;


    public RatingServiceImpl() {
        this.ratings = new ArrayList<>();
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public List<Rating> getAllRatings() {
        return ratings;
    }

    public double getAverageRating(){
        double averageRating = 0;
        return averageRating;
    }

    @Override
    public Rating saveRating(Rating rating) {
        return null;
    }

    @Override
    public Rating getRating(Long id) {
        return null;
    }

    @Override
    public Rating updateRating(Rating rating) {
        return null;
    }

    @Override
    public void deleteRating(Long id) {

    }
}
