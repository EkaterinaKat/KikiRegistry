package com.katyshevtseva.kikinotebook.core;

import com.katyshevtseva.kikinotebook.core.model.Author;

import java.util.List;

public class BooksService {

    public static List<Author> getAllAuthors() {
        return Dao.getAllAuthor();
    }

    public static void createAuthor(String name, String surname, String fileName) {
        if (name == null) {
            throw new RuntimeException();
        }

        Author author = new Author(name, surname, fileName);
        Dao.saveNew(author);
    }
}
