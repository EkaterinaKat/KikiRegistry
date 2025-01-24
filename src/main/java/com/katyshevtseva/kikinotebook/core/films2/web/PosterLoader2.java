package com.katyshevtseva.kikinotebook.core.films2.web;

import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.PosterState;
import com.katyshevtseva.kikinotebook.core.films2.FilmToWatchService2;
import com.katyshevtseva.kikinotebook.core.films2.model.FilmToWatch;
import com.katyshevtseva.kikinotebook.core.films2.web.model.FilmResponse;
import com.katyshevtseva.web.ImageDownloader;

import static com.katyshevtseva.kikinotebook.core.films.model.PosterState.ERROR;
import static com.katyshevtseva.kikinotebook.core.films.model.PosterState.LOADED;
import static com.katyshevtseva.kikinotebook.core.films2.FilmsService2.updatePosterState;
import static com.katyshevtseva.kikinotebook.core.films2.PosterFileManager2.*;

public class PosterLoader2 {

    public static void loadPoster(FilmToWatch filmToWatch) {
        if (filmToWatch.getPosterState() == PosterState.LOADED) {
            return;
        }

        System.out.println(filmToWatch.getTitle());
        try {
            System.out.println(filmToWatch.getPosterUrl());
            ImageDownloader.download(TO_WATCH_FILM_IMAGE_LOCATION, formImageFileName(filmToWatch), filmToWatch.getPosterUrl());
            FilmToWatchService2.updatePosterState(filmToWatch, LOADED);
        } catch (Exception e) {
            System.out.println("Some other exception");
            FilmToWatchService2.updatePosterState(filmToWatch, ERROR);
        }
        System.out.println("\n\n");
    }

    public static void loadPoster(Film film) {
        if (film.getPosterState() == PosterState.LOADED) {
            return;
        }

        System.out.println(film.getTitle());
        try {
            FilmResponse response = SpecificFilmLoader.loadFilm(film);
            String url = response.getPoster().getUrl();
            System.out.println(url);
            ImageDownloader.download(FILM_IMAGE_LOCATION, formImageFileName(film), url);
            updatePosterState(film, LOADED);
        } catch (Exception e) {
            updatePosterState(film, ERROR);
        }
        System.out.println("\n\n");

    }

    public static void loadPosterBySavedUrl(Film film) {
        if (film.getPosterState() == PosterState.LOADED) {
            return;
        }

        System.out.println(film.getTitle());
        try {
            ImageDownloader.download(FILM_IMAGE_LOCATION, formImageFileName(film), film.getPosterUrl());
            updatePosterState(film, LOADED);
        } catch (Exception e) {
            System.out.println("Some other exception");
            updatePosterState(film, ERROR);
        }
        System.out.println("\n\n");

    }
}
