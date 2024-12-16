package com.katyshevtseva.kikinotebook.core.films.web.model;

import lombok.Data;

@Data
public class FilmResponse {
    private String name;
    private int year;
    private PosterResponse poster;
}
