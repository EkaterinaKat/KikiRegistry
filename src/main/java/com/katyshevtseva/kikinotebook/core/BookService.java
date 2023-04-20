package com.katyshevtseva.kikinotebook.core;

import com.katyshevtseva.kikinotebook.core.model.Author;
import com.katyshevtseva.kikinotebook.core.model.Book;
import com.katyshevtseva.kikinotebook.core.model.BookAction;

import java.util.Date;
import java.util.List;

public class BookService {

    public static void save(Book existing, String name, Author author, BookAction action, boolean favorite, Date date) {
        if (name == null || author == null || action == null) {
            throw new RuntimeException();
        }
        if (existing != null && !existing.getAuthor().equals(author)) {
            throw new RuntimeException();
        }

        name = name.trim();
        if (existing == null) {
            existing = new Book(name, author, action, date, favorite);
            Dao.saveNew(existing);
        } else {
            existing.setValues(name, action, date, favorite);
            Dao.saveEdited(existing);
        }
    }

    public static List<Book> findByAuthor(Author author) {
        return Dao.findBooks(author);
    }
}
