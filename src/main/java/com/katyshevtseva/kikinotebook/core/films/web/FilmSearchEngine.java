package com.katyshevtseva.kikinotebook.core.films.web;

import com.katyshevtseva.kikinotebook.core.films.web.exception.FailedToLoadFilmsException;
import com.katyshevtseva.kikinotebook.core.films.web.model.FilmArrayResponse;
import com.katyshevtseva.web.HttpHelper;
import com.katyshevtseva.web.Response;

import static com.katyshevtseva.web.WebUtils.encodeStringUTF8;

public class FilmSearchEngine {
    private static final String BASE_URL = "https://api.kinopoisk.dev/v1.4/movie/";
    private static final String SEARCH_URL = "search?page=1&limit=10&query=%s&token=%s";
    private static final String TOKEN = "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB";

    public static FilmArrayResponse findFilms(String titleQuery) throws Exception {
        String query = BASE_URL + String.format(SEARCH_URL, encodeStringUTF8(titleQuery), TOKEN);
        Response response = HttpHelper.get(query);
        if (response.codeIsPositive()) {
            return response.parceBody(FilmArrayResponse.class);
        } else {
            throw new FailedToLoadFilmsException("Не удалось загрузить фильмы");
        }
    }
}
