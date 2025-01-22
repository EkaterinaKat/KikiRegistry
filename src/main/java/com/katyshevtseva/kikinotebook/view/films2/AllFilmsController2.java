package com.katyshevtseva.kikinotebook.view.films2;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController2;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.kikinotebook.core.films2.FilmsService2;
import com.katyshevtseva.kikinotebook.core.films2.PosterFileManager2;
import com.katyshevtseva.kikinotebook.core.films2.model.Film;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.ImageSizeUtil.setImageWidthPreservingRatio;
import static com.katyshevtseva.kikinotebook.view.films2.FilmMenuManager2.getEditItem;
import static com.katyshevtseva.kikinotebook.view.films2.FilmMenuManager2.getLoadPosterItem;

public class AllFilmsController2 implements SectionController {
    private static final int POSTER_WIDTH = 222;
    private static final int GRID_WIDTH = 1200;
    @FXML
    private VBox contentPane;

    @FXML
    private void initialize() {
        updateContent();
    }

    private void updateContent() {
        List<Film> films = FilmsService2.getAllFilms();
        System.out.println("Total: " + films.size());
        List<Film> filteredList = films
                .stream()
                .sorted(Comparator.comparing(Film::getId))
//                .filter(film -> film.getPosterState() != PosterState.LOADED)
//                .peek(PosterLoader::loadPoster)
                .collect(Collectors.toList());
        System.out.println("Filtered: " + filteredList.size());

        contentPane.getChildren().clear();
        contentPane.getChildren().add(getFilmGridNode(filteredList));
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
        FxUtils.setHeight(nameLabel, 50);
        nameLabel.setWrapText(true);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setAlignment(Pos.BASELINE_CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                getPaneWithHeight(10),
                nameLabel,
                getPaneWithHeight(10));

        ImageView imageView = new ImageView(PosterFileManager2.getPoster(film).getImage());
        setImageWidthPreservingRatio(imageView, blockWidth);
        vBox.getChildren().addAll(imageView, getPaneWithHeight(10));

        HBox hBox = new HBox();
        hBox.getChildren().add(vBox);
        hBox.setOnContextMenuRequested(event -> showContextMenu(event, hBox, film));
        return hBox;
    }

    private void showContextMenu(ContextMenuEvent event, Node node, Film film) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(getEditItem(film, this::updateContent));
        contextMenu.getItems().add(getLoadPosterItem(film, this::updateContent));
        contextMenu.show(node, event.getScreenX(), event.getScreenY());
    }
}
