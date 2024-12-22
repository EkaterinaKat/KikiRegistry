package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.general.GeneralUtils;
import com.katyshevtseva.image.ImageContainer;
import com.katyshevtseva.image.ImageContainerCache;
import com.katyshevtseva.kikinotebook.core.films.model.Film;

import java.io.File;
import java.util.Objects;

public class PosterFileManager {
    public static final String FILM_IMAGE_LOCATION = "D:\\onedrive\\central_image_storage\\films\\";
    private static final String FAILED_TO_LOAD_IMAGE = "failed_to_load_films.jpg";
    private static final String FILM_NOT_FOUND_IMAGE = "film_not_found.jpg";
    private static final String NOT_LOADED_IMAGE = "not_loaded.jpg";
    private static final String OTHER_ERROR_IMAGE = "other_error.jpg";

    private static final ImageContainerCache icc = ImageContainerCache.getInstance();

    public static ImageContainer getPoster(Film film) {
        switch (film.getPosterState()) {
            case LOADED:
                return icc.getImageContainer(formImageFileName(film), FILM_IMAGE_LOCATION, 222);
            case NOT_LOADED:
                return icc.getImageContainer(NOT_LOADED_IMAGE, FILM_IMAGE_LOCATION, 222);
            case FAILED_TO_LOAD_FILMS:
                return icc.getImageContainer(FAILED_TO_LOAD_IMAGE, FILM_IMAGE_LOCATION, 222);
            case FILM_NOT_FOUND:
                return icc.getImageContainer(FILM_NOT_FOUND_IMAGE, FILM_IMAGE_LOCATION, 222);
            case OTHER_ERROR:
                return icc.getImageContainer(OTHER_ERROR_IMAGE, FILM_IMAGE_LOCATION, 222);
        }
        throw new RuntimeException();
    }

    public static boolean filmHasPoster(Film film) {
        File[] files = Objects.requireNonNull(new File(FILM_IMAGE_LOCATION).listFiles());
        for (File file : files) {
            if (GeneralUtils.removeExtension(file.getName()).equals(film.getId().toString())) {
                return true;
            }
        }
        return false;
    }

    public static String formImageFileName(Film film) {
        return film.getId() + ".jpg";
    }
}
