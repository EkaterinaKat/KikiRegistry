package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.kikinotebook.core.films.web.FilmSearchEngine;
import com.katyshevtseva.kikinotebook.core.films.web.model.FilmResponse;

import java.util.List;

public class FilmSearchService {

    public static List<FilmResponse> search(String str) throws Exception {
        return FilmSearchEngine.findByTitle(str).getDocs();
    }
}
