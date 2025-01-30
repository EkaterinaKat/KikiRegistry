package com.katyshevtseva.kikinotebook;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;

import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
    }

    public static void printTrailerStatistics() {
        List<Film> films = Dao.getAllFilms();
        for (int i = 0; i < 18; i++) {
            printFilmsByNumOfTrailers(films, i);
        }
    }

    private static void printFilmsByNumOfTrailers(List<Film> films, int num) {
        List<Film> filteredFilm = films.stream()
                .filter(film -> film.getNumOfTrailers() == num)
                .collect(Collectors.toList());

        System.out.println(num + " " + filteredFilm.size());
    }

}
