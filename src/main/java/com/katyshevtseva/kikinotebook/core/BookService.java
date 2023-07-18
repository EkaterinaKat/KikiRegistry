package com.katyshevtseva.kikinotebook.core;

import com.katyshevtseva.kikinotebook.core.model.Author;
import com.katyshevtseva.kikinotebook.core.model.Book;
import com.katyshevtseva.kikinotebook.core.model.BookAction;
import com.katyshevtseva.kikinotebook.core.model.BookGrade;

import java.util.Date;
import java.util.List;

public class BookService {

    public static void save(Book existing, String name, Author author, BookAction action, BookGrade grade, Date date) {
        if (name == null || author == null || action == null) {
            throw new RuntimeException();
        }
        if (existing != null && !existing.getAuthor().equals(author)) {
            throw new RuntimeException();
        }

        name = name.trim();
        if (existing == null) {
            existing = new Book(name, author, action, date, grade);
            Dao.saveNew(existing);
        } else {
            existing.setValues(name, action, date, grade);
            Dao.saveEdited(existing);
        }
    }

    public static List<Book> find(Author author, String searchString) {
        return Dao.findBooks(author, searchString);
    }
}
