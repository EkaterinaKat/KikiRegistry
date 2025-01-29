package com.katyshevtseva.kikinotebook.core;

import com.katyshevtseva.kikinotebook.core.films.PosterFileManager;
import com.katyshevtseva.kikinotebook.core.films.model.Actor;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.PosterState;
import com.katyshevtseva.kikinotebook.core.films.web.ActorPhotoLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.katyshevtseva.general.GeneralUtils.isEmpty;

public class Tests {

    public static void allFilmTests() {
        testFilmStatus();
        testPosterState();
        testKpIdUniqueness();
    }

    public static void testFilmStatus() {
        for (Film film : Dao.getAllFilms()) {

            assert_(film, film.getKpId() != null, "film.getKpId()!=null");
            assert_(film, film.getPosterUrl() != null, "film.getPosterUrl()!=null");
            assert_(film, film.getTitle() != null, "film.getTitle()!=null");
            assert_(film, film.getYear() != null, "film.getYear()!=null");
            assert_(film, film.getType() != null, "film.getType()!=null");

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

        }
        System.out.println("testFilmStatus done");
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
            boolean a = PosterFileManager.filmHasPoster(film);
            boolean b = film.getPosterState() == PosterState.LOADED;
            if (a != b) {
                throw new RuntimeException(film.getTitle() + " error!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            if (!a) {
                throw new RuntimeException(film.getTitle() + " doesn't have poster");
            }
        }
        System.out.println("testPosterState done");
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
        boolean duplicatesExists = (idSet.size() < idList.size());
        if (duplicatesExists) {
            throw new RuntimeException("Id list has duplicates");
        }
        System.out.println("testKpIdUniqueness done");
    }

    public static void printActorStatistics() {
        List<Actor> actors = Dao.getAllActors();

        System.out.println("All " + actors.size());

        for (int i = 0; i < 18; i++) {
            printActorsByNumOfFilms(actors, i);
        }
    }

    private static void printActorsByNumOfFilms(List<Actor> actors, int num) {
        List<Actor> filteredActors = actors.stream()
                .filter(actor -> actor.getFilms().size() == num)
                .collect(Collectors.toList());


        System.out.println(num + " " + filteredActors.size());
        if (filteredActors.size() < 5) {
            System.out.println(filteredActors);
        }
    }

    public static void checkNumOfActorsInFilms() {
        int okCount = 0;
        int notOkCount = 0;

        for (Film film : Dao.getAllFilms()) {

            List<Actor> actors = Dao.findActors(film);
            System.out.println(film.getId() + " " + film.getTitle() + " actors:" + actors.size());

            if (film.getNumOfActors().equals(actors.size())) {
                okCount++;
            } else {
                notOkCount++;
            }
        }

        System.out.println("okCount " + okCount);
        System.out.println("notOkCount " + notOkCount);
    }

    public static void loadActorPhotos() {
        List<Actor> actors = Dao.getAllActors();
        for (Actor actor : actors) {

            if (actor.getFilms().size() > 2) {
                ActorPhotoLoader.loadActorPhoto(actor);
            }
        }
    }
}
