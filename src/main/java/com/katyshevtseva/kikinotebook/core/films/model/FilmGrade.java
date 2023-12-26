package com.katyshevtseva.kikinotebook.core.films.model;

import static com.katyshevtseva.kikinotebook.core.CoreConstants.GRAY;
import static com.katyshevtseva.kikinotebook.core.CoreConstants.HIGHEST_GRADE_COLOR;

public enum FilmGrade {
    FAVOURITE(HIGHEST_GRADE_COLOR),
    EXCELLENT("#45E345"),
    GOOD("#9CE345"),
    NORMAL("#E3DF45"),
    SOSO("#C5A45E"),
    BAD(GRAY),
    NOTFINISHED(GRAY),
    NOTCLASSIFIED(GRAY);

    private final String color;

    FilmGrade(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
