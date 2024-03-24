package com.katyshevtseva.kikinotebook.view.utils;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.kikinotebook.core.CoreConstants;
import lombok.Getter;

public class ViewConstants {

    @Getter
    public enum NotebookDialogInfo implements WindowBuilder.DialogInfo {
        MAIN("/fxml/main.fxml", new Size(1000, 1700), CoreConstants.APP_NAME),
        FILM_STATISTICS("/fxml/vbox_container.fxml", new Size(900, 1500), "Statistics"),
        AUTHOR_DIALOG("/fxml/books/author_dialog.fxml", new Size(500, 700), "Author edit"),
        ALBUM_DIALOG("/fxml/music/album_dialog.fxml", new Size(800, 1400), "Album edit");

        private final String fullFileName;
        private final Size size;
        private final String title;

        NotebookDialogInfo(String fullFileName, Size size, String title) {
            this.fullFileName = fullFileName;
            this.size = size;
            this.title = title;
        }
    }

    @Getter
    public enum NotebookNodeInfo implements WindowBuilder.NodeInfo {
        BOOKS("/fxml/books/main_books.fxml"),
        SERIES("/fxml/series/main_series.fxml"),
        FILMS("/fxml/films/main_films.fxml"),
        MUSIC_MAIN("/fxml/section_main.fxml"),
        ALBUMS("/fxml/music/albums.fxml"),
        SINGERS_AND_GENRES("/fxml/music/singers_and_genres.fxml");

        private final String fullFileName;

        NotebookNodeInfo(String fullFileName) {
            this.fullFileName = fullFileName;
        }
    }
}
