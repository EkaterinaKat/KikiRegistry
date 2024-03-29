package com.katyshevtseva.kikinotebook.core.films.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "film_dates", joinColumns = @JoinColumn(name = "film_id"))
    @Column(name = "date_")
    @Temporal(TemporalType.DATE)
    private List<Date> dates;

    //first viewed after date fixation started or just NEW
    //то есть если true первая дата из dates это дата моего первого просмотра этого фильма
    // если false первая дата из dates не является датой первого просмотра
    public Boolean fvadfs;

    public String getTitleAndYear() {
        if (year == null)
            return title;
        return title + "\n(" + year + ")";
    }
}
