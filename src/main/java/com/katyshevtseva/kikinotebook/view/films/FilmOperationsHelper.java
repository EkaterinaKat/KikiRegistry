package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.general.NoArgsKnob;
import com.katyshevtseva.kikinotebook.core.films.StatusService;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class FilmOperationsHelper {

    static void fillButtonBox(Film film, HBox buttonBox, NoArgsKnob onDeleteListener) {
        switch (film.getStatus()) {
            case WATCHED:
                buttonBox.getChildren().addAll(getWantToWatchButton(film));
                break;
            case TO_WATCH:
                buttonBox.getChildren().addAll(getDeleteFromToWatchButton(film, onDeleteListener));
                break;
            case WATCHED_AND_TO_WATCH:
                buttonBox.getChildren().addAll(getDeleteFromToWatchButton(film, onDeleteListener));

        }
    }

    private static Button getWantToWatchButton(Film film) {
        Button button = new Button("Want to watch");
        button.setOnMouseClicked(event -> StatusService.wantToWatchFilm(film));
        return button;
    }

    private static Button getDeleteFromToWatchButton(Film film, NoArgsKnob onDeleteListener) {
        Button button = new Button("Delete from to watch");
        button.setOnMouseClicked(event ->
                new StandardDialogBuilder().openQuestionDialog("Delete?", b -> {
                    if (b) {
                        StatusService.deleteFromToWatch(film);
                        onDeleteListener.execute();
                    }
                }));
        return button;
    }
}
