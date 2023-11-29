package com.katyshevtseva.kikinotebook.view.series;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController;
import com.katyshevtseva.fx.dialogconstructor.*;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.kikinotebook.core.series.SeriesService;
import com.katyshevtseva.kikinotebook.core.series.model.Series;
import com.katyshevtseva.kikinotebook.core.series.model.SeriesGrade;
import com.katyshevtseva.kikinotebook.core.series.model.SeriesState;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.katyshevtseva.fx.Styler.ThingToColor.*;

public class MainSeriesController implements SectionController {
    private final Map<SeriesState, BlockGridController<Series>> seriesGridControllerMap = new HashMap<>();
    @FXML
    private Button newSeriesButton;
    @FXML
    private HBox seriesPane;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button clearButton;

    @FXML
    private void initialize() {
        newSeriesButton.setOnAction(event -> openSeriesEditDialog(null));

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> updateContent());
        clearButton.setOnAction(event -> searchTextField.setText(""));

        adjustSeriesPane();
        updateContent();
    }

    private void openSeriesEditDialog(Series series) {
        boolean newSeries = series == null;
        DcTextField titleField = new DcTextField(true, newSeries ? "" : series.getTitle());
        DcComboBox<SeriesGrade> gradeDcComboBox = new DcComboBox<>(true, newSeries ? null : series.getGrade(),
                Arrays.asList(SeriesGrade.values()));
        DcComboBox<SeriesState> stateDcComboBox = new DcComboBox<>(true, newSeries ? null : series.getState(),
                Arrays.asList(SeriesState.values()));
        DcTextArea commentField = new DcTextArea(false, newSeries ? "" : series.getComment());
        DcDatePicker startDp = new DcDatePicker(false, newSeries ? null : series.getStartDate());
        DcDatePicker endDp = new DcDatePicker(false, newSeries ? null : series.getFinishDate());

        DialogConstructor.constructDialog(() -> {
            SeriesService.save(series, titleField.getValue(), stateDcComboBox.getValue(), gradeDcComboBox.getValue(),
                    commentField.getValue(), startDp.getValue(), endDp.getValue());
            updateContent();
        }, titleField, stateDcComboBox, gradeDcComboBox, commentField, startDp, endDp);
    }

    private void adjustSeriesPane() {
        int gridColumnWidth = 300;
        Size gridColumnSize = new Size(850, gridColumnWidth);
        int seriesBlockWidth = gridColumnWidth - 50;//BlockGridController FRAME_SIZE = 20; 50=20*2+10; 10 на scrollbar

        for (SeriesState state : SeriesState.values()) {
            ComponentBuilder.Component<BlockGridController<Series>> component =
                    new ComponentBuilder().setSize(gridColumnSize).getBlockGridComponent(seriesBlockWidth,
                            null, null, this::getSeriesNode);
            VBox vBox = new VBox();
            vBox.getChildren().addAll(new Label(state.toString()), component.getNode());
            seriesPane.getChildren().add(vBox);
            seriesGridControllerMap.put(state, component.getController());
        }
    }

    private Node getSeriesNode(Series series, int blockWidth) {
        HBox box = new HBox();
        box.setStyle(box.getStyle() + Styler.getColorfullStyle(BORDER, Styler.StandardColor.BLACK)
                + Styler.getColorfullStyle(BACKGROUND, series.getGrade().getColor()));
        box.setAlignment(Pos.CENTER);

        Label label = new Label(series.getFullInfo());
        label.setStyle(label.getStyle() + Styler.getColorfullStyle(TEXT, Styler.StandardColor.BLACK));
        label.setContextMenu(getSeriesContextMenu(series));
        label.setMaxWidth(blockWidth - 30); //30 на боковые отступы
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        box.getChildren().addAll(FxUtils.getPaneWithWidth(10), label, FxUtils.getPaneWithWidth(10));

        return box;
    }

    private ContextMenu getSeriesContextMenu(Series series) {
        ContextMenu menu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event1 -> openSeriesEditDialog(series));
        menu.getItems().add(editItem);

        return menu;
    }

    private void updateContent() {
        for (Map.Entry<SeriesState, BlockGridController<Series>> entry : seriesGridControllerMap.entrySet()) {
            entry.getValue().setContent(SeriesService.getSeries(entry.getKey(), searchTextField.getText()));
        }
    }
}
