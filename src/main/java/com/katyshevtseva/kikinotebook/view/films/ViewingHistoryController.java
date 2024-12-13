package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.switchcontroller.SectionController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ViewingHistoryController implements SectionController {
    private Integer currentYear;
    private Integer leftYear;
    private Integer rightYear;
    @FXML
    private Pane mainPane;
    @FXML
    private Button leftArrow;
    @FXML
    private Label yearLabel;
    @FXML
    private Button rightArrow;

    @FXML
    private void initialize() {
        new YearPicker(leftArrow, yearLabel, rightArrow, this::onYearSelected);
    }

    private void onYearSelected(Integer year) {
        System.out.println("Year selected " + year);

    }
}
