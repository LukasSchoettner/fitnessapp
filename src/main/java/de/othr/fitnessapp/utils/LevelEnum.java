package de.othr.fitnessapp.utils;

import lombok.Getter;

@Getter
public enum LevelEnum {
    BEGINNER("Beginner") ,
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced"),
    PROFESSIONAL("Professional");

    private final String displayValue;

    LevelEnum(String displayValue) {
        this.displayValue = displayValue;
    }
}
