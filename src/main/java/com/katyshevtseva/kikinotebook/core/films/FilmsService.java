package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGrade;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FilmsService {

    public static void save(Film existing, String title, Integer year, FilmGrade grade) {
        title = title.trim();

        if (existing == null) {
            existing = new Film();
            existing.setTitle(title);
            existing.setYear(year);
            existing.setGrade(grade);
            Dao.saveNew(existing);
        }
        existing.setTitle(title);
        existing.setYear(year);
        existing.setGrade(grade);
        Dao.saveEdited(existing);
    }

    public static List<Film> getFilms(FilmGrade grade, String searchString) {
        return Dao.findFilms(grade, searchString)
                .stream().sorted(Comparator.comparing(Film::getTitle)).collect(Collectors.toList());
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
}
