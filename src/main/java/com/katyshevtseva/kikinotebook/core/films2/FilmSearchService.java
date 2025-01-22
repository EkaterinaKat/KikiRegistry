package com.katyshevtseva.kikinotebook.core.films2;

import com.katyshevtseva.kikinotebook.core.films2.web.FilmSearchEngine;
import com.katyshevtseva.kikinotebook.core.films2.web.model.FilmResponse;

import java.util.List;

public class FilmSearchService {

    public static List<FilmResponse> search(String str) throws Exception {
        return FilmSearchEngine.findFilms(str).getDocs();
    }
}
