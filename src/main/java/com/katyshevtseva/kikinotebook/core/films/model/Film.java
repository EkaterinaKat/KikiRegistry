package com.katyshevtseva.kikinotebook.core.films.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer year;

    @Enumerated(EnumType.STRING)
    private FilmGrade grade;

    public String getTitleAndYear() {
        if (year == null)
            return title;
        return title + "\n(" + year + ")";
    }
}
