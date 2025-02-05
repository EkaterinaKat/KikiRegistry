package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGenre;
import com.katyshevtseva.kikinotebook.core.films.model.PosterState;
import com.katyshevtseva.kikinotebook.core.films.model.Type;
import com.katyshevtseva.kikinotebook.core.films.web.PosterLoader;
import com.katyshevtseva.kikinotebook.core.films.web.model.FilmResponse;
import com.katyshevtseva.kikinotebook.core.films.web.model.GenreResponse;

import java.util.*;
import java.util.stream.Collectors;

import static com.katyshevtseva.kikinotebook.core.films.model.FilmStatus.TO_WATCH;

public class ToWatchService {

    public static List<Film> getFilmsToWatch() {
        return Dao.getAllFilms()
                .stream()
                .filter(StatusService::isToWatch)
                .sorted(Comparator.comparing(Film::getToWatchAddingDate).reversed())
                .collect(Collectors.toList());
    }

    public static void saveToWatchFilm(FilmResponse response) throws Exception {
        Film existing = Dao.findFilmByKpId(response.getId());
        if (existing != null) {
            StatusService.wantToWatchFilm(existing);
        } else {
            Set<FilmGenre> genres = convertResponseGenresToEntity(response.getGenres());
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
                    Type.getByResponseString(response.getType()),
                    false,
                    0,
                    0,
                    null
            );
            Film savedFilm = Dao.saveNewFilm(film);
            PosterLoader.loadPosterBySavedUrl(savedFilm);
            AdditionalInfoService.loadAdditionalInfo(film);
        }
    }

    public static Set<FilmGenre> convertResponseGenresToEntity(List<GenreResponse> genreResponses) {
        Set<FilmGenre> result = new HashSet<>();
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
