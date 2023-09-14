package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGrade;

import java.util.List;

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
        return Dao.findFilms(grade, searchString);
    }
}
