package com.katyshevtseva.kikinotebook;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.web.FilmSearchEngine;
import com.katyshevtseva.kikinotebook.core.films.web.model.AdditionalInfoResponse;

import static com.katyshevtseva.kikinotebook.core.films.AdditionalInfoService.saveActors;
import static com.katyshevtseva.kikinotebook.core.films.AdditionalInfoService.saveTrailers;
import static com.katyshevtseva.kikinotebook.core.films.ToWatchService.convertResponseGenresToEntity;

public class Test {

    public static void main(String[] args) {
        int count = 1;
        for (Film film : Dao.getAllFilms()) {

            if (!film.getProcessed()) {
                try {
                    System.out.println(count + " " + film.getTitle());
                    AdditionalInfoResponse response = FilmSearchEngine.findById(film.getKpId());

                    saveTrailers(film, response);
                    saveActors(film, response);

                    film.setDescription(response.getDescription());
                    film.setLength(response.getMovieLength());
                    film.setGenres(convertResponseGenresToEntity(response.getGenres()));
                    film.setProcessed(true);
                    Dao.saveEdited(film);


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                count++;
            }

        }
    }


}
