package com.katyshevtseva.kikinotebook.core;

import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.PosterState;
import com.katyshevtseva.kikinotebook.core.films2.PosterFileManager2;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.katyshevtseva.general.GeneralUtils.isEmpty;

public class Tests {

    public static void testFilmStatus() {
        for (Film film : Dao.getAllFilms()) {

            assert_(film, film.getKpId() != null, "film.getKpId()!=null");
            assert_(film, film.getPosterUrl() != null, "film.getPosterUrl()!=null");
            assert_(film, film.getTitle() != null, "film.getTitle()!=null");
            assert_(film, film.getYear() != null, "film.getYear()!=null");

            switch (film.getStatus()) {
                case WATCHED:
                    testWatchedFilmState(film);
                    break;
                case TO_WATCH:
                    testToWatchFilmState(film);
                    break;
                case WATCHED_AND_TO_WATCH:
                    testWatchedAndToWatchFilmState(film);
                    break;
            }
            System.out.println(film.getTitle() + " tested");
        }
    }

    public static void testWatchedFilmState(Film film) {
        assert_(film, film.getGrade() != null, "WATCHED film.getGrade()!=null");
        assert_(film, !isEmpty(film.getDates()), "WATCHED !isEmpty(film.getDates())");
        assert_(film, film.getToWatchAddingDate() == null, "WATCHED film.getToWatchAddingDate()==null");
    }

    public static void testToWatchFilmState(Film film) {
        assert_(film, film.getGrade() == null, "TO_WATCH film.getGrade()==null");
        assert_(film, isEmpty(film.getDates()), "TO_WATCH isEmpty(film.getDates())");
        assert_(film, film.getToWatchAddingDate() != null, "WATCHED film.getToWatchAddingDate()!=null");
    }

    public static void testWatchedAndToWatchFilmState(Film film) {
        assert_(film, film.getGrade() != null, "WATCHED film.getGrade()!=null");
        assert_(film, !isEmpty(film.getDates()), "WATCHED !isEmpty(film.getDates())");
        assert_(film, film.getToWatchAddingDate() != null, "WATCHED film.getToWatchAddingDate()!=null");
    }

    private static void assert_(Film film, boolean b, String desc) {
        if (!b) {
            throw new RuntimeException(film.getTitle() + ": " + desc);
        }
    }

    public static void testPosterState() {
        for (Film film : Dao.getAllFilms()) {
            boolean a = PosterFileManager2.filmHasPoster(film);
            boolean b = film.getPosterState() == PosterState.LOADED;
            if (a != b) {
                System.out.println(film.getTitle() + " error!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            if (!a) {
                System.out.println(film.getTitle() + " doesn't have poster");
            }
        }
    }

    public static void testKpIdUniqueness() {
        List<Film> films = Dao.getAllFilms();
        List<Long> idList = films.stream()
                .map(Film::getKpId)
                .collect(Collectors.toList());
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
