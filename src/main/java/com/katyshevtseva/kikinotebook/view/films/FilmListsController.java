package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.date.DateUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController;
import com.katyshevtseva.fx.dialogconstructor.*;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.kikinotebook.core.films.FilmsService;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGrade;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.*;

import static com.katyshevtseva.fx.Styler.ThingToColor.*;
import static com.katyshevtseva.kikinotebook.core.films.model.FilmGrade.*;

public class FilmListsController implements SectionController {
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
        DcNumField yearField = new DcNumField(true, year);
        DcComboBox<FilmGrade> gradeDcComboBox = new DcComboBox<>(true, newFilm ? null : film.getGrade(),
                Arrays.asList(FilmGrade.values()));
        DcCheckBox fvadfsBox = new DcCheckBox(newFilm || film.getFvadfs(), "NEW");

        DialogConstructor.constructDialog(() -> {
            Integer year1 = yearField.getValue() != null ? (int) (long) yearField.getValue() : null;
            FilmsService.save(film, titleField.getValue(), year1, gradeDcComboBox.getValue(), fvadfsBox.getValue());
            updateContent();
        }, titleField, yearField, gradeDcComboBox, fvadfsBox);
    }

    private void adjustFilmsPane() {
        int gridColumnWidth = 210;
        Size gridColumnSize = new Size(780, gridColumnWidth);
        Size smallGridColumnSize = new Size(380, gridColumnWidth);
        int filmBlockWidth = gridColumnWidth - 50;//BlockGridController FRAME_SIZE = 20; 50=20*2+10; 10 на scrollbar

        //FAVOURITE, EXCELLENT, GOOD, NORMAL
        for (FilmGrade grade : Arrays.asList(FAVOURITE, EXCELLENT, GOOD, NORMAL)) {
            ComponentBuilder.Component<BlockGridController<Film>> component =
                    new ComponentBuilder().setSize(gridColumnSize).getBlockGridComponent(filmBlockWidth,
                            null, null, this::getFilmNode);
            component.getController().getGridPane().setStyle(Styler.getColorfullStyle(BACKGROUND, grade.getColor()));
            VBox vBox = new VBox();
            vBox.getChildren().addAll(new Label(grade.toString()), component.getNode());
            filmsPane.getChildren().add(vBox);
            filmGridControllerMap.put(grade, component.getController());
        }

        for (List<FilmGrade> grades : Arrays.asList(Arrays.asList(SOSO, NOTCLASSIFIED), Arrays.asList(BAD, NOTFINISHED))) {
            VBox vBox = new VBox();
            for (FilmGrade grade : grades) {
                ComponentBuilder.Component<BlockGridController<Film>> component =
                        new ComponentBuilder().setSize(smallGridColumnSize).getBlockGridComponent(filmBlockWidth,
                                null, null, this::getFilmNode);
                component.getController().getGridPane().setStyle(Styler.getColorfullStyle(BACKGROUND, grade.getColor()));
                vBox.getChildren().addAll(new Label(grade.toString()), component.getNode());
                filmGridControllerMap.put(grade, component.getController());
            }
            filmsPane.getChildren().add(vBox);
        }
    }

    private Node getFilmNode(Film film, int blockWidth) {
        VBox vBox = new VBox();
        vBox.setStyle(vBox.getStyle() + Styler.getColorfullStyle(BORDER, Styler.StandardColor.BLACK));
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label(film.getTitleAndYear());
        label.setStyle(label.getStyle() + Styler.getColorfullStyle(TEXT, Styler.StandardColor.BLACK));
        label.setContextMenu(getFilmContextMenu(film));
        label.setMaxWidth(blockWidth);
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        vBox.getChildren().add(label);

        if (film.getDates() != null) {
            for (int i = 0; i < film.getDates().size(); i++) {
                Date date = film.getDates().get(i);
                String firstViewMark = (i == 0 && film.fvadfs) ? " *FV*" : "";
                Label label1 = new Label(DateUtils.READABLE_DATE_FORMAT.format(date) + firstViewMark);
                label1.setStyle(label.getStyle() + Styler.getColorfullStyle(TEXT, Styler.StandardColor.BLACK));
                label1.setContextMenu(getDateContextMenu(film, date));
                vBox.getChildren().add(label1);
            }
        }

        return vBox;
    }

    private ContextMenu getDateContextMenu(Film film, Date date) {
        ContextMenu menu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event1 -> {
            FilmsService.deleteDate(film, date);
            updateContent();
        });
        menu.getItems().add(deleteItem);

        return menu;
    }

    private ContextMenu getFilmContextMenu(Film film) {
        ContextMenu menu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event1 -> openFilmEditDialog(film));
        menu.getItems().add(editItem);

        MenuItem addDateItem = new MenuItem("Add date");
        addDateItem.setOnAction(event1 -> {
            DcDatePicker datePicker = new DcDatePicker(true, new Date());
            DialogConstructor.constructDialog(() -> {
                FilmsService.addDate(film, datePicker.getValue());
                updateContent();
            }, datePicker);
        });
        menu.getItems().add(addDateItem);

        return menu;
    }

    private void updateContent() {
        for (Map.Entry<FilmGrade, BlockGridController<Film>> entry : filmGridControllerMap.entrySet()) {
            entry.getValue().setContent(FilmsService.getFilms(entry.getKey(), searchTextField.getText()));
        }
    }
}
