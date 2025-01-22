package com.katyshevtseva.kikinotebook.core.films2;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films2.model.FilmGenre;
import com.katyshevtseva.kikinotebook.core.films2.model.FilmToWatch;
import com.katyshevtseva.kikinotebook.core.films2.model.PosterState;
import com.katyshevtseva.kikinotebook.core.films2.web.PosterLoader;
import com.katyshevtseva.kikinotebook.core.films2.web.model.FilmResponse;
import com.katyshevtseva.kikinotebook.core.films2.web.model.GenreResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FilmToWatchService2 {

    public static void saveToWatchFilm(FilmResponse response) {
        List<FilmGenre> genres = convertResponseGenresToEntity(response.getGenres());
        FilmToWatch filmToWatch = Dao.saveNewToWatchFilm(new FilmToWatch(
                response.getId(),
                response.getName(),
                response.getYear(),
                response.getDescription(),
                genres,
                response.getMovieLength(),
                PosterState.NOT_LOADED,
                response.getPoster().getUrl(),
                new Date()
        ));
        PosterLoader.loadPoster(filmToWatch);
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

    public static List<FilmToWatch> getFilmsToWatch() {
        return Dao.getAllFilmsToWatch().stream()
                .sorted(Comparator.comparing(FilmToWatch::getId).reversed())
                .collect(Collectors.toList());
    }

    public static void updatePosterState(FilmToWatch film, PosterState posterState) {
        film.setPosterState(posterState);
        Dao.saveEdited(film);
    }

    public static void delete(FilmToWatch film) {
        Dao.delete(film);
    }
}
