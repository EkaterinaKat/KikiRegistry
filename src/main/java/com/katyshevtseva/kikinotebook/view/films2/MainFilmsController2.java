package com.katyshevtseva.kikinotebook.view.films2;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.switchcontroller.AbstractSwitchController;
import com.katyshevtseva.fx.switchcontroller.Section;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.fx.windowbuilder.WindowBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.List;

import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.*;

public class MainFilmsController2 extends AbstractSwitchController implements SectionController {
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
                new Section("Lists", new ListsController2(),
                        controller -> WindowBuilder.getNode(FILM_LISTS_NODE2, controller)),
                new Section("Statistics", new StatisticsController2(),
                        controller -> WindowBuilder.getNode(FILM_STATISTICS_NODE2, controller)),
                new Section("Viewing history", new ViewingHistoryController2(),
                        controller -> WindowBuilder.getNode(VIEWING_HISTORY_NODE2, controller)),
                new Section("All films2", new AllFilmsController2(),
                        controller -> WindowBuilder.getNode(ALL_FILMS_NODE2, controller)),
                new Section("To watch", new ToWatchController2(),
                        controller -> WindowBuilder.getNode(TO_WATCH_NODE2, controller)));
    }

    private void placeButton(Button button) {
        buttonBox.getChildren().addAll(FxUtils.getPaneWithWidth(30), button);
    }
}
