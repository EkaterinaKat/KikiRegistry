package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.date.Month;
import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.LabelBuilder;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController2;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.kikinotebook.core.films.ViewingHistoryService;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.FxUtils.getPaneWithWidth;
import static com.katyshevtseva.fx.Styler.ThingToColor.BACKGROUND;
import static com.katyshevtseva.fx.Styler.getColorfullStyle;
import static com.katyshevtseva.fx.Styler.setHoverStyle;

public class ViewingHistoryController implements SectionController {
    private static final int POSTER_WIDTH = 200;
    private static final int GRID_WIDTH = 1100;
    private Integer currentYear;
    private Integer leftYear;
    private Integer rightYear;
    @FXML
    private VBox contentPane;
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
        contentPane.getChildren().clear();
        for (Month month : ViewingHistoryService.getMonthsWithViews(year)) {
            contentPane.getChildren().add(new LabelBuilder().text(month.getTitle()).textSize(20).build());
            contentPane.getChildren().add(getFilmGridNode(ViewingHistoryService.getFilms(year, month)));
        }
    }

    private Node getFilmGridNode(List<Film> films) {
        ComponentBuilder.Component<BlockGridController2<Film>> component =
                new ComponentBuilder().getBlockGridComponent2(POSTER_WIDTH, GRID_WIDTH, this::getFilmNode);
        component.getController().setContent(films);
        return component.getNode();
    }

    private Node getFilmNode(Film film, int blockWidth) {
        Label nameLabel = new Label(film.getTitleAndYear());
        FxUtils.setWidth(nameLabel, blockWidth);
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.BASELINE_CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                getPaneWithHeight(10),
                nameLabel,
                getPaneWithHeight(10));

//        if (author.getImageName() != null) {
//            vBox.getChildren().addAll(
//                    placeImageInSquare(new ImageView(getImageContainer(author).getImage()), blockWidth),
//                    getPaneWithHeight(10));
//        }
//
//        for (Book book : BookService.find(author, getStringToSearchBook(author))) {
//            Label label = new Label(book.getListInfo());
//
//            label.setStyle(label.getStyle() + Styler.getColorfullStyle(BACKGROUND, book.getGrade().getColor()));
//            label.setWrapText(true);
//            label.setMaxWidth(blockWidth);
//
//            label.setContextMenu(getBookContextMenu(book, author));
//            vBox.getChildren().addAll(label, getPaneWithHeight(10));
//        }

        HBox hBox = new HBox();
        hBox.getChildren().addAll(getPaneWithWidth(10), vBox, getPaneWithWidth(10));
        hBox.setStyle(Styler.getBlackBorderStyle());
        setHoverStyle(hBox, getColorfullStyle(BACKGROUND, "#EF47FF"));
        return hBox;
    }
}
