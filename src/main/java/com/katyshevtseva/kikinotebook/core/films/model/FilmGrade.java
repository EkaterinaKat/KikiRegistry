package com.katyshevtseva.kikinotebook.core.films.model;

public enum FilmGrade {
    FAVOURITE("#45E397"),
    EXCELLENT("#45E345"),
    GOOD("#9CE345"),
    NORMAL("#E3DF45"),
    SOSO("#C5A45E"),
    BAD("#7A7A7A"),
    NOTFINISHED("#7A7A7A");

    private final String color;

    FilmGrade(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
