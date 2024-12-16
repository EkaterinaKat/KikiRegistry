package com.katyshevtseva.kikinotebook.core.films.web;

import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.web.model.FilmArrayResponse;
import com.katyshevtseva.kikinotebook.core.films.web.model.FilmResponse;
import com.katyshevtseva.web.HttpHelper;
import com.katyshevtseva.web.Response;
import com.katyshevtseva.web.WebException;

import static com.katyshevtseva.web.WebUtils.encodeStringUTF8;

public class FilmLoader {
    private static final String BASE_URL = "https://api.kinopoisk.dev/v1.4/movie/";
    private static final String SEARCH_URL = "search?page=1&limit=10&query=%s&token=%s";
    private static final String TOKEN = "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB";

    public static FilmResponse loadFilm(Film film) throws Exception {
        String query = BASE_URL + String.format(SEARCH_URL, encodeStringUTF8(film.getTitle()), TOKEN);
        Response response = HttpHelper.get(query);
        if (response.codeIsPositive()) {
            FilmArrayResponse movies = response.parceBody(FilmArrayResponse.class);
            for (FilmResponse filmResponse : movies.getDocs()) {
                if (checkIfItSameFilm(film, filmResponse)) {
                    return filmResponse;
                }
            }
        } else {
            throw new WebException("Не удалось загрузить фильм");
        }
        throw new Exception("Фильм не найден");
    }

    private static boolean checkIfItSameFilm(Film film, FilmResponse filmResponse) {
        return film.getTitle().equals(filmResponse.getName())
                && film.getYear().equals(filmResponse.getYear());
    }
}
