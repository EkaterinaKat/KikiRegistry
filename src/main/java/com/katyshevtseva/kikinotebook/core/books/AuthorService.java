package com.katyshevtseva.kikinotebook.core.books;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.books.model.Author;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.general.GeneralUtils.isEmpty;

public class AuthorService {

    public static List<Author> getAll() {
        return Dao.getAllAuthor();
    }

    public static List<Author> getAllSorted(String searchString) {
        List<Author> all = isEmpty(searchString) ? Dao.getAllAuthor() : Dao.findAuthors(searchString);
        all = all.stream().sorted(Comparator.comparing(AuthorService::getSortingString)).collect(Collectors.toList());

        List<Author> hasImage = new ArrayList<>();
        List<Author> hasntImage = new ArrayList<>();

        for (Author author : all) {
            if (author.getImageName() != null) {
                hasImage.add(author);
            } else {
                hasntImage.add(author);
            }
        }

        return new ArrayList<Author>() {{
            addAll(hasImage);
            addAll(hasntImage);
        }};
    }

    public static String getSortingString(Author author) {
        if (!isEmpty(author.getSurname()))
            return author.getSurname();
        if (!isEmpty(author.getName()))
            return author.getName();
        throw new RuntimeException();
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
