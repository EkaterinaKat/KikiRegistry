package com.katyshevtseva.kikinotebook.core.films.web.model;

import lombok.Data;

import java.util.List;

@Data
public class AdditionalInfoResponse {

    private List<PersonResponse> persons;

    private VideosResponse videos;

    private List<GenreResponse> genres;

    private String description;

    private Integer movieLength;
}
