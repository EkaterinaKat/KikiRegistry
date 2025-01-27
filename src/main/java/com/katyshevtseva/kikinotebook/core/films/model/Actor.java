package com.katyshevtseva.kikinotebook.core.films.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kpId;

    private String photoUrl;

    private String name;

    private String enName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_actors",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"))
    private Set<Film> films;

    @Override
    public String toString() {
        return name;
    }
}
