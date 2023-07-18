package com.katyshevtseva.kikinotebook.core.model;

public enum BookGrade {
    NOT_OK("#FD5E53"), OK("#FFFFFF"), NICE("#47FFAC"), FAVOURITE("#00FF00");

    private final String color;

    BookGrade(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
