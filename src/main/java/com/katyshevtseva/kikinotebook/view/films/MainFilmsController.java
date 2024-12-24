package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.switchcontroller.AbstractSwitchController;
import com.katyshevtseva.fx.switchcontroller.Section;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.List;

import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.NotebookNodeInfo.*;

public class MainFilmsController extends AbstractSwitchController implements SectionController {
    @FXML
    private Pane mainPane;
    @FXML
    private HBox buttonBox;

    @FXML
    private void initialize() {
        init(getSections(), mainPane, this::placeButton);
    }

    private List<Section> getSections() {
        return Arrays.asList(
                new Section("Lists", new FilmListsController(),
                        controller -> WindowBuilder.getNode(FILM_LISTS, controller)),
                new Section("Statistics", new StatisticsController(),
                        controller -> WindowBuilder.getNode(FILM_STATISTICS, controller)),
                new Section("Viewing history", new ViewingHistoryController(),
                        controller -> WindowBuilder.getNode(VIEWING_HISTORY, controller)),
                new Section("All films", new AllFilmsController(),
                        controller -> WindowBuilder.getNode(ALL_FILMS, controller)),
                new Section("To watch", new ToWatchController(),
                        controller -> WindowBuilder.getNode(TO_WATCH, controller)));
    }

    private void placeButton(Button button) {
        buttonBox.getChildren().addAll(FxUtils.getPaneWithWidth(30), button);
    }
}
