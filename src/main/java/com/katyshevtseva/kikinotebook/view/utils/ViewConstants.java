package com.katyshevtseva.kikinotebook.view.utils;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.kikinotebook.core.CoreConstants;
import lombok.Getter;

public class ViewConstants {

    @Getter
    public enum NotebookDialogInfo implements WindowBuilder.DialogInfo {
        MAIN("/fxml/main.fxml", new Size(1000, 1700), CoreConstants.APP_NAME),
        AUTHOR_DIALOG("/fxml/books/author_dialog.fxml", new Size(500, 700), "Author edit");

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
        BOOKS("/fxml/books/books.fxml"),
        FILMS("/fxml/films.fxml"),
        RECIPES("/fxml/recipes.fxml"),
        SERIES("/fxml/series.fxml");

        private final String fullFileName;

        NotebookNodeInfo(String fullFileName) {
            this.fullFileName = fullFileName;
        }
    }
}
