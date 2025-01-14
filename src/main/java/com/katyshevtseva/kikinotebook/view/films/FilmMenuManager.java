package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.fx.dialogconstructor.*;
import com.katyshevtseva.general.NoArgsKnob;
import com.katyshevtseva.kikinotebook.core.films.FilmToWatchService;
import com.katyshevtseva.kikinotebook.core.films.FilmsService;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.FilmGrade;
import com.katyshevtseva.kikinotebook.core.films.model.FilmToWatch;
import com.katyshevtseva.kikinotebook.core.films.web.PosterLoader;
import javafx.scene.control.MenuItem;

import java.util.Arrays;
import java.util.Date;

public class FilmMenuManager {

    static MenuItem getLoadPosterItem(Film film, NoArgsKnob knob) {
        MenuItem loadPosterItem = new MenuItem("Load poster");
        loadPosterItem.setOnAction(event1 -> {
            PosterLoader.loadPoster(film);
            knob.execute();
        });
        return loadPosterItem;
    }

    static MenuItem getEditItem(Film film, NoArgsKnob knob) {
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event1 -> openFilmEditDialog(film, knob));
        return editItem;
    }

    static MenuItem getAddDateItem(Film film, NoArgsKnob knob) {
        MenuItem addDateItem = new MenuItem("Add date");
        addDateItem.setOnAction(event1 -> {
            DcDatePicker datePicker = new DcDatePicker(true, new Date());
            DialogConstructor.constructDialog(() -> {
                FilmsService.addDate(film, datePicker.getValue());
                knob.execute();
            }, datePicker);
        });
        return addDateItem;
    }

    static void openFilmEditDialog(Film film, NoArgsKnob knob) {
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
            knob.execute();
        }, titleField, yearField, gradeDcComboBox, fvadfsBox);
    }

    static MenuItem getLoadPosterItem(FilmToWatch film, NoArgsKnob knob) {
        MenuItem loadPosterItem = new MenuItem("Load poster");
        loadPosterItem.setOnAction(event1 -> {
            PosterLoader.loadPoster(film);
            knob.execute();
        });
        return loadPosterItem;
    }

    static MenuItem getDeleteItem(FilmToWatch film, NoArgsKnob knob) {
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event1 -> new StandardDialogBuilder().openQuestionDialog("Delete?", b -> {
            if (b) {
                FilmToWatchService.delete(film);
                knob.execute();
            }
        }));
        return deleteItem;
    }

    static MenuItem getCreateWatchedFilmItem(FilmToWatch film, NoArgsKnob knob) {
        MenuItem item = new MenuItem("Create watched film");
        item.setOnAction(event1 -> openFilmTransferDialog(film, knob));
        return item;
    }

    static void openFilmTransferDialog(FilmToWatch film, NoArgsKnob knob) {
        DcTextField titleField = new DcTextField(true, film.getTitle());
        DcNumField yearField = new DcNumField(true, film.getYear().longValue());
        DcComboBox<FilmGrade> gradeDcComboBox = new DcComboBox<>(
                true, null, Arrays.asList(FilmGrade.values()));
        DcCheckBox fvadfsBox = new DcCheckBox(true, "NEW");

        DialogConstructor.constructDialog(() -> {
            FilmsService.saveTransferredFilm(
                    film,
                    titleField.getValue(),
                    yearField.getValue().intValue(),
                    gradeDcComboBox.getValue(),
                    fvadfsBox.getValue());
            FilmToWatchService.delete(film);
            knob.execute();
        }, titleField, yearField, gradeDcComboBox, fvadfsBox);
    }
}
