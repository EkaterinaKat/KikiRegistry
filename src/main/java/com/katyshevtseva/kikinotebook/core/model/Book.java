package com.katyshevtseva.kikinotebook.core.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static com.katyshevtseva.date.DateUtils.READABLE_DATE_FORMAT;
import static com.katyshevtseva.general.GeneralUtils.isEmpty;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Enumerated(EnumType.STRING)
    private BookAction action;

    @Temporal(TemporalType.DATE)
    private Date finishDate;

    private boolean favorite;

    public Book() {
    }

    public Book(String name, Author author, BookAction action, Date finishDate, boolean favorite) {
        this.name = name;
        this.author = author;
        this.action = action;
        this.finishDate = finishDate;
        this.favorite = favorite;
    }

    public void setValues(String name, BookAction action, Date finishDate, boolean favorite) {
        this.name = name;
        this.action = action;
        this.favorite = favorite;
        this.finishDate = finishDate;
    }

    public String getListInfo() {
        StringBuilder stringBuilder = new StringBuilder(name);
        if (finishDate != null && !isEmpty(action.getShortInfo())) {
            stringBuilder
                    .append("\n")
                    .append(READABLE_DATE_FORMAT.format(finishDate))
                    .append(" ")
                    .append(action.getShortInfo());
        }
        return stringBuilder.toString();
    }
}
