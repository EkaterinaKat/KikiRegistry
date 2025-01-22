package com.katyshevtseva.kikinotebook.view.films2;

import com.katyshevtseva.general.OneArgKnob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;

import static com.katyshevtseva.kikinotebook.core.films2.ViewingHistoryService2.getIndexOfCurrentYear;
import static com.katyshevtseva.kikinotebook.core.films2.ViewingHistoryService2.getYearsForDatePicker;

public class YearPicker2 {
    private final Button leftArrow;
    private final Label yearLabel;
    private final Button rightArrow;
    private final OneArgKnob<Integer> onYearSelectListener;

    List<Integer> years;
    private Integer selectedYearIndex;

    public YearPicker2(Button leftArrow, Label yearLabel, Button rightArrow, OneArgKnob<Integer> onYearSelectListener) {
        this.leftArrow = leftArrow;
        this.yearLabel = yearLabel;
        this.rightArrow = rightArrow;
        this.onYearSelectListener = onYearSelectListener;
        tunePicker();
    }

    private void tunePicker() {
        years = getYearsForDatePicker();
        selectYear(getIndexOfCurrentYear(years));

        leftArrow.setOnMouseClicked(event -> selectYear(selectedYearIndex - 1));
        rightArrow.setOnMouseClicked(event -> selectYear(selectedYearIndex + 1));
    }

    private void selectYear(Integer index) {
        selectedYearIndex = index;
        Integer selectedYear = years.get(selectedYearIndex);
        yearLabel.setText(selectedYear.toString());
        onYearSelectListener.execute(selectedYear);
        leftArrow.setDisable(selectedYearIndex == 0);
        rightArrow.setDisable(selectedYearIndex == years.size() - 1);
    }
}
