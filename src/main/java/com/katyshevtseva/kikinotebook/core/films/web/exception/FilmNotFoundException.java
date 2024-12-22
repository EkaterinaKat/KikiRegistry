package com.katyshevtseva.kikinotebook.core.films.web.exception;

public class FilmNotFoundException extends Exception {

    public FilmNotFoundException() {
    }

    public FilmNotFoundException(String message) {
        super(message);
    }
}
