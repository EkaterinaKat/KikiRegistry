package com.katyshevtseva.kikinotebook.core.films.web;

import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.PosterState;
import com.katyshevtseva.web.ImageDownloader;

import static com.katyshevtseva.kikinotebook.core.films.PosterFileManager.FILM_IMAGE_LOCATION;
import static com.katyshevtseva.kikinotebook.core.films.PosterFileManager.formImageFileName;
import static com.katyshevtseva.kikinotebook.core.films.Service.updatePosterState;
import static com.katyshevtseva.kikinotebook.core.films.model.PosterState.*;

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
