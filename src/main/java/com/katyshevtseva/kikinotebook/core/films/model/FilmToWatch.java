package com.katyshevtseva.kikinotebook.core.films.model;

import com.katyshevtseva.date.DateUtils;
import com.katyshevtseva.hibernate.HasId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static com.katyshevtseva.time.TimeUtil.getTimeStringByMinutes;

@Data
@Entity
@NoArgsConstructor
public class FilmToWatch implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public FilmToWatch(String title, Integer year, String description, List<FilmGenre> genres, Integer length,
                       PosterState posterState, String posterUrl, Date addingDate) {
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
        return title + "\n(" + year + ")"
                + "\n\n" + description
                + "\n\n" + genres
                + "\n" + getTimeStringByMinutes(length)
                + "\n" + "Добавлено: " + DateUtils.READABLE_DATE_FORMAT.format(addingDate);
    }
}
