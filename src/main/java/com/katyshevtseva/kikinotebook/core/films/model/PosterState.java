package com.katyshevtseva.kikinotebook.core.films.model;

import lombok.Getter;

@Getter
public enum PosterState {
    LOADED(null),
    NOT_LOADED("not_loaded.jpg"),
    FAILED_TO_LOAD_FILMS("failed_to_load_films.jpg"),
    FILM_NOT_FOUND("film_not_found.jpg"),
    OTHER_ERROR("other_error.jpg");

    private final String imageName;

    PosterState(String imageName) {
        this.imageName = imageName;
    }
}
