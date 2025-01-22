package com.katyshevtseva.kikinotebook.core;

import com.katyshevtseva.kikinotebook.core.films2.FilmsService;
import com.katyshevtseva.kikinotebook.core.films2.PosterFileManager;
import com.katyshevtseva.kikinotebook.core.films2.model.Film;
import com.katyshevtseva.kikinotebook.core.films2.model.FilmToWatch;
import com.katyshevtseva.kikinotebook.core.films2.model.PosterState;

import java.util.*;
import java.util.stream.Collectors;

public class Tests {

    public static void testPosterState() {
        for (Film film : FilmsService.getAllFilms()) {
            boolean a = PosterFileManager.filmHasPoster(film);
            boolean b = film.getPosterState() == PosterState.LOADED;
            if (a != b) {
                System.out.println("error!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            } else {
                System.out.println("it's fine");
            }
        }
    }

    public static void testKpIdUniqueness() {
        List<Film> films = Dao.getAllFilms();
        List<FilmToWatch> filmsToWatch = Dao.getAllFilmsToWatch();
        List<Long> idList = new ArrayList<>();
        idList.addAll(films.stream().map(Film::getKpId).collect(Collectors.toList()));
        idList.addAll(filmsToWatch.stream().map(FilmToWatch::getKpId).collect(Collectors.toList()));
        idList = idList
                .stream()
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < idList.size() - 1; i++) {
            long a = idList.get(i);
            long b = idList.get(i + 1);
            if (a == b) {
                System.out.println(a);
            }
        }

        Set<Long> idSet = new HashSet<>(idList);
        System.out.println("Id list has duplicates: " + (idSet.size() < idList.size()));
    }
}
