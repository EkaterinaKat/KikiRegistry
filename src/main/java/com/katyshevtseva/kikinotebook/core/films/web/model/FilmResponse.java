package com.katyshevtseva.kikinotebook.core.films.web.model;

import lombok.Data;

import java.util.List;

@Data
public class FilmResponse {
    private Long id;
    private String name;
    private int year;
    private PosterResponse poster;
    private String description;
    private List<GenreResponse> genres;
    private Integer movieLength;

    public String getTitleAndYear() {
        return name + " " + year;
    }
}
