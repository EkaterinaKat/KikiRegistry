package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Actor;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.Trailer;
import com.katyshevtseva.kikinotebook.core.films.web.model.AdditionalInfoResponse;
import com.katyshevtseva.kikinotebook.core.films.web.model.PersonResponse;
import com.katyshevtseva.kikinotebook.core.films.web.model.TrailerResponse;

import java.util.HashSet;
import java.util.Set;

public class AdditionalInfoService {

    public static void saveTrailers(Film film, AdditionalInfoResponse response) {
        if (response.getVideos() == null) {
            return;
        }
        for (TrailerResponse trailerResponse : response.getVideos().getTrailers()) {
            Trailer trailer = new Trailer(
                    null,
                    trailerResponse.getUrl(),
                    trailerResponse.getName(),
                    trailerResponse.getSite(),
                    trailerResponse.getType(),
                    film
            );
            Dao.saveNew(trailer);
        }
    }

    public static void saveActors(Film film, AdditionalInfoResponse response) {
        for (PersonResponse personResponse : response.getPersons()) {
            if (personResponse.getProfession().equals("актеры")) {
                Actor existing = Dao.findActorByKpId(personResponse.getId());
                if (existing != null) {
                    if (existing.getFilms() == null) {
                        existing.setFilms(new HashSet<>());
                    }
                    existing.getFilms().add(film);
                    Dao.saveEdited(existing);
                } else {
                    Set<Film> films = new HashSet<>();
                    films.add(film);
                    Actor actor = new Actor(
                            null,
                            personResponse.getId(),
                            personResponse.getPhoto(),
                            personResponse.getName(),
                            personResponse.getEnName(),
                            films
                    );
                    Dao.saveNew(actor);
                }
            }
        }
    }
}
