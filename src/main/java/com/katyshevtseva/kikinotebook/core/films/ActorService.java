package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Actor;
import com.katyshevtseva.kikinotebook.core.films.model.Film;

import java.util.List;

public class ActorService {

    public static List<Actor> findActors(Film film) {
        return Dao.findActors(film);
    }
}
