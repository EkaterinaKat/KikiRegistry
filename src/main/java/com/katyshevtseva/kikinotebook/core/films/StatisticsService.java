package com.katyshevtseva.kikinotebook.core.films;

import com.katyshevtseva.date.DateUtils;
import com.katyshevtseva.kikinotebook.core.Dao;
import com.katyshevtseva.kikinotebook.core.films.model.Film;

import java.util.*;

public class StatisticsService {

    public static Map<Integer, Integer> getYearFilmCountMap() {
        List<Film> films = Dao.getAllFilms();
        Map<Integer, Integer> map = new HashMap<>();

        int firstYear = films.stream().min(Comparator.comparing(Film::getYear)).get().getYear();
        int currentYear = DateUtils.getYearDateBelongsTo(new Date());
        for (int i = firstYear; i <= currentYear; i++) {
            map.put(i, 0);
        }

        for (Film film : films) {
            int count = map.getOrDefault(film.getYear(), 0);
            count++;
            map.put(film.getYear(), count);
        }

        int sum = map.values().stream().mapToInt(i -> i).sum();
        if (sum != films.size()) {
            throw new RuntimeException();
        }

        return map;
    }
}
