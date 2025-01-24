package com.katyshevtseva.kikinotebook.core.films.web;

import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.PosterState;
import com.katyshevtseva.web.ImageDownloader;

import static com.katyshevtseva.kikinotebook.core.films.model.PosterState.*;
import static com.katyshevtseva.kikinotebook.core.films2.FilmsService2.updatePosterState;
import static com.katyshevtseva.kikinotebook.core.films2.PosterFileManager2.FILM_IMAGE_LOCATION;
import static com.katyshevtseva.kikinotebook.core.films2.PosterFileManager2.formImageFileName;

public class PosterLoader {

    public static void loadPosterBySavedUrl(Film film) {
        if (film.getPosterState() == PosterState.LOADED) {
            return;
        }
        if (film.getPosterUrl() == null) {
            updatePosterState(film, URL_NOT_FOUND);
            return;
        }

        try {
            ImageDownloader.download(FILM_IMAGE_LOCATION, formImageFileName(film), film.getPosterUrl());
            updatePosterState(film, LOADED);
        } catch (Exception e) {
            updatePosterState(film, ERROR);
        }
    }
}
