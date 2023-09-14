package com.katyshevtseva.kikinotebook.view;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.switchcontroller.AbstractSwitchController;
import com.katyshevtseva.fx.switchcontroller.Section;
import com.katyshevtseva.kikinotebook.view.books.MainBooksController;
import com.katyshevtseva.kikinotebook.view.films.MainFilmsController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.NotebookNodeInfo.BOOKS;
import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.NotebookNodeInfo.FILMS;

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
                new Section("Books", new MainBooksController(),
                        controller -> WindowBuilder.getNode(BOOKS, controller)));
    }

    private void placeButton(Button button) {
        FxUtils.setWidth(button, 180);
        buttonBox.getChildren().addAll(FxUtils.getPaneWithHeight(40), button);
    }
}