package com.katyshevtseva.kikinotebook.core;

import com.katyshevtseva.kikinotebook.core.model.Author;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorService {

    public static List<Author> getAll() {
        return Dao.getAllAuthor();
    }

    public static List<Author> getAllSorted() {
        List<Author> all = Dao.getAllAuthor();
        List<Author> result = all.stream()
                .filter(author -> author.getImageName() != null)
                .sorted(Comparator.comparing(Author::getSortString))
                .collect(Collectors.toList());

        result.addAll(all.stream()
                .filter(author -> author.getImageName() == null)
                .sorted(Comparator.comparing(Author::getSortString))
                .collect(Collectors.toList()));

        return result;
    }

    public static void save(Author existing, String name, String surname, String fileName) {
        if (name == null) {
            throw new RuntimeException();
        }

        if (existing == null) {
            existing = new Author(name, surname, fileName);
            Dao.saveNew(existing);
        } else {
            existing.setValues(name, surname, fileName);
            Dao.saveEdited(existing);
        }
    }
}