package com.katyshevtseva.kikinotebook.core.films2;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGrade;
import com.katyshevtseva.kikinotebook.core.films2.model.FilmToWatch;
import com.katyshevtseva.kikinotebook.core.films2.model.PosterState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FilmsService2 {

    public static void save(Film existing, String title, Integer year, FilmGrade grade, boolean fvadfs) {
        title = title.trim();

        if (existing == null) {
            existing = new Film();
            existing.setTitle(title);
            existing.setPosterState(PosterState.NOT_LOADED);
            existing.setYear(year);
            existing.setGrade(grade);
            existing.setFvadfs(fvadfs);
            Dao.saveNew(existing);
        } else {
            existing.setTitle(title);
            existing.setYear(year);
            existing.setGrade(grade);
            existing.setFvadfs(fvadfs);
            Dao.saveEdited(existing);
        }
    }

    public static List<Film> getFilms(FilmGrade grade, String searchString) {
        return Dao.findFilms(grade, searchString)
                .stream().sorted(Comparator.comparing(Film::getTitle)).collect(Collectors.toList());
    }

    public static List<Film> getAllFilms() {
        return Dao.getAllFilms()
                .stream()
                .sorted(Comparator.comparing(Film::getTitle))
                .collect(Collectors.toList());
    }

    public static void addDate(Film film, Date date) {
        if (film.getDates() == null) {
            film.setDates(new ArrayList<>());
        }
        film.getDates().add(date);
        Dao.saveEdited(film);
    }

    public static void deleteDate(Film film, Date date) {
        if (film.getDates().remove(date)) {
            Dao.saveEdited(film);
        } else {
            throw new RuntimeException();
        }
    }

    public static void updatePosterState(Film film, PosterState posterState) {
        film.setPosterState(posterState);
        Dao.saveEdited(film);
    }

    public static void saveTransferredFilm(FilmToWatch filmToWatch,
                                           FilmGrade grade,
                                           boolean newFilm) {
        Film existing = new Film();
        existing.setTitle(filmToWatch.getTitle());
        existing.setPosterState(PosterState.NOT_LOADED);
        existing.setYear(filmToWatch.getYear());
        existing.setGrade(grade);
        existing.setFvadfs(newFilm);
        existing.setKpId(filmToWatch.getKpId());
        existing.setPosterUrl(filmToWatch.getPosterUrl());

        Film film = Dao.saveNewAndGetResult(Film.class, existing);
        PosterFileManager2.transferPoster(filmToWatch, film);
    }
}
