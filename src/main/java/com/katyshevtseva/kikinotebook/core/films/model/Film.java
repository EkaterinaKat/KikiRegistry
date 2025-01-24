package com.katyshevtseva.kikinotebook.core.films.model;

import com.katyshevtseva.date.DateUtils;
import com.katyshevtseva.general.GeneralUtils;
import com.katyshevtseva.hibernate.HasId;
import com.katyshevtseva.kikinotebook.core.films.Service;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.time.TimeUtil.getTimeStringByMinutes;

@Data
@Entity
@AllArgsConstructor
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

    @Enumerated(EnumType.STRING)
    private FilmStatus status;

    @ElementCollection
    @CollectionTable(name = "film_dates", joinColumns = @JoinColumn(name = "film_id"))
    @Column(name = "date_")
    @Temporal(TemporalType.DATE)
    private List<Date> dates;

    @Column(name = "poster_state")
    @Enumerated(EnumType.STRING)
    private PosterState posterState;

    @Column(length = 2000)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_genres",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<FilmGenre> genres;

    private Integer length;

    @Column(name = "to_watch_adding_date")
    @Temporal(TemporalType.DATE)
    private Date toWatchAddingDate;

    //first viewed after date fixation started or just NEW
    //то есть если true первая дата из dates это дата моего первого просмотра этого фильма
    // если false первая дата из dates не является датой первого просмотра
    //todo удалить
    public Boolean fvadfs;

    public Film() {
    }

    public String getTitleAndYear() {
        if (year == null)
            return title;
        return title + " (" + year + ")";
    }

    public String getDatesString() {
        if (GeneralUtils.isEmpty(dates)) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder(" [");

        String prefix = "";
        for (Date date : dates.stream().sorted().collect(Collectors.toList())) {
            stringBuilder.append(prefix);
            prefix = ", ";
            stringBuilder.append(Service.getDateString(date));
        }
        return stringBuilder.append("]").toString();
    }

    public String getToWatchDesc() {
        return title + " (" + year + ")"
                + "\n\n" + genres
                + "\n" + getTimeStringByMinutes(length)
                + "\n\n" + description
                + "\n\n" + "Добавлено: " + DateUtils.READABLE_DATE_FORMAT.format(toWatchAddingDate);
    }
}
