package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;

import java.util.Date;

import static com.katyshevtseva.kikinotebook.core.films.model.FilmStatus.*;

public class StatusService {

    public static boolean isToWatch(Film film) {
        return film.getStatus() == TO_WATCH || film.getStatus() == WATCHED_AND_TO_WATCH;
    }

    public static void deleteFromToWatch(Film film) {
        switch (film.getStatus()) {
            case WATCHED:
                throw new RuntimeException("Try to delete watched film from to watch list");
            case TO_WATCH:
                Dao.delete(film);
                break;
            case WATCHED_AND_TO_WATCH:
                film.setStatus(WATCHED);
                film.setToWatchAddingDate(null);
                Dao.saveEdited(film);
                break;
        }
    }

    public static void wantToWatchFilm(Film film) {
        if (film.getStatus() == WATCHED) {
            film.setStatus(WATCHED_AND_TO_WATCH);
            film.setToWatchAddingDate(new Date());
            Dao.saveEdited(film);
        }
    }
}
