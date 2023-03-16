package com.katyshevtseva.kikinotebook.core.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @Column(name = "image_name")
    private String imageName;

    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    public Author(String name, String surname, String imageName) {
        this.name = name;
        this.surname = surname;
        this.imageName = imageName;
    }

    public Author() {
    }

    public String getFullName() {
        return name + (surname != null ? " " + surname : "");
    }
}
