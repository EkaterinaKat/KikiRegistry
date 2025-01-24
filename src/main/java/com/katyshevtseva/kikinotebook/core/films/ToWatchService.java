package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.kikinotebook.core.films.model.FilmStatus.TO_WATCH;
import static com.katyshevtseva.kikinotebook.core.films.model.FilmStatus.WATCHED_AND_TO_WATCH;

public class ToWatchService {

    public static List<Film> getFilmsToWatch() {
        return Dao.getAllFilms()
                .stream()
                .filter(film -> film.getStatus() == TO_WATCH || film.getStatus() == WATCHED_AND_TO_WATCH)
                .sorted(Comparator.comparing(Film::getId).reversed())
                .collect(Collectors.toList());
    }
}
