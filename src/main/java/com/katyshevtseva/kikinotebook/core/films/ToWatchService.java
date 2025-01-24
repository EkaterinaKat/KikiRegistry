package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGenre;
import com.katyshevtseva.kikinotebook.core.films.model.PosterState;
import com.katyshevtseva.kikinotebook.core.films.web.PosterLoader;
import com.katyshevtseva.kikinotebook.core.films2.web.model.FilmResponse;
import com.katyshevtseva.kikinotebook.core.films2.web.model.GenreResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.kikinotebook.core.films.model.FilmStatus.*;

public class ToWatchService {

    public static List<Film> getFilmsToWatch() {
        return Dao.getAllFilms()
                .stream()
                .filter(film -> film.getStatus() == TO_WATCH || film.getStatus() == WATCHED_AND_TO_WATCH)
                .sorted(Comparator.comparing(Film::getId).reversed())
                .collect(Collectors.toList());
    }

    public static void saveToWatchFilm(FilmResponse response) {
        List<FilmGenre> genres = convertResponseGenresToEntity(response.getGenres());
        Film film = new Film(
                null,
                response.getId(),
                response.getPoster().getUrl(),
                response.getName(),
                response.getYear(),
                null,
                TO_WATCH,
                null,
                PosterState.NOT_LOADED,
                response.getDescription(),
                genres,
                response.getMovieLength(),
                new Date(),
                false
        );

        Film savedFilm = Dao.saveNewFilm(film);
        PosterLoader.loadPosterBySavedUrl(savedFilm);
    }

    private static List<FilmGenre> convertResponseGenresToEntity(List<GenreResponse> genreResponses) {
        List<FilmGenre> result = new ArrayList<>();
        for (GenreResponse response : genreResponses) {
            FilmGenre genre = Dao.findFilmGenreByTitle(response.getName());
            if (genre == null) {
                genre = Dao.saveNewGenre(new FilmGenre(response.getName()));
            }
            result.add(genre);
        }
        if (result.size() != genreResponses.size()) {
            throw new RuntimeException("Что-то пошло не так с жанрами");
        }
        return result;
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
}
