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

import static com.katyshevtseva.kikinotebook.core.films.model.FilmStatus.TO_WATCH;
import static com.katyshevtseva.kikinotebook.core.films.model.FilmStatus.WATCHED;

public class ToWatchService {

    public static List<Film> getFilmsToWatch() {
        return Dao.getAllFilms()
                .stream()
                .filter(StatusService::isToWatch)
                .sorted(Comparator.comparing(Film::getToWatchAddingDate).reversed())
                .collect(Collectors.toList());
    }

    public static void saveToWatchFilm(FilmResponse response) {
        Film existing = Dao.findFilmByKpId(response.getId());
        if (existing == null) {
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
        } else {
            if (existing.getStatus() == WATCHED) {
                StatusService.wantToWatchFilm(existing);
            }
        }
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
}
