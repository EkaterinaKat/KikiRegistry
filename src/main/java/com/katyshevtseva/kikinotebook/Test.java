package com.katyshevtseva.kikinotebook;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.Type;
import com.katyshevtseva.kikinotebook.core.films.web.FilmSearchEngine;
import com.katyshevtseva.kikinotebook.core.films.web.model.AdditionalInfoResponse;
import com.katyshevtseva.kikinotebook.core.films.web.model.PersonResponse;

import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.kikinotebook.core.Tests.checkNumOfActorsInFilms;
import static com.katyshevtseva.kikinotebook.core.Tests.printActorStatistics;
import static com.katyshevtseva.kikinotebook.core.films.AdditionalInfoService.saveActors;
import static com.katyshevtseva.kikinotebook.core.films.AdditionalInfoService.saveTrailers;
import static com.katyshevtseva.kikinotebook.core.films.ToWatchService.convertResponseGenresToEntity;

public class Test {

    public static void main(String[] args) {

    }

    private static void loadAdditionalInfo() {
        int count = 1;
        for (Film film : Dao.getAllFilms()) {
            if (!film.getProcessed()) {
                try {
                    System.out.println(count + " " + film.getTitle());
                    AdditionalInfoResponse response = FilmSearchEngine.findByKpId(film.getKpId());

                    List<PersonResponse> actors = response.getPersons().stream()
                            .filter(personResponse -> personResponse.getProfession().equals("актеры"))
                            .collect(Collectors.toList());

                    saveActors(film, actors);
                    saveTrailers(film, response);
                    film.setDescription(response.getDescription());
                    film.setLength(response.getMovieLength());
                    film.setGenres(convertResponseGenresToEntity(response.getGenres()));
                    film.setProcessed(true);
                    film.setNumOfActors(actors.size());
                    Dao.saveEdited(film);


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                count++;
            }

        }
    }


}
