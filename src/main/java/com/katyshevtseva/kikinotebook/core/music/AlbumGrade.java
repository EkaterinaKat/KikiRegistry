package com.katyshevtseva.kikinotebook.core.music;

public enum AlbumGrade {
    NICE("#45E397"), OK("#9CE345"), SOSO("#7A7A7A");

    private final String color;

    AlbumGrade(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
