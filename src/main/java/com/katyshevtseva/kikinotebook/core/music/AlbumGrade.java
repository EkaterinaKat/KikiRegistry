package com.katyshevtseva.kikinotebook.core.music;

import static com.katyshevtseva.kikinotebook.core.CoreConstants.*;

public enum AlbumGrade {
    NICE(HIGHEST_GRADE_COLOR), OK(AVERAGE_GRADE_COLOR), SOSO(GRAY);

    private final String color;

    AlbumGrade(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
