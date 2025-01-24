package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.hibernate.HasId;
import com.katyshevtseva.image.ImageContainer;
import com.katyshevtseva.image.ImageContainerCache;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films2.model.PosterState;

public class PosterFileManager {
    public static final String FILM_IMAGE_LOCATION = "D:\\onedrive\\central_image_storage\\films\\";
    private static final ImageContainerCache icc = ImageContainerCache.getInstance();

    public static ImageContainer getPoster(Film film) {
        if (film.getPosterState() == PosterState.LOADED) {
            return icc.getImageContainer(formImageFileName(film), FILM_IMAGE_LOCATION, 222);
        } else {
            return icc.getImageContainer(film.getPosterState().getImageName(), FILM_IMAGE_LOCATION, 222);
        }
    }

    public static String formImageFileName(HasId hasId) {
        return hasId.getId() + ".jpg";
    }
}
