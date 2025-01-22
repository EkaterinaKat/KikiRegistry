package com.katyshevtseva.kikinotebook.core.films2;

import com.katyshevtseva.file.FileUtil;
import com.katyshevtseva.general.GeneralUtils;
import com.katyshevtseva.hibernate.HasId;
import com.katyshevtseva.image.ImageContainer;
import com.katyshevtseva.image.ImageContainerCache;
import com.katyshevtseva.kikinotebook.core.films2.model.Film;
import com.katyshevtseva.kikinotebook.core.films2.model.FilmToWatch;
import com.katyshevtseva.kikinotebook.core.films2.model.PosterState;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.katyshevtseva.kikinotebook.core.films2.FilmsService2.updatePosterState;
import static com.katyshevtseva.kikinotebook.core.films2.model.PosterState.LOADED;
import static com.katyshevtseva.kikinotebook.core.films2.model.PosterState.OTHER_ERROR;

public class PosterFileManager2 {
    public static final String FILM_IMAGE_LOCATION = "D:\\onedrive\\central_image_storage\\films\\";
    public static final String TO_WATCH_FILM_IMAGE_LOCATION = "D:\\onedrive\\central_image_storage\\films\\to_watch\\";
    private static final ImageContainerCache icc = ImageContainerCache.getInstance();

    public static void transferPoster(FilmToWatch filmToWatch, Film film) {
        try {
            FileUtil.copy(
                    TO_WATCH_FILM_IMAGE_LOCATION + formImageFileName(filmToWatch),
                    FILM_IMAGE_LOCATION + film.getId() + ".jpg"
            );
            updatePosterState(film, LOADED);
        } catch (IOException e) {
            updatePosterState(film, OTHER_ERROR);
        }
    }

    public static ImageContainer getPoster(Film film) {
        if (film.getPosterState() == PosterState.LOADED) {
            return icc.getImageContainer(formImageFileName(film), FILM_IMAGE_LOCATION, 222);
        } else {
            return icc.getImageContainer(film.getPosterState().getImageName(), FILM_IMAGE_LOCATION, 222);
        }
    }

    public static ImageContainer getPoster(FilmToWatch film) {
        if (film.getPosterState() == PosterState.LOADED) {
            return icc.getImageContainer(formImageFileName(film), TO_WATCH_FILM_IMAGE_LOCATION, 222);
        } else {
            return icc.getImageContainer(film.getPosterState().getImageName(), TO_WATCH_FILM_IMAGE_LOCATION, 222);
        }
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

    public static String formImageFileName(HasId hasId) {
        return hasId.getId() + ".jpg";
    }
}
