package com.katyshevtseva.kikinotebook.view.utils;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.windowbuilder.DialogInfo;
import com.katyshevtseva.fx.windowbuilder.NodeInfo;
import com.katyshevtseva.kikinotebook.core.CoreConstants;

public class ViewConstants {
    public static final DialogInfo MAIN_DIALOG = new DialogInfo(
            "/fxml/main.fxml", new Size(1000, 1700), CoreConstants.APP_NAME);
    public static final DialogInfo AUTHOR_DIALOG =
            new DialogInfo("/fxml/books/author_dialog.fxml", new Size(500, 700), "Author edit");
    public static final DialogInfo FILM_SEARCH_DIALOG2 =
            new DialogInfo("/fxml/films2/film_search.fxml", new Size(800, 1000), "Film search");
    public static final DialogInfo ALBUM_DIALOG =
            new DialogInfo("/fxml/music/album_dialog.fxml", new Size(800, 1400), "Album edit");

    public static final NodeInfo SECTION_MAIN_NODE = new NodeInfo("/fxml/section_main.fxml");
    public static final NodeInfo BOOKS_NODE = new NodeInfo("/fxml/books/main_books.fxml");
    public static final NodeInfo SERIES_NODE = new NodeInfo("/fxml/series/main_series.fxml");
    // FILMS
    public static final NodeInfo FILM_LISTS_NODE2 = new NodeInfo("/fxml/films2/film_lists.fxml");
    public static final NodeInfo FILM_STATISTICS_NODE2 = new NodeInfo("/fxml/vbox_container.fxml");
    public static final NodeInfo VIEWING_HISTORY_NODE2 = new NodeInfo("/fxml/films2/viewing_history.fxml");
    public static final NodeInfo ALL_FILMS_NODE2 = new NodeInfo("/fxml/films2/all_films.fxml");
    public static final NodeInfo TO_WATCH_NODE2 = new NodeInfo("/fxml/films2/to_watch.fxml");
    // MUSIC
    public static final NodeInfo ALBUMS_NODE = new NodeInfo("/fxml/music/albums.fxml");
    public static final NodeInfo SINGERS_AND_GENRES_NODE = new NodeInfo("/fxml/music/singers_and_genres.fxml");
}
