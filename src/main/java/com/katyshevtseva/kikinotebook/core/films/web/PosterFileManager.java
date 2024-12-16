package com.katyshevtseva.kikinotebook.core.films.web;

import com.katyshevtseva.general.GeneralUtils;
import com.katyshevtseva.image.ImageContainer;
import com.katyshevtseva.image.ImageContainerCache;
import com.katyshevtseva.kikinotebook.core.films.model.Film;

import java.io.File;
import java.util.Objects;

public class PosterFileManager {
    static final String FILM_IMAGE_LOCATION = "D:\\onedrive\\central_image_storage\\films\\";

    private static final ImageContainerCache icc = ImageContainerCache.getInstance();

    public static ImageContainer getPoster(Film film) {
        return icc.getImageContainer(formImageFileName(film), FILM_IMAGE_LOCATION, 222);
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

    static String formImageFileName(Film film) {
        return film.getId() + ".jpg";
    }
}
