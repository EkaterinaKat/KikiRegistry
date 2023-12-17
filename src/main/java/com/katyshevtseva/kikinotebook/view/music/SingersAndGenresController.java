package com.katyshevtseva.kikinotebook.view.music;

import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.kikinotebook.core.music.MusicService;
import com.katyshevtseva.kikinotebook.core.music.entity.Genre;
import com.katyshevtseva.kikinotebook.core.music.entity.Singer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SingersAndGenresController implements SectionController {
    @FXML
    private VBox singerBox;
    @FXML
    private VBox genreBox;

    @FXML
    private void initialize() {
        updateContent();
    }

    @Override
    public void update() {
        updateContent();
    }

    private void updateContent() {
        singerBox.getChildren().clear();
        genreBox.getChildren().clear();

        for (Singer singer : MusicService.getSingers()) {
            Label label = new Label(singer.getName());
            singerBox.getChildren().add(label);
        }

        for (Genre genre : MusicService.getGenres()) {
            Label label = new Label(genre.getTitle());
            genreBox.getChildren().add(label);
        }
    }
}
