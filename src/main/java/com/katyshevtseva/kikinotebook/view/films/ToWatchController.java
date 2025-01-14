package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController2;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.kikinotebook.core.films.FilmToWatchService;
import com.katyshevtseva.kikinotebook.core.films.PosterFileManager;
import com.katyshevtseva.kikinotebook.core.films.model.FilmToWatch;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

import static com.katyshevtseva.fx.FxUtils.getPaneWithWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.setImageWidthPreservingRatio;
import static com.katyshevtseva.kikinotebook.view.films.FilmMenuManager.*;
import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.NotebookDialogInfo.FILM_SEARCH_DIALOG;

public class ToWatchController implements SectionController {
    private static final int POSTER_WIDTH = 200;
    private static final int DESC_WIDTH = 350;

    private static final int GRID_WIDTH = 1200;
    @FXML
    private VBox contentPane;
    @FXML
    private Button addFilmButton;

    @FXML
    private void initialize() {
        addFilmButton.setOnAction(event -> WindowBuilder.openDialog(FILM_SEARCH_DIALOG,
                new FilmSearchController(this::updateContent)));

        updateContent();
    }

    private void updateContent() {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(getFilmGridNode(FilmToWatchService.getFilmsToWatch()));
    }

    private Node getFilmGridNode(List<FilmToWatch> films) {
        ComponentBuilder.Component<BlockGridController2<FilmToWatch>> component = new ComponentBuilder()
                .getBlockGridComponent2(POSTER_WIDTH + DESC_WIDTH + 10, GRID_WIDTH, this::getFilmNode);
        component.getController().setContent(films);
        return component.getNode();
    }

    private Node getFilmNode(FilmToWatch film, int blockWidth) {
        ImageView imageView = new ImageView(PosterFileManager.getPoster(film).getImage());
        setImageWidthPreservingRatio(imageView, POSTER_WIDTH);

        Label descLabel = new Label(film.getFullDesc());
        FxUtils.setWidth(descLabel, DESC_WIDTH);
        descLabel.setWrapText(true);
//        descLabel.setTextAlignment(TextAlignment.CENTER);
//        descLabel.setAlignment(Pos.BASELINE_CENTER);

        HBox hBox = new HBox();
        hBox.setOnContextMenuRequested(event -> showContextMenu(event, hBox, film));
        hBox.setStyle(Styler.getBlackBorderStyle());
        hBox.getChildren().addAll(
                imageView,
                getPaneWithWidth(10),
                descLabel);


//        VBox vBox = new VBox();
//        vBox.getChildren().addAll(hBox);
//        hBox.getChildren().add(vBox);

        return hBox;
    }

    private void showContextMenu(ContextMenuEvent event, Node node, FilmToWatch film) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(getLoadPosterItem(film, this::updateContent));
        contextMenu.getItems().add(getDeleteItem(film, this::updateContent));
        contextMenu.getItems().add(getCreateWatchedFilmItem(film, this::updateContent));
        contextMenu.show(node, event.getScreenX(), event.getScreenY());
    }
}
