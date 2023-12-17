package com.katyshevtseva.kikinotebook.view;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.switchcontroller.AbstractSwitchController;
import com.katyshevtseva.fx.switchcontroller.Section;
import com.katyshevtseva.kikinotebook.view.books.MainBooksController;
import com.katyshevtseva.kikinotebook.view.films.MainFilmsController;
import com.katyshevtseva.kikinotebook.view.music.MainMusicController;
import com.katyshevtseva.kikinotebook.view.series.MainSeriesController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.NotebookNodeInfo.*;

public class MainController extends AbstractSwitchController implements FxController {
    @FXML
    private Pane mainPane;
    @FXML
    private VBox buttonBox;

    @FXML
    private void initialize() {
        init(getSections(), mainPane, this::placeButton);
    }

    private List<Section> getSections() {
        return Arrays.asList(
                new Section("Films", new MainFilmsController(),
                        controller -> WindowBuilder.getNode(FILMS, controller)),
                new Section("Music", new MainMusicController(),
                        controller -> WindowBuilder.getNode(MUSIC_MAIN, controller)),
                new Section("Series", new MainSeriesController(),
                        controller -> WindowBuilder.getNode(SERIES, controller)),
                new Section("Books", new MainBooksController(),
                        controller -> WindowBuilder.getNode(BOOKS, controller)));
    }

    private void placeButton(Button button) {
        FxUtils.setWidth(button, 180);
        buttonBox.getChildren().addAll(FxUtils.getPaneWithHeight(40), button);
    }
}