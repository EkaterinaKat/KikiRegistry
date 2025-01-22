package com.katyshevtseva.kikinotebook.core.films2.model;

import com.katyshevtseva.hibernate.HasId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "film_genre")
@NoArgsConstructor
public class FilmGenre implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public FilmGenre(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
