package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.kikinotebook.core.films.StatisticsService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.Map;

public class StatisticsController implements WindowBuilder.FxController {
    @FXML
    private VBox root;

    @FXML
    private void initialize() {
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Film count");
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setBarGap(0.1);
//        bc.setPrefSize(1000, 900);
        yAxis.setTickLabelFormatter(new IntegerStringConverter());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Map<Integer, Integer> yearFilmCountMap = StatisticsService.getYearFilmCountMap();
        for (Map.Entry<Integer, Integer> entry : yearFilmCountMap.entrySet()) {
            series.getData().add(new XYChart.Data<>("" + entry.getKey(), entry.getValue()));
        }
        bc.getData().add(series);
        int chartWidth = yearFilmCountMap.size() * 30;
        bc.setMinWidth(chartWidth);
        bc.setMinHeight(650);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(bc);
        FxUtils.setSize(scrollPane, new Size(700, 1300));
        root.getChildren().add(scrollPane);
    }

    static class IntegerStringConverter extends StringConverter<Number> {

        public IntegerStringConverter() {
        }

        @Override
        public String toString(Number object) {
            if (object.intValue() != object.doubleValue())
                return "";
            return "" + (object.intValue());
        }

        @Override
        public Number fromString(String string) {
            Number val = Double.parseDouble(string);
            return val.intValue();
        }
    }
}
