package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController;
import com.katyshevtseva.fx.dialogconstructor.DcComboBox;
import com.katyshevtseva.fx.dialogconstructor.DcNumField;
import com.katyshevtseva.fx.dialogconstructor.DcTextField;
import com.katyshevtseva.fx.dialogconstructor.DialogConstructor;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.kikinotebook.core.films.FilmsService;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGrade;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.katyshevtseva.fx.Styler.ThingToColor.*;

public class MainFilmsController implements SectionController {
    private final Map<FilmGrade, BlockGridController<Film>> filmGridControllerMap = new HashMap<>();
    @FXML
    private Button newFilmButton;
    @FXML
    private HBox filmsPane;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button clearButton;

    @FXML
    private void initialize() {
        newFilmButton.setOnAction(event -> openFilmEditDialog(null));

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> updateContent());
        clearButton.setOnAction(event -> searchTextField.setText(""));

        adjustFilmsPane();
        updateContent();
    }

    private void openFilmEditDialog(Film film) {
        boolean newFilm = film == null;
        DcTextField titleField = new DcTextField(true, newFilm ? "" : film.getTitle());
        Long year = newFilm ? null : film.getYear() == null ? null : new Long(film.getYear());
        DcNumField yearField = new DcNumField(false, year);
        DcComboBox<FilmGrade> gradeDcComboBox = new DcComboBox<>(true, newFilm ? null : film.getGrade(),
                Arrays.asList(FilmGrade.values()));

        DialogConstructor.constructDialog(() -> {
            Integer year1 = yearField.getValue() != null ? (int) (long) yearField.getValue() : null;
            FilmsService.save(film, titleField.getValue(), year1, gradeDcComboBox.getValue());
            updateContent();
        }, titleField, yearField, gradeDcComboBox);
    }

    private void adjustFilmsPane() {
        int gridColumnWidth = 210;
        Size gridColumnSize = new Size(850, gridColumnWidth);
        int filmBlockWidth = gridColumnWidth - 50;//BlockGridController FRAME_SIZE = 20; 50=20*2+10; 10 на scrollbar

        for (FilmGrade grade : FilmGrade.values()) {
            ComponentBuilder.Component<BlockGridController<Film>> component =
                    new ComponentBuilder().setSize(gridColumnSize).getBlockGridComponent(filmBlockWidth,
                            null, null, this::getFilmNode);
            component.getController().getGridPane().setStyle(Styler.getColorfullStyle(BACKGROUND, grade.getColor()));
            filmsPane.getChildren().add(component.getNode());
            filmGridControllerMap.put(grade, component.getController());
        }
    }

    private Node getFilmNode(Film film, int blockWidth) {
        Label label = new Label(film.getTitleAndYear());
        label.setStyle(label.getStyle()
                + Styler.getColorfullStyle(BORDER, Styler.StandardColor.BLACK)
                + Styler.getColorfullStyle(TEXT, Styler.StandardColor.BLACK));
        label.setContextMenu(getFilmContextMenu(film));
        label.setMaxWidth(blockWidth);
//        label.setMinWidth(blockWidth);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    private ContextMenu getFilmContextMenu(Film film) {
        ContextMenu menu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event1 -> openFilmEditDialog(film));
        menu.getItems().add(editItem);

        return menu;
    }

    private void updateContent() {
        for (Map.Entry<FilmGrade, BlockGridController<Film>> entry : filmGridControllerMap.entrySet()) {
            entry.getValue().setContent(FilmsService.getFilms(entry.getKey(), searchTextField.getText()));
        }
    }
}
