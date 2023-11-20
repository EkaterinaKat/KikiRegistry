package com.katyshevtseva.kikinotebook.core.books.model;

import lombok.Data;

import javax.persistence.*;

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

    public Author(String name, String surname, String imageName) {
        this.name = name;
        this.surname = surname;
        this.imageName = imageName;
    }

    public void setValues(String name, String surname, String imageName) {
        this.name = name;
        this.surname = surname;
        this.imageName = imageName;
    }

    public Author() {
    }

    public String getFullName() {
        return name + (surname != null ? " " + surname : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return id.equals(author.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
