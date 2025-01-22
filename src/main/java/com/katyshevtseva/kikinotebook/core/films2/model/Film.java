package com.katyshevtseva.kikinotebook.core.films2.model;

import com.katyshevtseva.date.DateUtils;
import com.katyshevtseva.hibernate.HasId;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Film implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kpId;

    private String posterUrl;

    private String title;

    private Integer year;

    @Enumerated(EnumType.STRING)
    private FilmGrade grade;

    @ElementCollection
    @CollectionTable(name = "film_dates", joinColumns = @JoinColumn(name = "film_id"))
    @Column(name = "date_")
    @Temporal(TemporalType.DATE)
    private List<Date> dates;

    @Column(name = "poster_state")
    @Enumerated(EnumType.STRING)
    private PosterState posterState;

    //first viewed after date fixation started or just NEW
    //то есть если true первая дата из dates это дата моего первого просмотра этого фильма
    // если false первая дата из dates не является датой первого просмотра
    public Boolean fvadfs;

    public Film() {
    }

    public String getTitleAndYear() {
        if (year == null)
            return title;
        return title + "\n(" + year + ")";
    }

    public String getTitleAndDates() {
        StringBuilder stringBuilder = new StringBuilder(title).append(" [");
        for (Date date : dates) {
            stringBuilder.append(DateUtils.READABLE_DATE_FORMAT.format(date)).append(", ");
        }
        return stringBuilder.append("]").toString();
    }
}
