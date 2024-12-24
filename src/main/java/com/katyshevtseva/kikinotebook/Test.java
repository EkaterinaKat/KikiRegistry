package com.katyshevtseva.kikinotebook;

import com.katyshevtseva.kikinotebook.core.films.web.FilmSearchEngine;

public class Test {

    public static void main(String[] args) {
        try {
            System.out.println(FilmSearchEngine.findFilms("Леон"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
