package com.katyshevtseva.kikinotebook.core.films.web;

import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.web.model.FilmResponse;
import com.katyshevtseva.web.ImageDownloader;

import static com.katyshevtseva.kikinotebook.core.films.web.PosterFileManager.FILM_IMAGE_LOCATION;
import static com.katyshevtseva.kikinotebook.core.films.web.PosterFileManager.formImageFileName;

public class PosterLoader {

    public static void loadPoster(Film film) {
        if (PosterFileManager.filmHasPoster(film)) {
            return;
        }

        System.out.println(film.getTitle());
        try {
            FilmResponse response = SpecificFilmLoader.loadFilm(film);
            String url = response.getPoster().getUrl();
            System.out.println(url);
            ImageDownloader.download(FILM_IMAGE_LOCATION, formImageFileName(film), url);
        } catch (Exception e) {
            System.out.println("********* Не удалось загрузить постер: " + e.getMessage());
        }
        System.out.println("\n\n\n");

    }
}
