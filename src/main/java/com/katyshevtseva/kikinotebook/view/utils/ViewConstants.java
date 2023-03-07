package com.katyshevtseva.kikinotebook.view.utils;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.kikinotebook.core.CoreConstants;
import lombok.Getter;

public class ViewConstants {

    @Getter
    public enum NotebookDialogInfo implements WindowBuilder.DialogInfo {
        MAIN("/fxml/main.fxml", new Size(1000, 1200), CoreConstants.APP_NAME);

        private final String fileName;
        private final Size size;
        private final String title;

        NotebookDialogInfo(String fileName, Size size, String title) {
            this.fileName = fileName;
            this.size = size;
            this.title = title;
        }
    }

    @Getter
    public enum NotebookNodeInfo implements WindowBuilder.NodeInfo {
        BOOKS("/fxml/books.fxml"),
        FILMS("/fxml/films.fxml"),
        RECIPES("/fxml/recipes.fxml"),
        SERIES("/fxml/series.fxml");

        private final String fileName;

        NotebookNodeInfo(String fileName) {
            this.fileName = fileName;
        }
    }
}
