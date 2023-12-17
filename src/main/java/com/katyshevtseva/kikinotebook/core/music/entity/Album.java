package com.katyshevtseva.kikinotebook.core.music.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String comment;

    private String imageName;

    private Integer year;

    @Temporal(TemporalType.DATE)
    private Date listeningDate;

    @ManyToOne
    @JoinColumn(name = "singer_id")
    private Singer singer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "album_genres",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    private boolean finished;

    public String getFullInfo() {
        StringBuilder sb = new StringBuilder();
        if (singer != null || year != null) {
            sb.append(singer).append(" (").append(year).append(")\n");
        }
        if (genres != null) {
            sb.append(genres).append("\n");
        }
        if (listeningDate != null) {
            sb.append("date: ").append(listeningDate).append("\n");
        }
        if (comment != null) {
            sb.append(comment).append("\n");
        }

        return sb.toString();
    }

    public Album(String title, String comment, String imageName, Integer year, Date listeningDate, Singer singer,
                 List<Genre> genres, boolean finished) {
        this.title = title;
        this.comment = comment;
        this.imageName = imageName;
        this.year = year;
        this.listeningDate = listeningDate;
        this.singer = singer;
        this.genres = genres;
        this.finished = finished;
    }

    public void setValues(String title, String comment, String imageName, Integer year, Date listeningDate, Singer singer,
                          List<Genre> genres, boolean finished) {
        this.title = title;
        this.comment = comment;
        this.imageName = imageName;
        this.year = year;
        this.listeningDate = listeningDate;
        this.singer = singer;
        this.genres = genres;
        this.finished = finished;
    }
}
