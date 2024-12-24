package com.katyshevtseva.kikinotebook.core;

import com.katyshevtseva.general.GeneralUtils;
import com.katyshevtseva.hibernate.CoreDao;
import com.katyshevtseva.kikinotebook.core.books.model.Author;
import com.katyshevtseva.kikinotebook.core.books.model.Book;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGenre;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGrade;
import com.katyshevtseva.kikinotebook.core.films.model.FilmToWatch;
import com.katyshevtseva.kikinotebook.core.music.entity.Album;
import com.katyshevtseva.kikinotebook.core.music.entity.Genre;
import com.katyshevtseva.kikinotebook.core.music.entity.Singer;
import com.katyshevtseva.kikinotebook.core.series.model.Series;
import com.katyshevtseva.kikinotebook.core.series.model.SeriesState;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
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

    public static <T> Long saveNewAndGetId(T t) {
        return (Long) coreDao.saveNewAndGetId(t);
    }

    /////////////////////////////////////////////// MUSIC ///////////////////////////////////////////////

    public static List<Album> getAllAlbum() {
        return coreDao.getAll(Album.class.getSimpleName());
    }

    public static List<Singer> getAllSinger() {
        return coreDao.getAll(Singer.class.getSimpleName());
    }

    public static List<Genre> getAllGenre() {
        return coreDao.getAll(Genre.class.getSimpleName());
    }

    /////////////////////////////////////////////// FILMS ///////////////////////////////////////////////

    public static List<Film> getAllFilms() {
        return coreDao.getAll(Film.class.getSimpleName());
    }

    public static List<FilmGenre> getAllFilmGenres() {
        return coreDao.getAll(FilmGenre.class.getSimpleName());
    }

    public static List<FilmToWatch> getAllFilmsToWatch() {
        return coreDao.getAll(FilmToWatch.class.getSimpleName());
    }

    public static FilmGenre findFilmGenreByTitle(String title) {
        List<FilmGenre> genres = coreDao.find(FilmGenre.class, Restrictions.eq("title", title));
        if (genres.size() > 1) {
            throw new RuntimeException("Более одно жанра в бд имеют одинаковые названия");
        }
        return genres.isEmpty() ? null : genres.get(0);
    }

    public static FilmGenre saveNewGenre(FilmGenre genre) {
        return coreDao.saveNewAndGetResult(FilmGenre.class, genre);
    }

    public static FilmToWatch saveNewToWatchFilm(FilmToWatch film) {
        return coreDao.saveNewAndGetResult(FilmToWatch.class, film);
    }

    public static List<Film> findFilms(FilmGrade grade, String searchString) {
        if (GeneralUtils.isEmpty(searchString)) {
            return coreDao.find(Film.class, Restrictions.eq("grade", grade));
        }

        String sqlString = "select * from film " +
                "where grade = :grade " +
                "and (upper(title) like :search_string " +
                "or CAST(year AS varchar) like :search_string ) ; ";
        return coreDao.findByQuery(session -> session.createSQLQuery(sqlString)
                .addEntity(Film.class)
                .setParameter("search_string", "%" + searchString.toUpperCase() + "%")
                .setParameter("grade", grade.toString()));
    }


    public static List<Film> findFilmsViewedInYear(int year) {
        String sqlString = "select distinct f.* from film f " +
                "join film_dates d on f.id = d.film_id " +
                "where EXTRACT(YEAR FROM d.date_)=:year ; ";
        return coreDao.findByQuery(session -> session.createSQLQuery(sqlString)
                .addEntity(Film.class)
                .setParameter("year", year));
    }

    public static List<Date> getAllDatesWithFilmViews() {
        String sqlString = "select date_ from film_dates ; ";
        return coreDao.findByQuery(session -> session.createSQLQuery(sqlString));
    }

    /////////////////////////////////////////////// SERIES ///////////////////////////////////////////////
    public static List<Series> findSeries(SeriesState state, String searchString) {
        if (GeneralUtils.isEmpty(searchString)) {
            return coreDao.find(Series.class, Restrictions.eq("state", state));
        }

        String sqlString = "select * from series " +
                "where state = :state " +
                "and upper(title) like :search_string ; ";
        return coreDao.findByQuery(session -> session.createSQLQuery(sqlString)
                .addEntity(Series.class)
                .setParameter("search_string", "%" + searchString.toUpperCase() + "%")
                .setParameter("state", state.toString()));
    }

    /////////////////////////////////////////////// BOOKS ///////////////////////////////////////////////
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
