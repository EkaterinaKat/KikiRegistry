package com.katyshevtseva.kikinotebook.core.series.model;

public enum SeriesGrade {
    FAVOURITE("#ffff00"), OK("#bcfff1"), DISLIKED("#c8c8c8");

    private final String color;

    SeriesGrade(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
