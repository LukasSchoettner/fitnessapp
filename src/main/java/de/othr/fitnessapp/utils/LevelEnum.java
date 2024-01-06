package de.othr.fitnessapp.utils;

public enum LevelEnum {
    BEGINNER("Beginner") ,
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced"),
    PROFESSIONAL("Professional");

    private final String displayValue;

    private LevelEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }



}
