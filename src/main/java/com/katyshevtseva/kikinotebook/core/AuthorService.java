package com.katyshevtseva.kikinotebook.core;

import com.katyshevtseva.general.GeneralUtils;
import com.katyshevtseva.kikinotebook.core.model.Author;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorService {

    public static List<Author> getAll() {
        return Dao.getAllAuthor();
    }

    public static List<Author> getAllSorted(String searchString) {
        List<Author> all = GeneralUtils.isEmpty(searchString) ? Dao.getAllAuthor() : Dao.findAuthors(searchString);
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

        name = name.trim();
        surname = surname.trim();
        if (existing == null) {
            existing = new Author(name, surname, fileName);
            Dao.saveNew(existing);
        } else {
            existing.setValues(name, surname, fileName);
            Dao.saveEdited(existing);
        }
    }
}
