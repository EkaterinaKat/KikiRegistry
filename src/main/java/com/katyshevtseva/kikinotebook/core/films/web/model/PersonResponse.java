package com.katyshevtseva.kikinotebook.core.films.web.model;

import lombok.Data;

@Data
public class PersonResponse {
    private Long id;
    private String photo;
    private String name;
    private String enName;
    private String profession;
}
