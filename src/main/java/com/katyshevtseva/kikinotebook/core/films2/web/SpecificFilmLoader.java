package com.katyshevtseva.kikinotebook.core.films2.web;

import com.katyshevtseva.kikinotebook.core.films2.model.Film;
import com.katyshevtseva.kikinotebook.core.films2.web.exception.FilmNotFoundException;
import com.katyshevtseva.kikinotebook.core.films2.web.model.FilmArrayResponse;
import com.katyshevtseva.kikinotebook.core.films2.web.model.FilmResponse;

public class SpecificFilmLoader {

    public static FilmResponse loadFilm(Film film) throws Exception {
        FilmArrayResponse movies = FilmSearchEngine.findFilms(film.getTitle());
        for (FilmResponse filmResponse : movies.getDocs()) {
            if (checkIfItSameFilm(film, filmResponse)) {
                return filmResponse;
            }
        }
        throw new FilmNotFoundException("Фильм не найден");
    }

    private static boolean checkIfItSameFilm(Film film, FilmResponse filmResponse) {
        return film.getTitle().replaceAll("[^a-zA-Zа-яА-Я]", "")
                .equalsIgnoreCase(filmResponse.getName().replaceAll("[^a-zA-Zа-яА-Я]", ""))
                && film.getYear().equals(filmResponse.getYear());
    }
}
