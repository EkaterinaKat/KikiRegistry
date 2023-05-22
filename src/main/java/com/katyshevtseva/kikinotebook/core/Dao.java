package com.katyshevtseva.kikinotebook.core;

import com.katyshevtseva.general.GeneralUtils;
import com.katyshevtseva.hibernate.CoreDao;
import com.katyshevtseva.kikinotebook.core.model.Author;
import com.katyshevtseva.kikinotebook.core.model.Book;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class Dao {
    private static final CoreDao coreDao = new CoreDao();

    public static <T> void saveNew(T t) {
        coreDao.saveNew(t);
    }

    public static <T> void saveEdited(T t) {
        coreDao.update(t);
    }

    public static <T> void delete(T t) {
        coreDao.delete(t);
    }

    public static List<Author> getAllAuthor() {
        return coreDao.getAll(Author.class.getSimpleName());
    }

    public static List<Author> findAuthors(String searchString) {
        String sqlString = "select distinct a.* from author a " +
                "join book b on b.author_id = a.id " +
                "where upper(a.name) like :search_string " +
                "or upper(a.surname) like :search_string " +
                "or upper(b.name) like :search_string ; ";
        return coreDao.findByQuery(session -> session.createSQLQuery(sqlString)
                .addEntity(Author.class)
                .setParameter("search_string", "%" + searchString.toUpperCase() + "%"));
    }

    public static List<Book> findBooks(@NotNull Author author, @Nullable String searchString) {
        if (GeneralUtils.isEmpty(searchString)) {
            return coreDao.find(Book.class, Restrictions.eq("author", author));
        }

        String sqlString = "select * from book " +
                "where author_id = :author_id " +
                "and upper(name) like :search_string ; ";
        return coreDao.findByQuery(session -> session.createSQLQuery(sqlString)
                .addEntity(Book.class)
                .setParameter("search_string", "%" + searchString.toUpperCase() + "%")
                .setParameter("author_id", author.getId()));
    }
}
