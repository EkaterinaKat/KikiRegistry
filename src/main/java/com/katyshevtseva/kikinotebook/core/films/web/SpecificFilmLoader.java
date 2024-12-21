package com.katyshevtseva.kikinotebook.core.films.web;

import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.web.model.FilmArrayResponse;
import com.katyshevtseva.kikinotebook.core.films.web.model.FilmResponse;

public class SpecificFilmLoader {

    public static FilmResponse loadFilm(Film film) throws Exception {
        FilmArrayResponse movies = FilmSearchEngine.findFilms(film.getTitle());
        for (FilmResponse filmResponse : movies.getDocs()) {
            if (checkIfItSameFilm(film, filmResponse)) {
                return filmResponse;
            }
        }
        throw new Exception("Фильм не найден");
    }

    private static boolean checkIfItSameFilm(Film film, FilmResponse filmResponse) {
        return film.getTitle().equals(filmResponse.getName())
                && film.getYear().equals(filmResponse.getYear());
    }
}
