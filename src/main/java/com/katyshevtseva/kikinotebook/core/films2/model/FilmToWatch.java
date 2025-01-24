package com.katyshevtseva.kikinotebook.core.films2.model;

import com.katyshevtseva.date.DateUtils;
import com.katyshevtseva.hibernate.HasId;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGenre;
import com.katyshevtseva.kikinotebook.core.films.model.PosterState;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static com.katyshevtseva.time.TimeUtil.getTimeStringByMinutes;

@Data
@Entity
public class FilmToWatch implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kpId;

    private String title;

    private Integer year;

    @Column(length = 2000)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "film_genres",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<FilmGenre> genres;

    private Integer length;

    @Column(name = "poster_state")
    @Enumerated(EnumType.STRING)
    private PosterState posterState;

    private String posterUrl;

    @Column(name = "adding_date")
    @Temporal(TemporalType.DATE)
    private Date addingDate;

    public FilmToWatch() {

    }

    public FilmToWatch(Long kpId, String title, Integer year, String description, List<FilmGenre> genres, Integer length,
                       PosterState posterState, String posterUrl, Date addingDate) {
        this.kpId = kpId;
        this.title = title;
        this.year = year;
        this.description = description;
        this.genres = genres;
        this.length = length;
        this.posterState = posterState;
        this.posterUrl = posterUrl;
        this.addingDate = addingDate;
    }

    public String getTitleAndYear() {
        return title + "\n(" + year + ")";
    }

    public String getFullDesc() {
        return title + " (" + year + ")"
                + "\n\n" + genres
                + "\n" + getTimeStringByMinutes(length)
                + "\n\n" + description
                + "\n\n" + "Добавлено: " + DateUtils.READABLE_DATE_FORMAT.format(addingDate);
    }
}
