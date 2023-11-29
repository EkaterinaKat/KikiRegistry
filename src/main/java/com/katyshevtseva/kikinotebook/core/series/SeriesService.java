package com.katyshevtseva.kikinotebook.core.series;

import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.series.model.Series;
import com.katyshevtseva.kikinotebook.core.series.model.SeriesGrade;
import com.katyshevtseva.kikinotebook.core.series.model.SeriesState;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SeriesService {

    public static void save(Series existing, String title, SeriesState state, SeriesGrade grade,
                            String comment, Date start, Date end) {
        title = title.trim();
        comment = comment.trim();

        if (existing == null) {
            existing = new Series();
            existing.setValues(title, state, grade, comment, start, end);
            Dao.saveNew(existing);
        }
        existing.setValues(title, state, grade, comment, start, end);
        Dao.saveEdited(existing);
    }

    public static List<Series> getSeries(SeriesState state, String searchString) {
        return Dao.findSeries(state, searchString)
                .stream().sorted(Comparator.comparing(Series::getTitle)).collect(Collectors.toList());
    }
}
