package com.katyshevtseva.kikinotebook.core.series.model;

import static com.katyshevtseva.kikinotebook.core.CoreConstants.*;

public enum SeriesGrade {
    FAVOURITE(HIGHEST_GRADE_COLOR),
    NICE("#52E552"),
    OK(AVERAGE_GRADE_COLOR),
    SOSO("#D0B453"),
    DISLIKED(GRAY);

    private final String color;

    SeriesGrade(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
