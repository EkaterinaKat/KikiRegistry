package com.katyshevtseva.kikinotebook.core.films2.web.model;

import lombok.Data;

import java.util.List;

@Data
public class FilmArrayResponse {
    private List<FilmResponse> docs;
}
