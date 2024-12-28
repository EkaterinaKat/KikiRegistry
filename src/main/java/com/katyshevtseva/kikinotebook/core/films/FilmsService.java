package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGrade;
import com.katyshevtseva.kikinotebook.core.films.model.FilmToWatch;
import com.katyshevtseva.kikinotebook.core.films.model.PosterState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FilmsService {

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

    public static Film saveNew(String title, Integer year, FilmGrade grade, boolean fvadfs) {
        Film existing = new Film();
        existing.setTitle(title.trim());
        existing.setPosterState(PosterState.NOT_LOADED);
        existing.setYear(year);
        existing.setGrade(grade);
        existing.setFvadfs(fvadfs);
        return Dao.saveNewAndGetResult(Film.class, existing);
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
                                           String title,
                                           Integer year,
                                           FilmGrade grade,
                                           boolean newFilm) {
        Film film = saveNew(title, year, grade, newFilm);
        PosterFileManager.transferPoster(filmToWatch, film);
    }
}
